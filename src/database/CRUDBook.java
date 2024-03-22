package database;

import entity.Author;
import entity.Book;

import java.util.List;

public interface CRUDBook {
    public Object insertBook(Object object);
    public boolean updateBook(Object object);
    public boolean deleteBook(Object object);
    public List<Object> findAllBook();
    public Object findByIdBook(int id);
    public List<Book> findByNameBook(String name);
    public List<Author> findByNameAuthor(String name);
}
