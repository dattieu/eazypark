package prototype.controller;

import javax.validation.Valid;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.model.Park;
import prototype.model.Review;
import prototype.service.ParkService;
import prototype.service.ReviewService;

// TODO implement Spring Aspect for validation in all controllers and also for logging 
@RestController
public class ReviewController {
	
	private static final String REVIEW = "/reviews/{parkId}";
	
	private final ReviewService reviewService;
	private final ParkService parkService;
	
	// REVIEW use Spring Aspect here to add a id obfuscation layer?
	private final Hashids idObfuscator;
	
	@Autowired
	public ReviewController(ReviewService reviewService, ParkService parkService, Hashids idObfuscator) {
		this.reviewService = reviewService;	
		this.parkService = parkService;
		this.idObfuscator = idObfuscator;
	}
	
	@ModelAttribute("review")
	public Review loadReviewWithParkid(@PathVariable("parkId") String parkId) {
		Review review = new Review();
		Park park = parkService.getById((int) idObfuscator.decode(parkId)[0]);
		park.addReview(review);
		return review;
	}
	
	@PostMapping(REVIEW)
	public void createNewReview(@Valid Review review, BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_REVIEW);
		}
		reviewService.save(review);
	}
	
}
