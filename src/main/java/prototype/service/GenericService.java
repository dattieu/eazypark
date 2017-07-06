package prototype.service;

import java.util.List;

public interface GenericService<T, V> {

	List<T> getAll() ;
	T getByKey(final String key, final V value);
	T getById(int id);
	void save(final T object);
	void delete(final T object);
	void update(final T object);
	T merge(final T object);
	
}
