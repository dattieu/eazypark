package prototype.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.model.Review;
import prototype.service.ReviewService;

// TODO implement Spring Aspect for validation in all controllers and also for logging 
@RestController
public class ReviewController {
	
	private static final String REVIEW = "parks/{parkId}/reviews";
	
	private final ReviewService reviewService;
	
	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;	
	}
	
	@PostMapping(REVIEW)
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewReview(@PathVariable(value = "parkId") String parkId, @RequestBody @Valid Review review, BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_REVIEW);
		}
		reviewService.createNewReview(Integer.parseInt(parkId), review);
	}
	
}
