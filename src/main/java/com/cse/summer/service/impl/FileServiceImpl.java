package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.domain.*;
import com.cse.summer.repository.MachineRepository;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.NameRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.FileService;
import com.cse.summer.util.Generator;
import com.cse.summer.util.StatusCode;
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
    private final NameRepository nameRepository;

    @Autowired
    public FileServiceImpl(MachineRepository machineRepository, MaterialRepository materialRepository, StructureRepository structureRepository, NameRepository nameRepository) {
        this.machineRepository = machineRepository;
        this.materialRepository = materialRepository;
        this.structureRepository = structureRepository;
        this.nameRepository = nameRepository;
    }

    private String findChineseName(String englishName, List<Name> names) {
        for (Name name : names) {
            if (name.getEnglish().equals(englishName)) {
                return name.getChinese();
            }
        }
        return "";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importMANXml(String machineName, MultipartFile file) throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file.getInputStream());
        Element root = doc.getRootElement();
        List<Material> materialList = new ArrayList<>(1000);
        List<Structure> structureList = new ArrayList<>(100);

        List<Name> names = nameRepository.findAll();

        xmlRecursiveTraversal(root.element("designSpec"), materialList, structureList, null, machineName, -1, null, null, names);

        Machine targetMachine = machineRepository.findMachineByName(machineName);
        if (null == targetMachine) {
            Machine machine = new Machine();
            machine.setObjectId(Generator.getObjectId());
            machine.setStatus(1);
            machine.setName(machineName);
            machine.setPatent(SummerConst.MachineType.MAN);
            machine.setMachineNo("");
            machine.setType("");
            machine.setCylinderAmount(0);
            machine.setShipNo("");
            machine.setClassificationSociety("");
            machineRepository.save(machine);
        }

        materialRepository.saveAll(materialList);
        structureRepository.saveAll(structureList);
    }

    /**
     * 层级遍历
     *
     * @param element       被遍历的元素
     * @param materialList  用于储存物料的集合，被遍历的元素统一存放到此处
     * @param structureList 用于存储部套的集合
     * @param parentId      父节点ID
     * @param machineName   机器名
     * @param parentLevel   父节点层级
     *                      部套判断是否已存在时的比较属性：机器名、物料号、专利方版本、部套号、是否已删除
     *                      物料判断是否已存在时的比较属性：物料号、专利方版本
     *                      先判断部套是否存在，如果部套存在那么该物料一定存在，如果部套不存在，才判断物料是否存在
     */
    @SuppressWarnings("unchecked")
    private void xmlRecursiveTraversal(Element element, List<Material> materialList, List<Structure> structureList,
                                       String parentId, String machineName, int parentLevel, String atNo, String atRevision, List<Name> names) {
        if ("designSpec".equals(element.getName())) {
            logger.info("装置号id: " + element.attributeValue("id"));
            Element revision = element.element("revision");
            Element modules = revision.element("moduleList");
            List<Element> moduleList = modules.elements("module");
            for (Element module : moduleList) {
                xmlRecursiveTraversal(module, materialList, structureList, null, machineName, parentLevel, null, null, names);
            }
        } else if ("module".equals(element.getName())) {
            Element revision = element.element("revision");
            String structNoM = revision.element("structureNo").getText();
            String materNoM = element.attributeValue("id");
            String revisionM = revision.attributeValue("revision");
            // 判断部套是否存在，不存在的情况下才需要保存该部套信息
            Structure targetStruct = structureRepository.findExistStructure(machineName, structNoM, materNoM, revisionM);
            // 如果部套不存在，则保存部套，并判断库中是否有部套对应的物料，物料存在则重用库中物料，不存在则新建物料
            // 如果部套已经存在，那么物料一定也存在了，那么该部套对应的物料所有的子节点都不需要遍历了
            if (null == targetStruct) {
                targetStruct = new Structure();
                targetStruct.setObjectId(Generator.getObjectId());
                targetStruct.setStatus(1);
                targetStruct.setMachineName(machineName);
                targetStruct.setStructureNo(structNoM);
                targetStruct.setMaterialNo(materNoM);
                targetStruct.setRevision(revisionM);
                targetStruct.setVersion(0);

                // 判断待保存的物料是否存在
                List<Material> materials = materialRepository.findAllByMaterialNoAndRevisionAndLevel(materNoM, revisionM, 0);
                // 集合大于0表示物料存在
                if (0 == materials.size()) {
                    // 如果物料不存在，则需要保存物料的数据，并遍历物料的所有子节点
                    Material material = new Material(1);
                    material.setAtNo(materNoM);
                    material.setAtRevision(revisionM);
                    int level = parentLevel + 1;
                    material.setLevel(level);
                    material.setObjectId(Generator.getObjectId());
                    material.setStatus(1);
                    material.setVersion(0);
                    material.setLatestVersion(0);
                    material.setMaterialNo(materNoM);
                    material.setName(element.element("name").getText());
                    String chineseName = findChineseName(element.element("name").getText(), names);
                    material.setChinese(chineseName);
                    material.setRevision(revisionM);
                    material.setPage(revision.element("noOfPages").getText());
                    material.setWeight(revision.element("mass").getText());
                    // 可能存在数量为1.00的形式，所以字符串先转为double再转为int
                    material.setAbsoluteAmount(1);
                    targetStruct.setAmount((int) Double.parseDouble(revision.element("quantity").getText()));
                    materialList.add(material);
                    Element parts = revision.element("partList");
                    int childCount = xmlPartsRecursiveTraversal(parts, materialList, material.getObjectId(), machineName, level, materNoM, revisionM, names);
                    material.setChildCount(childCount);
                }
                structureList.add(targetStruct);
            }
        } else if ("part".equals(element.getName())) {
            Material material = new Material(1);
            material.setAtNo(atNo);
            material.setAtRevision(atRevision);
            int level = parentLevel + 1;
            material.setLevel(level);
            material.setObjectId(Generator.getObjectId());
            material.setStatus(1);
            material.setVersion(0);
            material.setLatestVersion(0);
            material.setParentId(parentId);
            material.setMaterialNo(element.attributeValue("id"));
            material.setName(element.element("name").getText());
            String chineseName = findChineseName(element.element("name").getText(), names);
            material.setChinese(chineseName);
            Element revision = element.element("revision");
            material.setRevision(revision.attributeValue("revision"));
            if (null != revision.element("mass")) {
                material.setWeight(revision.element("mass").getText());
            }
            if (null != revision.element("quantity")) {
                material.setAbsoluteAmount((int) Double.parseDouble(revision.element("quantity").getText()));
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
            int childCount = xmlPartsRecursiveTraversal(parts, materialList, material.getObjectId(), machineName, level, atNo, atRevision, names);

            material.setChildCount(childCount);
        } else if ("standardPart".equals(element.getName()) || "document".equals(element.getName())
                || "supDrawing".equals(element.getName()) || "licData".equals(element.getName())) {
            Material material = new Material(1);
            material.setAtNo(atNo);
            material.setAtRevision(atRevision);
            int level = parentLevel + 1;
            material.setLevel(level);
            material.setObjectId(Generator.getObjectId());
            material.setStatus(1);
            material.setVersion(0);
            material.setLatestVersion(0);
            material.setParentId(parentId);
            material.setMaterialNo(element.attributeValue("id"));
            material.setName(element.element("name").getText());
            String chineseName = findChineseName(element.element("name").getText(), names);
            material.setChinese(chineseName);
            Element revision = element.element("revision");
            material.setRevision(revision.attributeValue("revision"));
            if (null != revision.element("mass")) {
                material.setWeight(revision.element("mass").getText());
            }
            if (null != revision.element("quantity")) {
                material.setAbsoluteAmount((int) Double.parseDouble(revision.element("quantity").getText()));
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

    /**
     * 查找该层元素是否有子节点并决定继续遍历的方法
     *
     * @param parts        该层元素
     * @param materialList 用于储存物料的集合，被遍历的元素统一存放到此处
     * @param parentId     父物料节点ID
     * @param machineName  机器名，用于部套数据检索，确定该机器是否已有某一部套
     * @param parentLevel  父节点层级
     * @return 该层元素子节点数
     */
    @SuppressWarnings("unchecked")
    private int xmlPartsRecursiveTraversal(Element parts, List<Material> materialList, String parentId, String machineName, int parentLevel, String atNo, String atRevision, List<Name> names) {
        int childCount = 0;
        if (null != parts) {
            List<Element> partList = parts.elements("part");
            if (partList.size() > 0) {
                childCount += partList.size();
                for (Element part : partList) {
                    xmlRecursiveTraversal(part, materialList, null, parentId, machineName, parentLevel, atNo, atRevision, names);
                }
            }
            List<Element> standardParts = parts.elements("standardPart");
            if (standardParts.size() > 0) {
                childCount += standardParts.size();
                for (Element standardPart : standardParts) {
                    xmlRecursiveTraversal(standardPart, materialList, null, parentId, machineName, parentLevel, atNo, atRevision, names);
                }
            }
            List<Element> documents = parts.elements("document");
            if (documents.size() > 0) {
                childCount += documents.size();
                for (Element document : documents) {
                    xmlRecursiveTraversal(document, materialList, null, parentId, machineName, parentLevel, atNo, atRevision, names);
                }
            }
            List<Element> supDrawings = parts.elements("supDrawing");
            if (supDrawings.size() > 0) {
                childCount += supDrawings.size();
                for (Element supDrawing : supDrawings) {
                    xmlRecursiveTraversal(supDrawing, materialList, null, parentId, machineName, parentLevel, atNo, atRevision, names);
                }
            }
            List<Element> licData = parts.elements("licData");
            if (licData.size() > 0) {
                childCount += licData.size();
                for (Element aLicData : licData) {
                    xmlRecursiveTraversal(aLicData, materialList, null, parentId, machineName, parentLevel, atNo, atRevision, names);
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

        List<Name> names = nameRepository.findAll();

        winGDExcelProcess(machineName, workbook, materialList, structureList, names);

        Machine targetMachine = machineRepository.findMachineByName(machineName);
        if (null == targetMachine) {
            Machine machine = new Machine();
            machine.setObjectId(Generator.getObjectId());
            machine.setStatus(1);
            machine.setName(machineName);
            machine.setPatent(SummerConst.MachineType.WIN_GD);
            machine.setMachineNo("");
            machine.setType("");
            machine.setCylinderAmount(0);
            machine.setShipNo("");
            machine.setClassificationSociety("");
            machineRepository.save(machine);
        }

        structureRepository.saveAll(structureList);
        materialRepository.saveAll(materialList);
    }

    private void winGDExcelProcess(String machineName, Workbook workbook, List<Material> materialList, List<Structure> structureList, List<Name> names) {
        // 建立维护层级关系的数组
        Material[] levelArray = new Material[12];
        Sheet sheet = workbook.getSheetAt(1);
        int index = 0;
        String unImportMater = "";
        for (Row row : sheet) {
            if (index < 1) {
                index++;
            } else {
                String materNo = row.getCell(4).toString();
                String materVersion = row.getCell(5).toString();
                // 获取该节点层级，从0开始
                int level = row.getCell(0).toString().length() / 3 - 1;
                if (0 == level) {
                    // 设为空，防止多个部套，部套名相同但物料号不同的情况
                    unImportMater = "";
                    String structNo = row.getCell(8).toString();
                    Structure targetStruct = structureRepository.findExistStructure(machineName, structNo, materNo, materVersion);

                    // 当部套不存在的时候就新创建部套
                    if (null == targetStruct) {
                        targetStruct = new Structure();
                        targetStruct.setObjectId(Generator.getObjectId());
                        targetStruct.setStatus(1);
                        targetStruct.setMachineName(machineName);
                        targetStruct.setStructureNo(structNo);
                        targetStruct.setMaterialNo(materNo);
                        targetStruct.setRevision(materVersion);
                        targetStruct.setAmount((int) Double.parseDouble(row.getCell(14).toString()));

                        // 当物料存在时就为部套设置物料最新的版本
                        List<Material> materials = materialRepository.findAllByMaterialNoAndRevisionAndLevel(materNo, materVersion, 0);
                        if (materials.size() > 0) {
                            // 当物料存在时，不需要再导入
                            unImportMater = materNo;
                        }
                        targetStruct.setVersion(0);
                        structureList.add(targetStruct);
                    } else {
                        // 当该部套存在时，该部套存在的物料也已经存在，所以不需要导入
                        unImportMater = materNo;
                    }
                }

                // 此处，即使部套已经存在也还需要遍历表，因为此处不是树级结构
                Material material = new Material(2);
                material.setObjectId(Generator.getObjectId());
                material.setStatus(1);
                material.setVersion(0);
                material.setLatestVersion(0);
                material.setChildCount(0);
                material.setMaterialNo(materNo);
                material.setRevision(materVersion);

                if (null != row.getCell(1)) {
                    material.setDrawingSize(row.getCell(1).toString());
                }
                if (null != row.getCell(2)) {
                    material.setDrawingNo(row.getCell(2).toString());
                }
                if (null != row.getCell(3)) {
                    material.setDrawingVersion(row.getCell(3).toString());
                }
                if (null != row.getCell(7)) {
                    material.setName(row.getCell(7).toString());
                }
                if (null != row.getCell(9) && !"".equals(row.getCell(9).toString())) {
                    material.setMaterial(row.getCell(9).toString());
                } else if (null != row.getCell(10) && !"".equals(row.getCell(10).toString())) {
                    material.setMaterial(row.getCell(10).toString());
                } else if (null != row.getCell(11) && !"".equals(row.getCell(11).toString())) {
                    material.setMaterial(row.getCell(11).toString());
                } else {
                    material.setMaterial("");
                }
                if (null != row.getCell(12)) {
                    material.setWeight(row.getCell(12).toString());
                }

                int levelLength = row.getCell(0).toString().length();
                material.setPositionNo(row.getCell(0).toString().substring(levelLength - 3, levelLength));

                if (null != row.getCell(16)) {
                    material.setModifyNote(row.getCell(16).toString());
                }
                if (null != row.getCell(19)) {
                    material.setAbsoluteAmount((int) Double.parseDouble(row.getCell(19).toString()));
                }

                // 维护WinGD原层级格式
                material.setSrcLevel(row.getCell(0).toString());
                // 设置层级
                material.setLevel(level);
                if (0 == level) {
                    // 最上层节点时不设置父节点
                    levelArray[0] = material;
                    material.setAtNo(material.getMaterialNo());
                    material.setAtRevision(material.getRevision());
                } else {
                    Material parentMat = levelArray[level - 1];
                    parentMat.setChildCount(parentMat.getChildCount() + 1);
                    // 根据最上级节点设置所属
                    material.setAtNo(levelArray[0].getMaterialNo());
                    material.setAtRevision(levelArray[0].getRevision());
                    // 设置该节点所属上级节点的ID
                    material.setParentId(parentMat.getObjectId());
                    // 将该节点覆盖数组中相同层级的上一个节点
                    levelArray[level] = material;
                }

                int amount = (int) Double.parseDouble(row.getCell(14).toString());
                material.setAbsoluteAmount(amount);

                if (!unImportMater.equals(material.getAtNo())) {
                    String chineseName = findChineseName(row.getCell(7).toString(), names);
                    material.setChinese(chineseName);
                    materialList.add(material);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importNewVersionStructureExcel(Structure structure, MultipartFile file) throws IOException, InvalidFormatException {
        // 开始解析新版本数据
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Row structRow = workbook.getSheetAt(0).getRow(1);
        String materNo = structRow.getCell(3).toString();
        String materV = structRow.getCell(14).toString();

        // 更新旧部套的最新版本号
        List<Material> oldMaterialList = materialRepository.findAllByAtNoAndAtRevision(materNo, materV);
        int oldVersion = oldMaterialList.get(0).getLatestVersion();
        int latestVersion = oldVersion + 1;
        // 不可以使用foreach 因为foreach循环是拷贝而不是引用同一个对象
        for (int index = 0; index < oldMaterialList.size(); index++) {
            oldMaterialList.get(index).setLatestVersion(latestVersion);
        }
        materialRepository.saveAll(oldMaterialList);

        List<Material> recordList = new ArrayList<>(500);
        // 建立维护层级关系的数组
        Material[] levelArray = new Material[10];
        Sheet sheet = workbook.getSheetAt(0);
        int index = 0;
        for (Row row : sheet) {
            if (index < 1) {
                index++;
            } else {
                Material material = new Material();
                material.setObjectId(Generator.getObjectId());
                material.setStatus(1);
                material.setVersion(latestVersion);
                material.setLatestVersion(latestVersion);
                material.setMaterialNo(row.getCell(3).toString());
                material.setRevision(row.getCell(14).toString());
                material.setChildCount(0);
                if (null != row.getCell(1)) {
                    int level = (int) Double.parseDouble(row.getCell(1).toString());
                    material.setLevel(level);
                    if (0 == level) {
                        // 最上层节点时不设置父节点
                        levelArray[0] = material;
                        material.setAtNo(material.getMaterialNo());
                        material.setAtRevision(material.getRevision());

                        String structNo = row.getCell(0).toString();
                        if (!structure.getStructureNo().equals(structNo)) {
                            throw new SummerException(StatusCode.STRUCTURE_NO_ERROR);
                        }
                    } else {
                        Material parentMat = levelArray[level - 1];
                        parentMat.setChildCount(parentMat.getChildCount() + 1);
                        // 设置该节点所属上级节点的ID
                        material.setParentId(parentMat.getObjectId());
                        material.setAtNo(parentMat.getAtNo());
                        material.setAtRevision(parentMat.getAtRevision());
                        // 将该节点覆盖数组中相同层级的上一个节点
                        levelArray[level] = material;
                    }
                }
                if (null != row.getCell(2)) {
                    material.setPositionNo(row.getCell(2).toString());
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
                    material.setChinese(row.getCell(7).toString());
                }
                if (null != row.getCell(8)) {
                    material.setMaterial(row.getCell(8).toString());
                }
                if (null != row.getCell(11)) {
                    material.setAbsoluteAmount((int) Double.parseDouble(row.getCell(11).toString()));
                }
                if (null != row.getCell(12)) {
                    material.setWeight(row.getCell(12).toString());
                }
                if (null != row.getCell(13)) {
                    material.setModifyNote(row.getCell(13).toString());
                }
                if (null != row.getCell(14)) {
                    material.setRevision(row.getCell(14).toString());
                }
                if (null != row.getCell(15)) {
                    material.setSpareExp(row.getCell(15).toString().replace(".0", ""));
                }
                recordList.add(material);
            }
        }
        materialRepository.saveAll(recordList);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Excel exportMachineExcel(String machineName) {
        Machine machine = machineRepository.findMachineByName(machineName);
        List<Structure> structureList = structureRepository.findAllByMachineNameAndStatusOrderByStructureNo(machineName, 1);
        List<Material> materialList = new ArrayList<>();
        for (Structure structure : structureList) {
            List<Material> materials = materialRepository.findAllByAtNoAndAtRevisionAndVersion(
                    structure.getMaterialNo(), structure.getRevision(), structure.getVersion());
            dataHandler(materials, machine.getCylinderAmount(), structure);
            materialList.addAll(materials);
        }
        XSSFWorkbook workbook = buildExcelWorkbook(materialList, machine);
        return new Excel(machineName + ".xlsx", workbook);
    }

    private void dataHandler(List<Material> materialList, int cylinderAmount, Structure structure) {
        Material[] materArray = new Material[12];
        for (int index = 0; index < materialList.size(); index++) {
            Material material = materialList.get(index);
            material.setStructureNo(structure.getStructureNo());
            materArray[material.getLevel()] = material;
            if (0 == material.getLevel()) {
                material.setAmount(structure.getAmount());
                material.setAbsoluteAmount(structure.getAmount());
            } else {
                Integer parentAmount = materArray[material.getLevel() - 1].getAmount();
                if (null != material.getAbsoluteAmount()) {
                    material.setAmount(parentAmount * material.getAbsoluteAmount());
                }
            }
            if (null != material.getSpareExp() && !"".equals(material.getSpareExp())) {
                int spare = spareAnalysis(cylinderAmount, material.getSpareExp());
                material.setSpare(spare);
            }
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Excel exportStructureExcel(Structure structure) {
        List<Material> materialList = materialRepository.findAllByAtNoAndAtRevisionAndVersion(
                structure.getMaterialNo(), structure.getRevision(), structure.getVersion()
        );
        for (Material material : materialList) {
            material.setStructureNo(structure.getStructureNo());
        }

        XSSFWorkbook workbook = buildExcelWorkbook(materialList, null);
        String name = structure.getStructureNo() + "_" + structure.getMaterialNo() + "." + structure.getRevision() + "_" + structure.getVersion() + ".xlsx";
        return new Excel(name, workbook);
    }

    private XSSFWorkbook buildExcelWorkbook(List<Material> materialList, Machine machine) {
        int i = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        if (null != machine) {
            XSSFRow row0 = sheet.createRow(i);
            XSSFCell cell00 = row0.createCell(0);
            cell00.setCellValue("机号");
            XSSFCell cell01 = row0.createCell(1);
            cell01.setCellValue(machine.getMachineNo());
            XSSFCell cell02 = row0.createCell(2);
            cell02.setCellValue("机型");
            XSSFCell cell03 = row0.createCell(3);
            cell03.setCellValue(machine.getType());
            XSSFCell cell04 = row0.createCell(4);
            cell04.setCellValue("船号");
            XSSFCell cell05 = row0.createCell(5);
            cell05.setCellValue(machine.getShipNo());
            XSSFCell cell06 = row0.createCell(6);
            cell06.setCellValue("船级社");
            XSSFCell cell07 = row0.createCell(7);
            cell07.setCellValue(machine.getClassificationSociety());
            i++;
            XSSFRow row1 = sheet.createRow(i);
            XSSFCell cell10 = row1.createCell(0);
            cell10.setCellValue("部套层次");
            XSSFCell cell13 = row1.createCell(3);
            cell13.setCellValue("物料信息");
            XSSFCell cell16 = row1.createCell(6);
            cell16.setCellValue("名称转换");
            XSSFCell cell18 = row1.createCell(8);
            cell18.setCellValue("材料转换");
            XSSFCell cell110 = row1.createCell(10);
            cell110.setCellValue("装机件");
            XSSFCell cell113 = row1.createCell(13);
            cell113.setCellValue("设计工艺信息");
            XSSFCell cell114 = row1.createCell(14);
            cell114.setCellValue("备件");
            i++;
        }

        XSSFRow row = sheet.createRow(i);
        XSSFCell cell0 = row.createCell(0);
        cell0.setCellValue("部套号");
        XSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("层次");
        XSSFCell cell2 = row.createCell(2);
        cell2.setCellValue("件号");
        XSSFCell cell3 = row.createCell(3);
        cell3.setCellValue("物料号");
        XSSFCell cell4 = row.createCell(4);
        cell4.setCellValue("图号");
        XSSFCell cell5 = row.createCell(5);
        cell5.setCellValue("图幅");
        XSSFCell cell6 = row.createCell(6);
        cell6.setCellValue("名称（英文）");
        XSSFCell cell7 = row.createCell(7);
        cell7.setCellValue("名称（中文）");
        XSSFCell cell8 = row.createCell(8);
        cell8.setCellValue("专利材料");
        XSSFCell cell9 = row.createCell(9);
        cell9.setCellValue("国标材料");
        XSSFCell cell10 = row.createCell(10);
        cell10.setCellValue("总数量");
        XSSFCell cell11 = row.createCell(11);
        cell11.setCellValue("数量");
        XSSFCell cell12 = row.createCell(12);
        cell12.setCellValue("单重");
        XSSFCell cell13 = row.createCell(13);
        cell13.setCellValue("更改");
        if (null == machine) {
            XSSFCell cell14 = row.createCell(14);
            cell14.setCellValue("专利方版本");
            XSSFCell cell15 = row.createCell(15);
            cell15.setCellValue("备件");
        } else {
            XSSFCell cell15 = row.createCell(14);
            cell15.setCellValue("数量");
        }
        i++;

        int size = materialList.size();
        for (int index = 0; index < size; index++) {
            Material material = materialList.get(index);
            XSSFRow tempRow = sheet.createRow(index + i);
            XSSFCell tempCell0 = tempRow.createCell(0);
            tempCell0.setCellValue(material.getStructureNo());
            XSSFCell tempCell1 = tempRow.createCell(1);
            if (null != material.getLevel()) {
                tempCell1.setCellValue(material.getLevel());
            }
            XSSFCell tempCell2 = tempRow.createCell(2);
            if (null != material.getPositionNo()) {
                tempCell2.setCellValue(material.getPositionNo());
            } else {
                tempCell2.setCellValue("0000");
            }
            XSSFCell tempCell3 = tempRow.createCell(3);
            if (null != material.getMaterialNo()) {
                tempCell3.setCellValue(material.getMaterialNo());
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
            if (null != material.getChinese()) {
                tempCell7.setCellValue(material.getChinese());
            } else {
                tempCell7.setCellValue("");
            }
            XSSFCell tempCell8 = tempRow.createCell(8);
            if (null != material.getMaterial()) {
                tempCell8.setCellValue(material.getMaterial());
            }
            XSSFCell tempCell9 = tempRow.createCell(9);
            tempCell9.setCellValue("*");
            XSSFCell tempCell11 = tempRow.createCell(11);
            if (null != material.getAbsoluteAmount()) {
                tempCell11.setCellValue(material.getAbsoluteAmount());
            }
            XSSFCell tempCell12 = tempRow.createCell(12);
            if (null != material.getWeight()) {
                tempCell12.setCellValue(material.getWeight());
            }
            XSSFCell tempCell13 = tempRow.createCell(13);
            if (null != material.getModifyNote()) {
                tempCell13.setCellValue(material.getModifyNote());
            }
            if (null == machine) {
                XSSFCell tempCell14 = tempRow.createCell(14);
                if (null != material.getRevision()) {
                    tempCell14.setCellValue(material.getRevision());
                }
                XSSFCell tempCell15 = tempRow.createCell(15);
                if (null != material.getSpareExp()) {
                    tempCell15.setCellValue(material.getSpareExp());
                }
            } else {
                if (null != material.getAmount()) {
                    XSSFCell tempCell10 = tempRow.createCell(10);
                    tempCell10.setCellValue(material.getAmount());
                }
                XSSFCell tempCell14 = tempRow.createCell(14);
                if (null != material.getSpare()) {
                    tempCell14.setCellValue(material.getSpare());
                }
            }
        }

        return workbook;
    }

    private int spareAnalysis(int cylinderAmount, String exp) {
        int mantissa = 0;
        int multiplier = 0;
        int plusIndex = -1;
        int nIndex = -1;
        if (exp.contains("n")) {
            nIndex = exp.indexOf("n");
            if (nIndex != 0) {
                multiplier = Integer.parseInt(exp.substring(0, nIndex));
            } else {
                multiplier = 1;
            }
        }
        if (exp.contains("+")) {
            plusIndex = exp.indexOf("+");
            mantissa = Integer.parseInt(exp.substring(plusIndex + 1, exp.length()));
        }
        return multiplier * cylinderAmount + mantissa;
    }
}