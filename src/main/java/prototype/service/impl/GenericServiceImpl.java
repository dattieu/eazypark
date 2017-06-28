package prototype.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import prototype.dao.GenericDao;
import prototype.service.GenericService;

@Service("genericService")
public class GenericServiceImpl<T, V> implements GenericService<T, V> {

	private GenericDao<T, V> genericDao;
	 
	public GenericServiceImpl() {}
	
	public GenericServiceImpl(GenericDao<T, V> genericDao) {
		this.genericDao = genericDao;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAll() {
		return genericDao.getAll();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T getByKey(String key, V value) {
		return genericDao.getByKey(key, value);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T getById(int id) {
		return genericDao.getById(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(T object) {
		genericDao.save(object);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T object) {
		genericDao.delete(object);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T object) {
		genericDao.update(object);
	}

}
