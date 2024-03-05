package main_project_025.I6E1.domain.review.repository;

import main_project_025.I6E1.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByCommissionId(long commissionId, Pageable pageable);
}
