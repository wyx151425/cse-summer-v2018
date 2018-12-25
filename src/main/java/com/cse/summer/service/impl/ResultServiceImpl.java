package com.cse.summer.service.impl;

import com.cse.summer.domain.ImportResult;
import com.cse.summer.repository.ResultRepository;
import com.cse.summer.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author WangZhenqi
 */
@Service(value = "resultService")
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    @Autowired
    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<ImportResult> findImportResultList(String machineName) {
        return resultRepository.findAllByMachineName(machineName);
    }
}
