package com.cse.summer.repository;

import com.cse.summer.domain.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 王振琦
 */
@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer> {
}
