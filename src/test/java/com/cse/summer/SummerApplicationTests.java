package com.cse.summer;

import com.cse.summer.domain.Material;
import com.cse.summer.domain.Name;
import com.cse.summer.domain.Structure;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.NameRepository;
import com.cse.summer.repository.StructureRepository;
import com.cse.summer.util.Generator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SummerApplicationTests {

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private NameRepository nameRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void findExistStructure() {
        Structure structure = structureRepository.findExistStructure("YB-682", "21-0002", "1", "10");
        System.out.println(structure);
    }

    @Test
    public void findStructureMaterial() {
        List<Material> materialList = materialRepository.findAllByMaterialNo("5109547-9");
        System.out.println(materialList);
    }

//    @Test
//    public void addNameRelation() {
//        InputStream in = null;
//        try {
//            in = new FileInputStream(new File("name.xlsx"));
//            Workbook workbook = WorkbookFactory.create(in);
//            Sheet sheet = workbook.getSheet("Dictionary");
//            List<Name> names = new ArrayList<>();
//            int index = 0;
//            for (Row row : sheet) {
//                if (index < 1) {
//                    index++;
//                } else {
//                    Name name = new Name();
//                    name.setObjectId(Generator.getObjectId());
//                    if (null != row.getCell(0)) {
//                        name.setEnglish(row.getCell(0).toString());
//                    } else {
//                        name.setEnglish("");
//                    }
//                    if (null != row.getCell(1)) {
//                        name.setChinese(row.getCell(1).toString());
//                    } else {
//                        name.setChinese("");
//                    }
//                    names.add(name);
//                }
//            }
//            nameRepository.saveAll(names);
//        } catch (InvalidFormatException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }
//        }
//    }
}
