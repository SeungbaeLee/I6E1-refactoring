package main_project_025.I6E1.domain.tag.repository;

import main_project_025.I6E1.domain.tag.entity.CommissionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionTagRepository extends JpaRepository<CommissionTag, Long> {
}
