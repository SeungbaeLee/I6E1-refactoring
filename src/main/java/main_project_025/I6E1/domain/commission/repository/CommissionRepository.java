package main_project_025.I6E1.domain.commission.repository;

import main_project_025.I6E1.domain.commission.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long> {
}