package com.cse.summer.service.impl;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.domain.*;
import com.cse.summer.domain.Name;
import com.cse.summer.repository.MachineRepository;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.NameRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.service.FileService;
import com.cse.summer.util.Generator;
import com.cse.summer.util.StatusCode;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
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

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

    // 从数据库中英文名称参照表中查找中文名称
    private String findChineseName(String englishName, List<Name> names) {
        for (Name name : names) {
            if (name.getEnglish().equals(englishName)) {
                return name.getChinese();
            }
        }
        return "";
    }

    // 新建机器通用方法
    private Machine createNewMachine(String machineName) {
        Machine machine = new Machine();
        machine.setObjectId(Generator.getObjectId());
        machine.setStatus(1);
        machine.setName(machineName);
        machine.setMachineNo("");
        machine.setType("");
        machine.setCylinderAmount(0);
        machine.setShipNo("");
        machine.setClassificationSociety("");
        return machine;
    }

    private Structure createNewStructure(String machineName) {
        Structure structure = new Structure();
        structure.setObjectId(Generator.getObjectId());
        structure.setStatus(1);
        structure.setMachineName(machineName);
        return structure;
    }

    private Material createNewMaterial() {
        Material material = new Material();
        material.setObjectId(Generator.getObjectId());
        material.setStatus(1);
        material.setLevel(-1);
        material.setPositionNo("");
        material.setMaterialNo("");
        material.setDrawingNo("");
        material.setDrawingSize("");
        material.setName("");
        material.setChinese("");
        material.setMaterial("*");
        material.setStandard("*");
        material.setWeight("");
        material.setSource("*");
        material.setSpareExp("");
        material.setSpareSrc("");
        material.setDesignNote("");
        material.setPaintProtect("");
        material.setModifyNote("");
        material.setErpParent("");
        return material;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importCSEBOM(String machineName, MultipartFile file) throws InvalidFormatException, IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheet("整机BOM表");
        List<Material> materialList = new ArrayList<>(1000);
        List<Structure> structureList = new ArrayList<>(100);

        cseBOMExcelProcess(machineName, sheet, materialList, structureList);

        // 检查机器是否存在
        Machine targetMachine = machineRepository.findMachineByName(machineName);
        if (null == targetMachine) {
            Machine machine = createNewMachine(machineName);
            machineRepository.save(machine);
        }

        structureRepository.saveAll(structureList);
        materialRepository.saveAll(materialList);
    }

    private void cseBOMExcelProcess(String machineName, Sheet sheet, List<Material> materialList, List<Structure> structureList) {
        // 用于维护部套层级的数组
        Material[] levelArr = new Material[12];
        // Workbook行索引
        int index = 0;
        for (Row row : sheet) {
            // 前三行数据为机器信息及字段的批注，所以不予解析
            if (index < 3) {
                index++;
            } else {
                if (null == row.getCell(3)) {
                    break;
                }
                String levelStr = row.getCell(1).toString();
                if ("".equals(levelStr)) {
                    throw new SummerException(StatusCode.MATERIAL_LEVEL_BLANK);
                }
                int level = (int) Double.parseDouble(levelStr);
                String materialNo = row.getCell(3).toString();
                int latestVersion = 0;
                if (0 == level) {
                    // 如果通过该部套顶层物料的物料号和专利方版本查询到库中存在部套，则为库中的部套升级最新版本
                    List<Material> materials = materialRepository.findAllByAtNo(materialNo);
                    if (materials.size() > 0) {
                        int oldLatestVersion = materials.get(0).getLatestVersion();
                        latestVersion = oldLatestVersion + 1;
                        for (int i = 0; i < materials.size(); i++) {
                            materials.get(i).setLatestVersion(latestVersion);
                        }
                        materialRepository.saveAll(materials);
                    }
                    // 保存该部套
                    Structure structure = createNewStructure(machineName);
                    structure.setStructureNo(row.getCell(0).toString());
                    structure.setMaterialNo(materialNo);
                    structure.setVersion(latestVersion);
                    if (null != row.getCell(11) && !"".equals(row.getCell(11).toString())) {
                        structure.setAmount((int) Double.parseDouble(row.getCell(11).toString()));
                    }
                    structureList.add(structure);
                }
                Material material = createNewMaterial();
                material.setVersion(latestVersion);
                material.setLatestVersion(latestVersion);
                material.setChildCount(0);
                material.setLevel(level);
                if (null != row.getCell(2)) {
                    material.setPositionNo(row.getCell(2).toString());
                }
                material.setMaterialNo(materialNo);
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
                if (null != row.getCell(9)) {
                    material.setStandard(row.getCell(9).toString());
                }
                if (null != row.getCell(12)) {
                    material.setSource(row.getCell(12).toString());
                }
                if (null != row.getCell(13)) {
                    material.setWeight(row.getCell(13).toString());
                }
                if (null != row.getCell(14)) {
                    material.setSpareExp(row.getCell(14).toString());
                }
                if (null != row.getCell(15)) {
                    material.setSpareSrc(row.getCell(15).toString());
                }
                if (null != row.getCell(16)) {
                    material.setDesignNote(row.getCell(16).toString());
                }
                if (null != row.getCell(17)) {
                    material.setPaintProtect(row.getCell(17).toString());
                }
                if (null != row.getCell(18)) {
                    material.setModifyNote(row.getCell(18).toString());
                }
                if (null != row.getCell(19)) {
                    material.setErpParent(row.getCell(19).toString());
                }
                if (0 == level) {
                    material.setAbsoluteAmount(1);
                } else {
                    if (null == row.getCell(10) || "".equals(row.getCell(10).toString())) {
                        material.setAbsoluteAmount(0);
                    } else {
                        material.setAbsoluteAmount((int) Double.parseDouble(row.getCell(10).toString()));
                    }
                }
                materialList.add(material);

                if (0 == level) {
                    // 最上层节点时不设置父节点
                    levelArr[0] = material;
                    material.setAtNo(materialNo);
                } else {
                    Material parentMat = levelArr[level - 1];
                    parentMat.setChildCount(parentMat.getChildCount() + 1);
                    // 根据最上级节点设置所属
                    material.setAtNo(levelArr[0].getMaterialNo());
                    material.setVersion(levelArr[0].getVersion());
                    material.setLatestVersion(levelArr[0].getLatestVersion());
                    // 设置该节点所属上级节点的ID
                    material.setParentId(parentMat.getObjectId());
                    // 将该节点覆盖数组中相同层级的上一个节点
                    levelArr[level] = material;
                }
            }
        }
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

        xmlRecursiveTraversal(root.element("designSpec"), materialList, structureList, null, machineName, -1, null, names);

        Machine targetMachine = machineRepository.findMachineByName(machineName);
        if (null == targetMachine) {
            Machine machine = createNewMachine(machineName);
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
    private void xmlRecursiveTraversal(Element
                                               element, List<Material> materialList, List<Structure> structureList,
                                       String parentId, String machineName, int parentLevel, String atNo, List<Name> names) {
        if ("designSpec".equals(element.getName())) {
            logger.info("装置号id: " + element.attributeValue("id"));
            Element revision = element.element("revision");
            Element modules = revision.element("moduleList");
            List<Element> moduleList = modules.elements("module");
            for (Element module : moduleList) {
                xmlRecursiveTraversal(module, materialList, structureList, null, machineName, parentLevel, null, names);
            }
        } else if ("module".equals(element.getName())) {
            Element revision = element.element("revision");
            String structNoM = revision.element("structureNo").getText();
            String materNoM = element.attributeValue("id");
            String revisionM = revision.attributeValue("revision");
            String materialNo = materNoM + "." + revisionM;
            // 判断部套是否存在，不存在的情况下才需要保存该部套信息
            Structure targetStruct = structureRepository.findExistStructure(machineName, structNoM, materialNo);
            // 如果部套不存在，则保存部套，并判断库中是否有部套对应的物料，物料存在则重用库中物料，不存在则新建物料
            // 如果部套已经存在，那么物料一定也存在了，那么该部套对应的物料所有的子节点都不需要遍历了
            if (null == targetStruct) {
                targetStruct = createNewStructure(machineName);
                targetStruct.setStructureNo(structNoM);
                targetStruct.setMaterialNo(materialNo);
                targetStruct.setVersion(0);

                // 判断待保存的物料是否存在
                List<Material> materials = materialRepository.findAllByMaterialNoAndLevel(materialNo, 0);
                // 集合大于0表示物料存在
                if (0 == materials.size()) {
                    // 如果物料不存在，则需要保存物料的数据，并遍历物料的所有子节点
                    Material material = createNewMaterial();
                    material.setAtNo(materialNo);
                    int level = parentLevel + 1;
                    material.setLevel(level);
                    material.setVersion(0);
                    material.setLatestVersion(0);
                    material.setMaterialNo(materialNo);
                    material.setName(element.element("name").getText());
                    String chineseName = findChineseName(element.element("name").getText(), names);
                    material.setChinese(chineseName);
                    material.setPage(revision.element("noOfPages").getText());
                    material.setWeight(revision.element("mass").getText());
                    material.setAbsoluteAmount(1);
                    materialList.add(material);
                    Element parts = revision.element("partList");
                    int childCount = xmlPartsRecursiveTraversal(parts, materialList, material.getObjectId(), machineName, level, materialNo, names);
                    material.setChildCount(childCount);
                }
                targetStruct.setAmount((int) Double.parseDouble(revision.element("quantity").getText()));
                structureList.add(targetStruct);
            }
        } else if ("part".equals(element.getName())) {
            Material material = createNewMaterial();
            material.setAtNo(atNo);
            int level = parentLevel + 1;
            material.setLevel(level);
            material.setVersion(0);
            material.setLatestVersion(0);
            material.setParentId(parentId);

            String materNoM = element.attributeValue("id");
            Element revision = element.element("revision");
            String revisionM = revision.attributeValue("revision");
            String materialNo = materNoM + "." + revisionM;

            material.setMaterialNo(materialNo);
            material.setName(element.element("name").getText());
            String chineseName = findChineseName(element.element("name").getText(), names);
            material.setChinese(chineseName);

            if (null != revision.element("mass")) {
                material.setWeight(revision.element("mass").getText());
            }
            if (null != revision.element("quantity")) {
                // 可能存在数量为1.00的形式，所以字符串先转为double再转为int
                material.setAbsoluteAmount((int) Double.parseDouble(revision.element("quantity").getText()));
            }
            if (null != revision.element("drawingSize")) {
                material.setDrawingSize(revision.element("drawingSize").getText());
            }
            if (null != revision.element("material")) {
                material.setMaterial(revision.element("material").getText());
            }
            if (null != revision.element("posNo")) {
                material.setPositionNo(revision.element("posNo").getText().substring(1));
            } else {
                material.setPositionNo("000");
            }
            if (null != revision.element("sequenceNo")) {
                material.setSequenceNo(revision.element("sequenceNo").getText());
            }
            materialList.add(material);
            Element parts = revision.element("partList");
            int childCount = xmlPartsRecursiveTraversal(parts, materialList, material.getObjectId(), machineName, level, atNo, names);

            material.setChildCount(childCount);
        } else if ("standardPart".equals(element.getName()) || "document".equals(element.getName())
                || "supDrawing".equals(element.getName()) || "licData".equals(element.getName())) {
            Material material = createNewMaterial();
            material.setAtNo(atNo);
            int level = parentLevel + 1;
            material.setLevel(level);
            material.setVersion(0);
            material.setLatestVersion(0);
            material.setParentId(parentId);

            String materNoM = element.attributeValue("id");
            Element revision = element.element("revision");
            String revisionM = revision.attributeValue("revision");
            String materialNo = materNoM + "." + revisionM;

            material.setMaterialNo(materialNo);
            material.setName(element.element("name").getText());
            String chineseName = findChineseName(element.element("name").getText(), names);
            material.setChinese(chineseName);

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
                material.setPositionNo(revision.element("posNo").getText().substring(1));
            } else {
                material.setPositionNo("000");
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
    private int xmlPartsRecursiveTraversal(Element parts, List<Material> materialList, String parentId, String
            machineName, int parentLevel, String atNo, List<Name> names) {
        int childCount = 0;
        if (null != parts) {
            List<Element> otherParts = new ArrayList<>();
            List<Element> partList = parts.elements("part");
            List<Element> standardParts = parts.elements("standardPart");
            List<Element> documents = parts.elements("document");
            List<Element> supDrawings = parts.elements("supDrawing");
            List<Element> licData = parts.elements("licData");
            if (partList.size() > 0) {
                otherParts.addAll(partList);
            }
            if (standardParts.size() > 0) {
                otherParts.addAll(standardParts);
            }
            if (documents.size() > 0) {
                otherParts.addAll(documents);
            }
            if (supDrawings.size() > 0) {
                otherParts.addAll(supDrawings);
            }
            if (licData.size() > 0) {
                otherParts.addAll(licData);
            }
            childCount = otherParts.size();

            otherParts.sort(Comparator.comparing(o -> o.element("revision").element("sequenceNo").getText()));

            for (Element element : otherParts) {
                xmlRecursiveTraversal(element, materialList, null, parentId, machineName, parentLevel, atNo, names);
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
            Machine machine = createNewMachine(machineName);
            machineRepository.save(machine);
        }

        structureRepository.saveAll(structureList);
        materialRepository.saveAll(materialList);
    }

    private void winGDExcelProcess(String machineName, Workbook
            workbook, List<Material> materialList, List<Structure> structureList, List<Name> names) {
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
                String materialNo = materNo + materVersion;
                // 获取该节点层级，从0开始
                int level = row.getCell(0).toString().length() / 3 - 1;
                if (0 == level) {
                    // 设为空，防止多个部套，部套名相同但物料号不同的情况
                    unImportMater = "";
                    String structNo = row.getCell(8).toString();
                    Structure targetStruct = structureRepository.findExistStructure(machineName, structNo, materialNo);

                    // 当部套不存在的时候就新创建部套
                    if (null == targetStruct) {
                        targetStruct = createNewStructure(machineName);
                        targetStruct.setStructureNo(structNo);
                        targetStruct.setMaterialNo(materialNo);
                        targetStruct.setAmount((int) Double.parseDouble(row.getCell(14).toString()));

                        // 当物料存在时就为部套设置物料最新的版本
                        List<Material> materials = materialRepository.findAllByMaterialNoAndLevel(materialNo, 0);
                        if (materials.size() > 0) {
                            // 当物料存在时，不需要再导入
                            unImportMater = materialNo;
                        }
                        targetStruct.setVersion(0);
                        structureList.add(targetStruct);
                    } else {
                        // 当该部套存在时，该部套存在的物料也已经存在，所以不需要导入
                        unImportMater = materialNo;
                    }
                }

                // 此处，即使部套已经存在也还需要遍历表，因为此处不是树级结构
                Material material = createNewMaterial();
                material.setVersion(0);
                material.setLatestVersion(0);
                material.setChildCount(0);
                material.setMaterialNo(materialNo);

                if (null != row.getCell(1)) {
                    material.setDrawingSize(row.getCell(1).toString());
                }
                material.setDrawingNo(row.getCell(2).toString() + row.getCell(3).toString());
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

                // 设置层级
                material.setLevel(level);
                if (0 == level) {
                    // 最上层节点时不设置父节点
                    levelArray[0] = material;
                    material.setAtNo(material.getMaterialNo());
                } else {
                    Material parentMat = levelArray[level - 1];
                    parentMat.setChildCount(parentMat.getChildCount() + 1);
                    // 根据最上级节点设置所属
                    material.setAtNo(levelArray[0].getMaterialNo());
                    // 设置该节点所属上级节点的ID
                    material.setParentId(parentMat.getObjectId());
                    // 将该节点覆盖数组中相同层级的上一个节点
                    levelArray[level] = material;
                }

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
    public void importNewStructureExcel(Structure structure, MultipartFile file) throws
            InvalidFormatException, IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Row structRow = workbook.getSheetAt(0).getRow(4);
        String materNo = structRow.getCell(3).toString();
        // 根据物料号和专利方版本检查
        List<Material> materials = materialRepository.findAllByMaterialNoAndLevel(materNo, 0);
        if (materials.size() > 0) {
            throw new SummerException(StatusCode.STRUCTURE_EXIST);
        } else {
            List<Material> materialList = new ArrayList<>(100);
            newStructureAnalysis(workbook.getSheetAt(0), structure, materialList, 0);
            materialRepository.saveAll(materialList);

            structure.setObjectId(Generator.getObjectId());
            structure.setStatus(1);
            structure.setMaterialNo(materNo);
            structure.setVersion(0);
            structureRepository.save(structure);
        }
    }

    private void newStructureAnalysis(Sheet sheet, Structure structure, List<Material> materialList, int version) {
        Material[] levelArray = new Material[12];
        int index = 0;
        for (Row row : sheet) {
            if (index < 4) {
                index++;
            } else {
                if (null == row.getCell(3)) {
                    break;
                }

                Material material = createNewMaterial();
                material.setVersion(version);
                material.setLatestVersion(version);
                material.setMaterialNo(row.getCell(3).toString());
                material.setChildCount(0);

                // 解析物料层级
                String levelStr = row.getCell(1).toString();
                if ("".equals(levelStr)) {
                    throw new SummerException(StatusCode.MATERIAL_LEVEL_BLANK);
                }
                int level = (int) Double.parseDouble(levelStr);
                material.setLevel(level);
                if (0 == level) {
                    // 最上层节点时不设置父节点
                    levelArray[0] = material;
                    material.setAtNo(material.getMaterialNo());
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
                    // 将该节点覆盖数组中相同层级的上一个节点
                    levelArray[level] = material;
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
                if (null != row.getCell(9)) {
                    material.setStandard(row.getCell(9).toString());
                }
                if (null != row.getCell(10)) {
                    material.setAbsoluteAmount((int) Double.parseDouble(row.getCell(10).toString()));
                }
                if (null != row.getCell(12)) {
                    material.setSource(row.getCell(12).toString());
                }
                if (null != row.getCell(13)) {
                    material.setWeight(row.getCell(13).toString());
                }
                if (null != row.getCell(14)) {
                    material.setSpareExp(row.getCell(14).toString().replace(".0", ""));
                }
                if (null != row.getCell(15)) {
                    material.setSpareSrc(row.getCell(15).toString());
                }
                if (null != row.getCell(16)) {
                    material.setDesignNote(row.getCell(16).toString());
                }
                if (null != row.getCell(17)) {
                    material.setSpareSrc(row.getCell(17).toString());
                }
                if (null != row.getCell(18)) {
                    material.setModifyNote(row.getCell(18).toString());
                }
                if (null != row.getCell(19)) {
                    material.setErpParent(row.getCell(19).toString());
                }

                materialList.add(material);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importNewVersionStructureExcel(Structure structure, MultipartFile file) throws
            IOException, InvalidFormatException {
        // 开始解析新版本数据
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Row structRow = workbook.getSheetAt(0).getRow(4);
        String materNo = structRow.getCell(3).toString();

        // 若能查询到旧部套则更新旧部套的最新版本号
        int latestVersion;
        List<Material> oldMaterialList = materialRepository.findAllByAtNo(materNo);
        if (oldMaterialList.size() > 0) {
            int oldVersion = oldMaterialList.get(0).getLatestVersion();
            latestVersion = oldVersion + 1;
            // 不可以使用foreach 因为foreach循环是拷贝而不是引用同一个对象
            for (int index = 0; index < oldMaterialList.size(); index++) {
                oldMaterialList.get(index).setLatestVersion(latestVersion);
            }
            materialRepository.saveAll(oldMaterialList);
        } else {
            throw new SummerException(StatusCode.STRUCTURE_MATERIAL_NOT_EXIST);
        }

        List<Material> materialList = new ArrayList<>(100);
        Sheet sheet = workbook.getSheetAt(0);
        newStructureAnalysis(sheet, structure, materialList, latestVersion);
        materialRepository.saveAll(materialList);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Excel exportMachineExcel(String machineName, Integer status) {
        Machine machine = machineRepository.findMachineByName(machineName);
        List<Structure> structureList;
        if (0 == status) {
            structureList = structureRepository.findAllByMachineNameAndStatusGreaterThanEqualOrderByStructureNo(machineName, 1);
        } else {
            structureList = structureRepository.findAllByMachineNameAndStatusOrderByStructureNo(machineName, status);
        }
        List<Material> materialList = new ArrayList<>();
        for (Structure structure : structureList) {
            List<Material> materials = materialRepository.findAllByAtNoAndVersion(structure.getMaterialNo(), structure.getVersion());
            amountAndSpareHandler(materials, machine.getCylinderAmount(), structure);
            materialList.addAll(materials);
        }
        XSSFWorkbook workbook = buildExcelWorkbook(materialList, machine);
        return new Excel(machineName + ".xlsx", workbook);
    }

    private void amountAndSpareHandler(List<Material> materialList, int cylinderAmount, Structure structure) {
        Material[] materArray = new Material[12];
        for (int index = 0; index < materialList.size(); index++) {
            Material material = materialList.get(index);
            material.setStructureNo(structure.getStructureNo());
            materArray[material.getLevel()] = material;
            if (0 == material.getLevel()) {
                material.setAmount(structure.getAmount());
                material.setAbsoluteAmount(structure.getAmount());
            }
            if (0 != material.getLevel() && null != materArray[material.getLevel() - 1].getAmount()) {
                Integer parentAmount = materArray[material.getLevel() - 1].getAmount();
                if (null != material.getAbsoluteAmount()) {
                    material.setAmount(parentAmount * material.getAbsoluteAmount());
                }
            }
            if (!"".equals(material.getSpareExp())) {
                int spare = spareAnalysis(cylinderAmount, material.getSpareExp());
                material.setSpare(spare);
            }
        }
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Excel exportStructureExcel(User user, Structure structure) {
        List<Material> materialList = materialRepository.findAllByAtNoAndVersion(structure.getMaterialNo(), structure.getVersion());
        for (Material material : materialList) {
            material.setStructureNo(structure.getStructureNo());
        }

        XSSFWorkbook workbook = buildExcelWorkbook(materialList, null);
        String name = structure.getStructureNo() + "_" + structure.getMaterialNo() + "_" + structure.getVersion() + ".xlsx";
        return new Excel(name, workbook);
    }

    private XSSFWorkbook buildExcelWorkbook(List<Material> materialList, Machine machine) {
        int i = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
//        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

        // 设置文本对齐方向
        XSSFCellStyle direct = workbook.createCellStyle();
        direct.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        direct.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        direct.setFillForegroundColor(new XSSFColor(new Color(197, 217, 241)));
        direct.setBorderTop(XSSFCellStyle.BORDER_THIN);
        direct.setBorderRight(XSSFCellStyle.BORDER_THIN);
        direct.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        direct.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        direct.setFont(font);

        // 设置文本对齐方向
        XSSFCellStyle centerBlue = workbook.createCellStyle();
        centerBlue.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        centerBlue.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        centerBlue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        centerBlue.setFillForegroundColor(new XSSFColor(new Color(197, 217, 241)));
        centerBlue.setBorderTop(XSSFCellStyle.BORDER_THIN);
        centerBlue.setBorderRight(XSSFCellStyle.BORDER_THIN);
        centerBlue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        centerBlue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        centerBlue.setFont(font);

        XSSFCellStyle center = workbook.createCellStyle();
        center.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        center.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        center.setBorderTop(XSSFCellStyle.BORDER_THIN);
        center.setBorderRight(XSSFCellStyle.BORDER_THIN);
        center.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        center.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        center.setFont(font);

        XSSFCellStyle border = workbook.createCellStyle();
        border.setBorderTop(XSSFCellStyle.BORDER_THIN);
        border.setBorderRight(XSSFCellStyle.BORDER_THIN);
        border.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        border.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        border.setFont(font);

        XSSFCellStyle blue = workbook.createCellStyle();
        blue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        blue.setFillForegroundColor(new XSSFColor(new Color(197, 217, 241)));
        blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
        blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
        blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        blue.setFont(font);

        XSSFCellStyle green = workbook.createCellStyle();
        green.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        green.setFillForegroundColor(new XSSFColor(new Color(146, 208, 80)));
        green.setBorderTop(XSSFCellStyle.BORDER_THIN);
        green.setBorderRight(XSSFCellStyle.BORDER_THIN);
        green.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        green.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        green.setFont(font);

        XSSFCellStyle common = workbook.createCellStyle();
        common.setBorderTop(XSSFCellStyle.BORDER_THIN);
        common.setBorderRight(XSSFCellStyle.BORDER_THIN);
        common.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        common.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        common.setFont(font);

        XSSFSheet sheet;
        if (null == machine) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet("整机BOM表");
        }
        sheet.setColumnWidth(0, 77 * 32);
        sheet.setColumnWidth(1, 101 *32);
        sheet.setColumnWidth(2, 37 * 32);
        sheet.setColumnWidth(3, 117 * 32);
        sheet.setColumnWidth(4, 117 * 32);
        sheet.setColumnWidth(5, 25 * 32);
        sheet.setColumnWidth(6, 149 * 32);
        sheet.setColumnWidth(7, 149 * 32);
        sheet.setColumnWidth(8, 101 * 32);
        sheet.setColumnWidth(9, 101 * 32);
        sheet.setColumnWidth(10, 37 * 32);
        sheet.setColumnWidth(11, 37 * 32);
        sheet.setColumnWidth(12, 37 * 32);
        sheet.setColumnWidth(13, 61 * 32);
        sheet.setColumnWidth(14, 37 * 32);
        sheet.setColumnWidth(15, 37 * 32);
        sheet.setColumnWidth(16, 149 * 32);
        sheet.setColumnWidth(17, 101 * 32);
        sheet.setColumnWidth(18, 101 * 32);
        sheet.setColumnWidth(19, 101 * 32);

        if (null == machine) {
            XSSFRow row0 = sheet.createRow(i);
            XSSFCell cell0 = row0.createCell(0);
            cell0.setCellValue("文件号");
            cell0.setCellStyle(blue);
            XSSFCell cell1 = row0.createCell(1);
            cell1.setCellValue(materialList.get(0).getMaterialNo());
            cell1.setCellStyle(border);
            XSSFCell cell2 = row0.createCell(2);
            cell2.setCellValue("版本");
            cell2.setCellStyle(blue);
            XSSFCell cell3 = row0.createCell(3);
            cell3.setCellValue("" + materialList.get(0).getVersion());
            cell3.setCellStyle(border);
            XSSFCell cell4 = row0.createCell(4);
            cell4.setCellValue("编制");
            cell4.setCellStyle(blue);
            XSSFCell cell5 = row0.createCell(5);
            cell5.setCellStyle(border);
            XSSFCell cell6 = row0.createCell(6);
            cell6.setCellValue("校对");
            cell6.setCellStyle(blue);
            XSSFCell cell7 = row0.createCell(7);
            cell7.setCellStyle(border);
            XSSFCell cell8 = row0.createCell(8);
            cell8.setCellValue("审核");
            cell8.setCellStyle(blue);
            XSSFCell cell9 = row0.createCell(9);
            cell9.setCellStyle(border);
            i++;

            XSSFRow row1 = sheet.createRow(i);
            XSSFCell cell10 = row1.createCell(0);
            cell10.setCellValue("机型");
            cell10.setCellStyle(blue);
            XSSFCell cell11 = row1.createCell(1);
            cell11.setCellStyle(border);
            row1.createCell(2);
            row1.createCell(3);
            i++;

            cell4.setCellStyle(centerBlue);
            CellRangeAddress cra4 = new CellRangeAddress(0, 1, 4, 4);
            setBorderForMergeCell(CellStyle.BORDER_THIN, cra4, sheet, workbook);
            sheet.addMergedRegion(cra4);

            cell5.setCellStyle(center);
            CellRangeAddress cra5 = new CellRangeAddress(0, 1, 5, 5);
            setBorderForMergeCell(CellStyle.BORDER_THIN, cra5, sheet, workbook);
            sheet.addMergedRegion(cra5);

            cell6.setCellStyle(centerBlue);
            CellRangeAddress cra6 = new CellRangeAddress(0, 1, 6, 6);
            setBorderForMergeCell(CellStyle.BORDER_THIN, cra6, sheet, workbook);
            sheet.addMergedRegion(cra6);

            cell7.setCellStyle(center);
            CellRangeAddress cra7 = new CellRangeAddress(0, 1, 7, 7);
            setBorderForMergeCell(CellStyle.BORDER_THIN, cra7, sheet, workbook);
            sheet.addMergedRegion(cra7);

            cell8.setCellStyle(centerBlue);
            CellRangeAddress cra8 = new CellRangeAddress(0, 1, 8, 8);
            setBorderForMergeCell(CellStyle.BORDER_THIN, cra8, sheet, workbook);
            sheet.addMergedRegion(cra8);

            cell9.setCellStyle(center);
            CellRangeAddress cra9 = new CellRangeAddress(0, 1, 9, 9);
            setBorderForMergeCell(CellStyle.BORDER_THIN, cra9, sheet, workbook);
            sheet.addMergedRegion(cra9);
        } else {
            sheet.protectSheet(Generator.getReadonlyPassword());
            XSSFRow row0 = sheet.createRow(i);
            XSSFCell cell00 = row0.createCell(0);
            cell00.setCellValue("机号");
            cell00.setCellStyle(blue);
            XSSFCell cell01 = row0.createCell(1);
            cell01.setCellValue(machine.getMachineNo());
            cell01.setCellStyle(border);
            XSSFCell cell02 = row0.createCell(2);
            cell02.setCellValue("机型");
            cell02.setCellStyle(blue);
            XSSFCell cell03 = row0.createCell(3);
            cell03.setCellValue(machine.getType());
            cell03.setCellStyle(border);
            XSSFCell cell04 = row0.createCell(4);
            cell04.setCellValue("船号");
            cell04.setCellStyle(blue);
            XSSFCell cell05 = row0.createCell(5);
            cell05.setCellValue(machine.getShipNo());
            cell05.setCellStyle(border);
            XSSFCell cell06 = row0.createCell(6);
            cell06.setCellValue("船级社");
            cell06.setCellStyle(blue);
            XSSFCell cell07 = row0.createCell(7);
            cell07.setCellValue(machine.getClassificationSociety());
            cell07.setCellStyle(border);
            i++;
        }

        XSSFRow row1 = sheet.createRow(i);

        XSSFCell cell10 = row1.createCell(0);
        cell10.setCellValue("部套层次");
        cell10.setCellStyle(direct);
        // 参数依次为：起始行，终止行，起始列，终止列
        CellRangeAddress cra0 = new CellRangeAddress(i, i, 0, 2);
        setBorderForMergeCell(CellStyle.BORDER_THIN, cra0, sheet, workbook);
        sheet.addMergedRegion(cra0);

        XSSFCell cell13 = row1.createCell(3);
        cell13.setCellValue("物料信息");
        cell13.setCellStyle(direct);
        CellRangeAddress cra3 = new CellRangeAddress(i, i, 3, 5);
        setBorderForMergeCell(CellStyle.BORDER_THIN, cra3, sheet, workbook);
        sheet.addMergedRegion(cra3);

        XSSFCell cell16 = row1.createCell(6);
        cell16.setCellValue("名称转换");
        cell16.setCellStyle(direct);
        CellRangeAddress cra6 = new CellRangeAddress(i, i, 6, 7);
        setBorderForMergeCell(CellStyle.BORDER_THIN, cra6, sheet, workbook);
        sheet.addMergedRegion(cra6);

        XSSFCell cell18 = row1.createCell(8);
        cell18.setCellValue("材料转换");
        cell18.setCellStyle(direct);
        CellRangeAddress cra8 = new CellRangeAddress(i, i, 8, 9);
        setBorderForMergeCell(CellStyle.BORDER_THIN, cra8, sheet, workbook);
        sheet.addMergedRegion(cra8);

        XSSFCell cell110 = row1.createCell(10);
        cell110.setCellValue("装机件");
        cell110.setCellStyle(direct);
        CellRangeAddress cra10 = new CellRangeAddress(i, i, 10, 13);
        setBorderForMergeCell(CellStyle.BORDER_THIN, cra10, sheet, workbook);
        sheet.addMergedRegion(cra10);

        XSSFCell cell114 = row1.createCell(14);
        cell114.setCellValue("备件");
        cell114.setCellStyle(direct);
        CellRangeAddress cra14 = new CellRangeAddress(i, i, 14, 15);
        setBorderForMergeCell(CellStyle.BORDER_THIN, cra14, sheet, workbook);
        sheet.addMergedRegion(cra14);

        XSSFCell cell116 = row1.createCell(16);
        cell116.setCellValue("设计工艺信息");
        cell116.setCellStyle(direct);
        CellRangeAddress cra16 = new CellRangeAddress(i, i, 16, 19);
        setBorderForMergeCell(CellStyle.BORDER_THIN, cra16, sheet, workbook);
        sheet.addMergedRegion(cra16);
        i++;

        XSSFRow row = sheet.createRow(i);
        XSSFCell cell20 = row.createCell(0);
        cell20.setCellValue("部套");
        XSSFCell cell21 = row.createCell(1);
        cell21.setCellValue("层次");
        XSSFCell cell22 = row.createCell(2);
        cell22.setCellValue("件号");
        XSSFCell cell23 = row.createCell(3);
        cell23.setCellValue("物料号");
        XSSFCell cell24 = row.createCell(4);
        cell24.setCellValue("图号");
        XSSFCell cell25 = row.createCell(5);
        cell25.setCellValue("图幅");
        XSSFCell cell26 = row.createCell(6);
        cell26.setCellValue("名称（英文）");
        XSSFCell cell27 = row.createCell(7);
        cell27.setCellValue("名称（中文）");
        XSSFCell cell28 = row.createCell(8);
        cell28.setCellValue("专利材料");
        XSSFCell cell29 = row.createCell(9);
        cell29.setCellValue("国标材料");
        XSSFCell cell210 = row.createCell(10);
        cell210.setCellValue("层数量");
        XSSFCell cell211 = row.createCell(11);
        cell211.setCellValue("总数量");
        XSSFCell cell212 = row.createCell(12);
        cell212.setCellValue("货源");
        XSSFCell cell213 = row.createCell(13);
        cell213.setCellValue("重量");
        XSSFCell cell214 = row.createCell(14);
        cell214.setCellValue("数量");
        XSSFCell cell215 = row.createCell(15);
        cell215.setCellValue("货源");
        XSSFCell cell216 = row.createCell(16);
        cell216.setCellValue("设计备注");
        XSSFCell cell217 = row.createCell(17);
        cell217.setCellValue("喷漆防护");
        XSSFCell cell218 = row.createCell(18);
        cell218.setCellValue("更改");
        XSSFCell cell219 = row.createCell(19);
        cell219.setCellValue("ERP-父项");
        for (Cell cell : row) {
            cell.setCellStyle(blue);
        }
        i++;

        int size = materialList.size();
        for (int index = 0; index < size; index++) {
            Material material = materialList.get(index);
            XSSFRow tempRow = sheet.createRow(index + i);
            XSSFCell tempCell0 = tempRow.createCell(0);
            tempCell0.setCellValue(material.getStructureNo());

            // 将层级数字转为带有空格的层级字符串
            int level = material.getLevel();
            StringBuilder space = new StringBuilder();
            for (int j = 0; j < level; j++) {
                space.append(" ");
                space.append(" ");
            }
            String levelStr = space.append(level).toString();
            XSSFCell tempCell1 = tempRow.createCell(1);
            tempCell1.setCellValue(levelStr);

            // 设置部件号，0层设置部套号，其它层时，直接设置部件号
            XSSFCell tempCell2 = tempRow.createCell(2);
            if (0 == level) {
                tempCell2.setCellValue(material.getStructureNo());
            } else {
                tempCell2.setCellValue(material.getPositionNo());
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
            tempCell5.setCellValue(material.getDrawingSize());
            XSSFCell tempCell6 = tempRow.createCell(6);
            tempCell6.setCellValue(material.getName());
            XSSFCell tempCell7 = tempRow.createCell(7);
            tempCell7.setCellValue(material.getChinese());
            XSSFCell tempCell8 = tempRow.createCell(8);
            tempCell8.setCellValue(material.getMaterial());
            XSSFCell tempCell9 = tempRow.createCell(9);
            tempCell9.setCellValue(material.getStandard());

            XSSFCell tempCell10 = tempRow.createCell(10);
            if (null != material.getAbsoluteAmount()) {
                tempCell10.setCellValue(String.valueOf(material.getAbsoluteAmount()));
            }
            XSSFCell tempCell11 = tempRow.createCell(11);
            if (null != material.getAmount()) {
                tempCell11.setCellValue(String.valueOf(material.getAmount()));
            }
            XSSFCell tempCell12 = tempRow.createCell(12);
            tempCell12.setCellValue(material.getSource());
            XSSFCell tempCell13 = tempRow.createCell(13);
            tempCell13.setCellValue(material.getWeight());
            XSSFCell tempCell14 = tempRow.createCell(14);
            if (null == machine) {
                if (!"".equals(material.getSpareExp())) {
                    tempCell14.setCellValue(material.getSpareExp());
                }
            } else {
                if (null != material.getSpare()) {
                    tempCell14.setCellValue(String.valueOf(material.getSpare()));
                }
            }
            XSSFCell tempCell15 = tempRow.createCell(15);
            tempCell15.setCellValue(material.getSpareSrc());
            XSSFCell tempCell16 = tempRow.createCell(16);
            tempCell16.setCellValue(material.getDesignNote());
            XSSFCell tempCell17 = tempRow.createCell(17);
            tempCell17.setCellValue(material.getPaintProtect());
            XSSFCell tempCell18 = tempRow.createCell(18);
            tempCell18.setCellValue(material.getModifyNote());
            XSSFCell tempCell19 = tempRow.createCell(19);
            tempCell19.setCellValue(material.getErpParent());

            if (0 == level) {
                for (Cell cell : tempRow) {
                    cell.setCellStyle(green);
                }
            } else {
                for (Cell cell : tempRow) {
                    cell.setCellStyle(common);
                }
            }
        }
        return workbook;
    }

    private static int spareAnalysis(int cylinderAmount, String exp) {
        int mantissa = 0;
        int multiplier = 0;
        int plusIndex = -1;
        int nIndex = -1;
        if (exp.contains("n")) {
            nIndex = exp.indexOf("n");
            if (nIndex > 0) {
                multiplier = Integer.parseInt(exp.substring(0, nIndex));
            } else if (0 == nIndex) {
                multiplier = 1;
            }
            if (exp.contains("+")) {
                plusIndex = exp.indexOf("+");
                mantissa = Integer.parseInt(exp.substring(plusIndex + 1, exp.length()));
            }
        } else {
            mantissa = (int) Double.parseDouble(exp);
        }
        return multiplier * cylinderAmount + mantissa;
    }

    private void setBorderForMergeCell(int i, CellRangeAddress cellRangeTitle, Sheet sheet, XSSFWorkbook workbook) {
        RegionUtil.setBorderBottom(i, cellRangeTitle, sheet, workbook);
        RegionUtil.setBorderLeft(i, cellRangeTitle, sheet, workbook);
        RegionUtil.setBorderRight(i, cellRangeTitle, sheet, workbook);
        RegionUtil.setBorderTop(i, cellRangeTitle, sheet, workbook);
    }
}