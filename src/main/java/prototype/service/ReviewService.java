package prototype.service;

import prototype.model.Review;

public interface ReviewService extends GenericService<Review, String> {
	
	void createNewReview(int parkId, Review review);
	
}
