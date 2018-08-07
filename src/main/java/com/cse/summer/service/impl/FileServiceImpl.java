package com.cse.summer.service.impl;

import com.cse.summer.domain.Excel;
import com.cse.summer.domain.Machine;
import com.cse.summer.domain.Material;
import com.cse.summer.domain.Structure;
import com.cse.summer.repository.MachineRepository;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.FileService;
import com.cse.summer.util.Generator;
import com.cse.summer.util.SummerConst;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王振琦
 */
@Service(value = "fileService")
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final MachineRepository machineRepository;
    private final MaterialRepository materialRepository;
    private final StructureRepository structureRepository;

    @Autowired
    public FileServiceImpl(MachineRepository machineRepository, MaterialRepository materialRepository, StructureRepository structureRepository) {
        this.machineRepository = machineRepository;
        this.materialRepository = materialRepository;
        this.structureRepository = structureRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importMANXml(String machineName, MultipartFile file) throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file.getInputStream());
        Element root = doc.getRootElement();
        List<Material> materialList = new ArrayList<>(1000);
        List<Structure> structureList = new ArrayList<>(100);
        xmlRecursiveTraversal(root.element("designSpec"), materialList, structureList, null, null, machineName, -1);

        Machine machine = new Machine();
        machine.setObjectId(Generator.getObjectId());
        machine.setStatus(1);
        machine.setName(machineName);
        machine.setType(SummerConst.MachineType.MAN);
        machineRepository.save(machine);

        materialRepository.saveAll(materialList);
        structureRepository.saveAll(structureList);
    }

    @SuppressWarnings("unchecked")
    private void xmlRecursiveTraversal(Element element, List<Material> materialList, List<Structure> structureList, String structureNo, String parentId, String machineName, int parentLevel) {
        if ("designSpec".equals(element.getName())) {
            logger.info("装置号id: " + element.attributeValue("id"));
            Element revision = element.element("revision");
            Element modules = revision.element("moduleList");
            List<Element> moduleList = modules.elements("module");
            for (Element module : moduleList) {
                xmlRecursiveTraversal(module, materialList, structureList, null, null, machineName, parentLevel);
            }
        } else if ("module".equals(element.getName())) {
            Element revision = element.element("revision");
            // 如果库中已有当前部套，则不会再次导入
            List<Material> materials = materialRepository.findMaterialByMaterialNoAndMaterialVersionAndTypeAndLevel(
                    element.attributeValue("id"), revision.attributeValue("revision"), 1, 0);
            Structure structure = new Structure();
            structure.setObjectId(Generator.getObjectId());
            structure.setStatus(1);
            structure.setMachineName(machineName);
            structure.setRevision(revision.attributeValue("revision"));
            structure.setStructureNo(revision.element("structureNo").getText());
            if (materials.size() > 0) {
                structure.setVersion(materials.get(0).getLatestVersion());
                structureList.add(structure);
            } else {
                Material material = new Material(1);
                int level = parentLevel + 1;
                material.setLevel(level);
                material.setObjectId(Generator.getObjectId());
                material.setStatus(1);
                material.setVersion(0);
                material.setLatestVersion(0);
                material.setMaterialNo(element.attributeValue("id"));
                material.setName(element.element("name").getText());
                material.setMaterialVersion(revision.attributeValue("revision"));
                material.setPage(revision.element("noOfPages").getText());
                material.setWeight(revision.element("mass").getText());
                material.setStructureNo(revision.element("structureNo").getText());
                material.setAmount(revision.element("quantity").getText());
                materialList.add(material);
                Element parts = revision.element("partList");
                int childCount = xmlPartsRecursiveTraversal(parts, materialList, material.getStructureNo(), material.getObjectId(), machineName, level);
                material.setChildCount(childCount);

                structure.setVersion(0);
                structureList.add(structure);
            }
        } else if ("part".equals(element.getName())) {
            Material material = new Material(1);
            int level = parentLevel + 1;
            material.setLevel(level);
            material.setObjectId(Generator.getObjectId());
            material.setStatus(1);
            material.setVersion(0);
            material.setLatestVersion(0);
            material.setParentId(parentId);
            material.setStructureNo(structureNo);
            material.setMaterialNo(element.attributeValue("id"));
            material.setName(element.element("name").getText());
            Element revision = element.element("revision");
            material.setMaterialVersion(revision.attributeValue("revision"));
            if (null != revision.element("mass")) {
                material.setWeight(revision.element("mass").getText());
            }
            if (null != revision.element("quantity")) {
                material.setAmount(revision.element("quantity").getText());
            }
            if (null != revision.element("drawingSize")) {
                material.setDrawingSize(revision.element("drawingSize").getText());
            }
            if (null != revision.element("material")) {
                material.setMaterial(revision.element("material").getText());
            }
            if (null != revision.element("posNo")) {
                material.setPositionNo(revision.element("posNo").getText());
            }
            if (null != revision.element("sequenceNo")) {
                material.setSequenceNo(revision.element("sequenceNo").getText());
            }
            materialList.add(material);
            Element parts = revision.element("partList");
            int childCount = xmlPartsRecursiveTraversal(parts, materialList, structureNo, material.getObjectId(), machineName, level);

            material.setChildCount(childCount);
        } else if ("standardPart".equals(element.getName()) || "document".equals(element.getName())
                || "supDrawing".equals(element.getName()) || "licData".equals(element.getName())) {
            Material material = new Material(1);
            int level = parentLevel + 1;
            material.setLevel(level);
            material.setObjectId(Generator.getObjectId());
            material.setStatus(1);
            material.setVersion(0);
            material.setLatestVersion(0);
            material.setParentId(parentId);
            material.setStructureNo(structureNo);
            material.setMaterialNo(element.attributeValue("id"));
            material.setName(element.element("name").getText());
            Element revision = element.element("revision");
            material.setMaterialVersion(revision.attributeValue("revision"));
            if (null != revision.element("mass")) {
                material.setWeight(revision.element("mass").getText());
            }
            if (null != revision.element("quantity")) {
                material.setAmount(revision.element("quantity").getText());
            }
            if (null != revision.element("drawingSize")) {
                material.setDrawingSize(revision.element("drawingSize").getText());
            }
            if (null != revision.element("material")) {
                material.setMaterial(revision.element("material").getText());
            }
            if (null != revision.element("posNo")) {
                material.setPositionNo(revision.element("posNo").getText());
            }
            if (null != revision.element("sequenceNo")) {
                material.setSequenceNo(revision.element("sequenceNo").getText());
            }
            material.setChildCount(0);
            materialList.add(material);
        }
    }

    @SuppressWarnings("unchecked")
    private int xmlPartsRecursiveTraversal(Element parts, List<Material> materialList, String structureNo, String parentId, String machineName, int parentLevel) {
        int childCount = 0;
        if (null != parts) {
            List<Element> partList = parts.elements("part");
            if (partList.size() > 0) {
                childCount += partList.size();
                for (Element part : partList) {
                    xmlRecursiveTraversal(part, materialList, null, structureNo, parentId, machineName, parentLevel);
                }
            }
            List<Element> standardParts = parts.elements("standardPart");
            if (standardParts.size() > 0) {
                childCount += standardParts.size();
                for (Element standardPart : standardParts) {
                    xmlRecursiveTraversal(standardPart, materialList, null, structureNo, parentId, machineName, parentLevel);
                }
            }
            List<Element> documents = parts.elements("document");
            if (documents.size() > 0) {
                childCount += documents.size();
                for (Element document : documents) {
                    xmlRecursiveTraversal(document, materialList, null, structureNo, parentId, machineName, parentLevel);
                }
            }
            List<Element> supDrawings = parts.elements("supDrawing");
            if (supDrawings.size() > 0) {
                childCount += supDrawings.size();
                for (Element supDrawing : supDrawings) {
                    xmlRecursiveTraversal(supDrawing, materialList, null, structureNo, parentId, machineName, parentLevel);
                }
            }
            List<Element> licData = parts.elements("licData");
            if (licData.size() > 0) {
                childCount += licData.size();
                for (Element aLicData : licData) {
                    xmlRecursiveTraversal(aLicData, materialList, null, structureNo, parentId, machineName, parentLevel);
                }
            }
        }
        return childCount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importWinGDExcel(String machineName, MultipartFile file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        List<Material> materialList = new ArrayList<>(1000);
        List<Structure> structureList = new ArrayList<>(100);
        winGDExcelProcess(machineName, workbook, materialList, structureList);
        Machine machine = new Machine();
        machine.setObjectId(Generator.getObjectId());
        machine.setStatus(1);
        machine.setName(machineName);
        machine.setType(SummerConst.MachineType.WIN_GD);
        machineRepository.save(machine);
        structureRepository.saveAll(structureList);
        materialRepository.saveAll(materialList);
    }

    private void winGDExcelProcess(String machineName, Workbook workbook, List<Material> materialList, List<Structure> structureList) {
        // 建立维护层级关系的数组
        Material[] levelArray = new Material[10];
        Sheet sheet = workbook.getSheetAt(1);
        int index = 0;
        for (Row row : sheet) {
            if (index < 1) {
                index++;
            } else {
                String unImportStruct = "";
                if (null != row.getCell(0)) {
                    // 获取该节点层级，从0开始
                    int level = row.getCell(0).toString().length() / 3 - 1;
                    if (0 == level) {
                        Structure structure = new Structure();
                        structure.setObjectId(Generator.getObjectId());
                        structure.setStatus(1);
                        structure.setMachineName(machineName);
                        structure.setStructureNo(row.getCell(8).toString());
                        String materialVersion = "";
                        if (null != row.getCell(5)) {
                            materialVersion = row.getCell(5).toString();
                        }
                        structure.setRevision(materialVersion);

                        List<Material> materials = materialRepository.findMaterialByMaterialNoAndMaterialVersionAndTypeAndLevel(
                                row.getCell(4).toString(), materialVersion, 2, 0);
                        if (materials.size() > 0) {
                            structure.setVersion(materials.get(0).getLatestVersion());
                            unImportStruct = structure.getStructureNo();
                        } else {
                            structure.setVersion(0);
                        }
                        structureList.add(structure);
                    }

                    Material material = new Material(2);
                    material.setObjectId(Generator.getObjectId());
                    material.setStatus(1);
                    material.setVersion(0);
                    material.setLatestVersion(0);
                    material.setChildCount(0);
                    if (null != row.getCell(1)) {
                        material.setDrawingSize(row.getCell(1).toString());
                    }
                    if (null != row.getCell(2)) {
                        material.setDrawingNo(row.getCell(2).toString());
                    }
                    if (null != row.getCell(3)) {
                        material.setDrawingVersion(row.getCell(3).toString());
                    }
                    if (null != row.getCell(4)) {
                        material.setMaterialNo(row.getCell(4).toString());
                    }
                    if (null != row.getCell(5)) {
                        material.setMaterialVersion(row.getCell(5).toString());
                    } else {
                        material.setMaterialVersion("");
                    }
                    if (null != row.getCell(7)) {
                        material.setName(row.getCell(7).toString());
                    }
                    if (null != row.getCell(8)) {
                        material.setStructureNo(row.getCell(8).toString());
                    }
                    if (null != row.getCell(9)) {
                        material.setMaterial(row.getCell(9).toString());
                    }
                    if (null != row.getCell(10)) {
                        material.setMaterialJis(row.getCell(10).toString());
                    }
                    if (null != row.getCell(11)) {
                        material.setMaterialWin(row.getCell(11).toString());
                    }
                    if (null != row.getCell(12)) {
                        material.setWeight(row.getCell(12).toString());
                    }
                    if (null != row.getCell(13)) {
                        material.setPositionNo(row.getCell(13).toString());
                    }
                    if (null != row.getCell(14)) {
                        material.setAmount(row.getCell(14).toString());
                    }
                    if (null != row.getCell(16)) {
                        material.setModifyNote(row.getCell(16).toString());
                    }
                    if (null != row.getCell(19)) {
                        material.setAbsoluteAmount(row.getCell(19).toString());
                    }
                    if (null != row.getCell(0)) {
                        // 维护WinGD原层级格式
                        material.setSrcLevel(row.getCell(0).toString());
                        // 获取该节点层级，从0开始
                        int aLevel = row.getCell(0).toString().length() / 3 - 1;
                        // 设置层级
                        material.setLevel(aLevel);
                        if (0 == aLevel) {
                            // 最上层节点时不设置父节点
                            levelArray[0] = material;
                        } else {
                            Material parentMat = levelArray[aLevel - 1];
                            parentMat.setChildCount(parentMat.getChildCount() + 1);
                            // 根据最上级节点设置部套号
                            material.setStructureNo(parentMat.getStructureNo());
                            // 设置该节点所属上级节点的ID
                            material.setParentId(parentMat.getObjectId());
                            // 将该节点覆盖数组中相同层级的上一个节点
                            levelArray[aLevel] = material;
                        }
                    }
                    if (!unImportStruct.equals(material.getStructureNo())) {
                        materialList.add(material);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Excel exportMachineExcel(String machineName) {
        List<Structure> structureList = structureRepository.findAllByMachineNameAndStatusOrderByStructureNo(machineName, 1);
        List<Material> materialList = new ArrayList<>();
        for (Structure structure : structureList) {
            List<Material> materials = materialRepository.findAllByStructureNoAndVersion(structure.getStructureNo(), structure.getVersion());
            materialList.addAll(materials);
        }
        XSSFWorkbook workbook = buildExcelWorkbook(materialList);
        return new Excel(machineName + ".xlsx", workbook);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importNewVersionStructureExcel(String structureNo, MultipartFile file) throws IOException, InvalidFormatException {

        // 更新旧部套的最新版本号
        List<Material> oldRecordList = materialRepository.findAllByStructureNo(structureNo);
        int oldVersion = oldRecordList.get(0).getLatestVersion();
        int latestVersion = oldVersion + 1;
        // 不可以使用foreach 因为foreach循环是拷贝而不是引用同一个对象
        for (int index = 0; index < oldRecordList.size(); index++) {
            oldRecordList.get(index).setLatestVersion(latestVersion);
        }
        materialRepository.saveAll(oldRecordList);

        // 开始解析新版本数据
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        List<Material> recordList = new ArrayList<>(500);
        // 建立维护层级关系的数组
        Material[] levelArray = new Material[10];
        Sheet sheet = workbook.getSheetAt(0);
        int index = 0;
        for (Row row : sheet) {
            if (index < 1) {
                index++;
            } else {
                Material material = new Material(2);
                material.setObjectId(Generator.getObjectId());
                material.setStatus(1);
                material.setVersion(latestVersion);
                material.setLatestVersion(latestVersion);
                material.setChildCount(0);
                if (null != row.getCell(0)) {
                    material.setStructureNo(row.getCell(0).toString());
                }
                if (null != row.getCell(1)) {
                    /*不知道为什么表格中有层次为0的数据会自动被替换为0.0*/
                    int level = Integer.parseInt(row.getCell(1).toString().replace(".0", ""));
                    material.setLevel(level);
                    if (0 == level) {
                        // 最上层节点时不设置父节点
                        levelArray[0] = material;
                    } else {
                        Material parentMat = levelArray[level - 1];
                        parentMat.setChildCount(parentMat.getChildCount() + 1);
                        // 设置该节点所属上级节点的ID
                        material.setParentId(parentMat.getObjectId());
                        // 将该节点覆盖数组中相同层级的上一个节点
                        levelArray[level] = material;
                    }
                }
                if (null != row.getCell(2)) {
                    material.setMaterialNo(row.getCell(2).toString());
                }
                if (null != row.getCell(3)) {
                    material.setMaterialVersion(row.getCell(3).toString());
                }
                if (null != row.getCell(4)) {
                    material.setDrawingNo(row.getCell(4).toString());
                }
                if (null != row.getCell(5)) {
                    material.setDrawingSize(row.getCell(5).toString());
                }
                if (null != row.getCell(6)) {
                    material.setName(row.getCell(6).toString());
                }
                if (null != row.getCell(7)) {
                    material.setAmount(row.getCell(7).toString());
                }
                if (null != row.getCell(8)) {
                    material.setWeight(row.getCell(8).toString());
                }
                if (null != row.getCell(9)) {
                    material.setAbsoluteAmount(row.getCell(9).toString());
                }
                recordList.add(material);
            }
        }
        materialRepository.saveAll(recordList);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Excel exportStructureExcel(String structureNo, Integer version) {
        List<Material> materialList = materialRepository.findAllByStructureNoAndVersion(structureNo, version);
        XSSFWorkbook workbook = buildExcelWorkbook(materialList);
        Material structure = materialList.get(0);
        String revision = structure.getMaterialVersion();
        String name = structure.getStructureNo() + "_" + structure.getMaterialNo() + "." + revision + "_" + structure.getVersion() + ".xlsx";
        return new Excel(name, workbook);
    }

    private XSSFWorkbook buildExcelWorkbook(List<Material> recordList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell0 = row.createCell(0);
        cell0.setCellValue("部套号");
        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("层次");
        XSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("物料号");
        XSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("专利方版本");
        XSSFCell cell4 = row.createCell(4);
        cell4.setCellValue("图号");
        XSSFCell cell5 = row.createCell(5);
        cell5.setCellValue("图幅");
        XSSFCell cell6 = row.createCell(6);
        cell6.setCellValue("名称");
        XSSFCell cell7 = row.createCell(7);
        cell7.setCellValue("总数量");
        XSSFCell cell8 = row.createCell(8);
        cell8.setCellValue("单重");
        XSSFCell cell9 = row.createCell(9);
        cell9.setCellValue("数量");

        int size = recordList.size();
        for (int index = 0; index < size; index++) {
            Material material = recordList.get(index);
            XSSFRow tempRow = sheet.createRow(index + 1);
            XSSFCell tempCell0 = tempRow.createCell(0);
            if (null != material.getStructureNo()) {
                tempCell0.setCellValue(material.getStructureNo());
            }
            XSSFCell tempCell1 = tempRow.createCell(1);
            if (null != material.getLevel()) {
                tempCell1.setCellValue(material.getLevel());
            }
            XSSFCell tempCell2 = tempRow.createCell(2);
            if (null != material.getMaterialNo()) {
                tempCell2.setCellValue(material.getMaterialNo());
            }
            XSSFCell tempCell3 = tempRow.createCell(3);
            if (null != material.getMaterialVersion()) {
                tempCell3.setCellValue(material.getMaterialVersion());
            }
            XSSFCell tempCell4 = tempRow.createCell(4);
            if (null != material.getDrawingNo()) {
                tempCell4.setCellValue(material.getDrawingNo());
            }
            XSSFCell tempCell5 = tempRow.createCell(5);
            if (null != material.getDrawingSize()) {
                tempCell5.setCellValue(material.getDrawingSize());
            }
            XSSFCell tempCell6 = tempRow.createCell(6);
            if (null != material.getName()) {
                tempCell6.setCellValue(material.getName());
            }
            XSSFCell tempCell7 = tempRow.createCell(7);
            if (null != material.getAmount()) {
                tempCell7.setCellValue(material.getAmount());
            }
            XSSFCell tempCell8 = tempRow.createCell(8);
            if (null != material.getWeight()) {
                tempCell8.setCellValue(material.getWeight());
            }
            XSSFCell tempCell9 = tempRow.createCell(9);
            if (null != material.getAbsoluteAmount()) {
                tempCell9.setCellValue(material.getAbsoluteAmount());
            }
        }
        return workbook;
    }
}