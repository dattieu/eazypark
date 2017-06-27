package prototype.dao.impl;

import org.springframework.stereotype.Repository;

import prototype.dao.ReviewDao;
import prototype.model.Review;

@Repository("reviewDao")
public class ReviewDaoImpl extends GenericDaoImpl<Review, String> implements ReviewDao {

}
