package prototype.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import prototype.model.Park;
import prototype.model.Review;
import prototype.service.ParkService;
import prototype.service.ReviewService;

@Service("reviewService")
public class ReviewServiceImpl extends GenericServiceImpl<Review, String> implements ReviewService {

	private final ParkService parkService;
	
	public ReviewServiceImpl(ParkService parkService) {
		this.parkService = parkService;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void createNewReview(int parkId, Review review) {
		// REVIEW only able to save review by updating the parent (park object) 
		Park dbPark = parkService.getById(parkId);
		if(dbPark == null) {
			throw new EntityNotFoundException("Park with id " + parkId + " not found");
		}
		
		dbPark.addReview(review);
		parkService.update(dbPark);
	}
	
}
