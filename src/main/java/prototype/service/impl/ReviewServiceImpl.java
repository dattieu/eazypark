package prototype.service.impl;

import org.springframework.stereotype.Service;

import prototype.model.Review;
import prototype.service.ReviewService;

@Service("reviewService")
public class ReviewServiceImpl extends GenericServiceImpl<Review, String> implements ReviewService {
	
}
