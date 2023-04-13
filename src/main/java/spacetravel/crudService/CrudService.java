package spacetravel.crudService;

/**
 *  T - type of Entity
 *  I - Primary Key for Entity
 * */

public interface CrudService<T, I> {
    I create(T value);
    T read(I id);
    T update(T value);
    void delete(T value);
}
