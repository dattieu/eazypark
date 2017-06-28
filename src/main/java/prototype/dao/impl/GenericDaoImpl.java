package prototype.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import prototype.dao.GenericDao;

@SuppressWarnings("unchecked")
@Repository("genericDao")
public abstract class GenericDaoImpl<T, V extends Serializable> implements GenericDao<T, V> {

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Class<? extends T> classType;
   
	public GenericDaoImpl() {
		ParameterizedType paramType = (ParameterizedType) getClass().getGenericSuperclass();
		classType = (Class<? extends T>) paramType.getActualTypeArguments()[0];
	}

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    protected Criteria getCriteria() {
    	return getSession().createCriteria(classType);
    }
    
    public List<T> getAll() {
    	return getCriteria().list();
    }
	
	// a.k.a getByEntityProperty
    public T getByKey(final String key, final V value) {
    	return (T) getCriteria().add(Restrictions.eq(key, value)).uniqueResult();
    }
	
    public T getById(int id) {
		return (T) getSession().get(classType, id);
    }
	
    public void save(final T object) {
    	getSession().persist(object);
    }

    public void delete(final T object) {
    	getSession().delete(object);
    }

	public void update(final T object) {
		getSession().saveOrUpdate(object);
	}

}
