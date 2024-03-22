package database;

import entity.Author;

import java.util.List;

public interface CRUDAuthor {
    public Object insertAuthor (Object object);
    public boolean updateAuthor (Object object);
    public boolean deleteAuthor (Object object);
    public List<Object> findAllAuthor();
    public Object findByIdAuthor(int id);
    public List<Author> findByNameAuthor(String name);
}
