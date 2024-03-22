package main_project_025.I6E1.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.domain.review.dto.ReviewDto;
import main_project_025.I6E1.domain.review.entity.Review;
import main_project_025.I6E1.domain.review.mapper.ReviewMapper;
import main_project_025.I6E1.domain.review.service.ReviewService;
import main_project_025.I6E1.global.Page.PageDto;
import main_project_025.I6E1.global.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper mapper;

    // CREATE
    @PostMapping
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity postReview(@Valid @RequestBody ReviewDto.Post post) {
        Review review = reviewService.createReview(mapper.reviewPostDtoToReview(post));

        ReviewDto.Response response = mapper.reviewToResponse(review);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // READ
    @GetMapping("/{review-id}")
    public ResponseEntity getReview(@PathVariable("review-id") long reviewId) throws BusinessException {
            Review review = reviewService.readReview(reviewId);
            ReviewDto.Response response = mapper.reviewToResponse(review);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // READ ALL BY commissionID
    // 페이지네이션
    @GetMapping
    public ResponseEntity getReviews(Pageable pageable,long commissionId) {
            Page<Review> reviewPage = reviewService.readReviews(pageable, commissionId);
            List<Review> reviewList = reviewPage.getContent();

            PageDto pageDto = new PageDto<>(mapper.reviewToResponses(reviewList), reviewPage);
            return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    //UPDATE
    @PatchMapping("/{review-id}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity patchReview(@PathVariable("review-id") long reviewId,
                                      @Valid @RequestBody ReviewDto.Patch patch) throws BusinessException {
            Review review = reviewService.updateReview(reviewId, mapper.reviewPatchDtoToReview(patch));

            ReviewDto.Response response = mapper.reviewToResponse(review);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{review-id}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity deleteReview(@PathVariable("review-id") long reviewId) throws BusinessException {

            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}