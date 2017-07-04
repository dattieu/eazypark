package prototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import prototype.service.ReviewService;

@RestController
public class ReviewController {
	
	private static final String REVIEW = "/reviews/{parkId}";
	
	private final ReviewService reviewService;
	
	@Autowired
	public ReviewController(ReviewService reviewService) {
	this.reviewService = reviewService;	
	}
	
	@PostMapping(REVIEW)
	public void createNewReview(@PathVariable("parkId") String parkId) {
		// TODO like Payment
	}
	

}
