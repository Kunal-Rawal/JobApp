package com.firstjobapp.review.impl;

import com.firstjobapp.company.Company;
import com.firstjobapp.company.CompanyService;
import com.firstjobapp.review.Review;
import com.firstjobapp.review.ReviewRepository;
import com.firstjobapp.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CompanyService companyService;
    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Review getReviewById(Long companyId, Long reviewId) {
        List<Review> reviews=reviewRepository.findByCompanyId(companyId);

        //converting list of reviews into streams then filtering the review id
        return reviews.stream().filter(review -> review.getId().equals(reviewId))
                .findFirst().orElse(null);

    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {

        if(companyService.getCompanyById(companyId)!=null){
            updatedReview.setCompany(companyService.getCompanyById(companyId));
            updatedReview.setId(reviewId);
            reviewRepository.save(updatedReview);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if(companyService.getCompanyById(companyId)!=null
        &&reviewRepository.existsById(reviewId)){
            Review review=reviewRepository.findById(reviewId).orElse(null);
            Company company=review.getCompany();
            company.getReviews().remove(review);
            companyService.updateCompany(company,companyId);
            review.setCompany(null);
            reviewRepository.deleteById(reviewId);
            return true;
        }else{
            return false;
        }
    }
}
