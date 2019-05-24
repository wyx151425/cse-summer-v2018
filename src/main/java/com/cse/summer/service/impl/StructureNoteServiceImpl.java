package com.cse.summer.service.impl;

import com.cse.summer.model.entity.StructureNote;
import com.cse.summer.repository.StructureNoteRepository;
import com.cse.summer.service.StructureNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author WangZhenqi
 */
@Service(value = "structureNoteService")
public class StructureNoteServiceImpl implements StructureNoteService {

    private final StructureNoteRepository structureNoteRepository;

    @Autowired
    public StructureNoteServiceImpl(StructureNoteRepository structureNoteRepository) {
        this.structureNoteRepository = structureNoteRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public StructureNote findStructureNoteByMaterialNoAndVersion(String materialNo, Integer version) {
        return structureNoteRepository.findOneByMaterialNoAndVersion(materialNo, version);
    }
}
