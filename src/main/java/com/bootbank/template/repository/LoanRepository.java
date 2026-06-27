package com.bootbank.template.repository;

import com.bootbank.template.model.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByCif(String cif);
}
