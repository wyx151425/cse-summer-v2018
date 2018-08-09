package com.cse.summer;

import com.cse.summer.domain.Material;
import com.cse.summer.domain.Structure;
import com.cse.summer.repository.MaterialRepository;
import com.cse.summer.repository.StructureRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SummerApplicationTests {

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private MaterialRepository materialRepository;

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
}
