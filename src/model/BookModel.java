package model;

import database.CRUDBook;
import database.ConfigDB;
import entity.Author;
import entity.Book;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookModel implements CRUDBook {
    private BookModel objBookModel;

    @Override
    public Object insertBook(Object object) {

        //1. Abrir conexión
        Connection objConnection = ConfigDB.openConnection();

        //2. Castear el objeto
        Book objBook = (Book) object;

        try {
            //3. Crear el SQL
            String sql = "INSERT INTO books (title, year_publication, price) VALUES (?, ?, ?)";

            //4. Preparar el statement
            PreparedStatement objPrepare = (PreparedStatement) objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            //5. Asignar los ?
            objPrepare.setString(1, objBook.getTitle());
            objPrepare.setDate(2, objBook.getYearPublication());
            objPrepare.setDouble(3, objBook.getPrice());

            //6. Ejecutamos el Query
            objPrepare.execute();

            //7. Obtener el resultado
            ResultSet objResult = objPrepare.getGeneratedKeys();
            while (objResult.next()) {
                objBook.setId(objResult.getInt(1));
            }

            //8. Cerramos el prepareStatement
            objPrepare.close();
            JOptionPane.showMessageDialog(null," Book insertion was successful.");

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error adding Book " + e.getMessage());
        }
        //9. Cerramos la conexión
        ConfigDB.closeConnection();
        return objBook;
    }

    @Override
    public boolean updateBook(Object object) {

        //1. Abrir la conexión
        Connection objConecction = ConfigDB.openConnection();

        //2. Convertir el objeto
        Book objBook = (Book) object;

        //3. Variable bandera para saber si se actualizó
        boolean isUpdated = false;

        try {
            //4. Creamos la sentencia SQL
            String sql = "UPDATE books SET title = ?, year_publication = ?, price = ? WHERE id_books = ?;";

            //5. Preparamos el statement
            PreparedStatement objPrepare = objConecction.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            //6. Dar valor a los ? parámetros de Query
            objPrepare.setString(1, objBook.getTitle());
            objPrepare.setDate(2, objBook.getYearPublication());
            objPrepare.setDouble(3, objBook.getPrice());


            //7. Ejecutamos el query
            int rowAffected = objPrepare.executeUpdate();
            if (rowAffected > 0) {
                isUpdated = true;
                JOptionPane.showMessageDialog(null,"The update was successful.");
            }

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }finally {
            //8. Cerrar la conexión
            ConfigDB.closeConnection();
        }

        return isUpdated;
    }

    @Override
    public boolean deleteBook(Object object) {
        //1. Convertir el objeto a la entidad
        Book objBook = (Book) object;

        //2. Variable boolena para medir el estado de la eliminación
        boolean isDeleted = false;

        //3. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();

        try {
            //4. Escribir la sentencia SQL
            String sql = "DELETE FROM books WHERE books.id_books = ?;";

            //5. Preparamos el statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            //6. Asignamos el valor al ?
            objPrepare.setInt(1, objBook.getId());

            //7. ExecuteUpdate devuelve la cantidad de filas afectadas por la sentencia SQL ejecutada.
            int totalAffectedRows = objPrepare.executeUpdate();

            if (totalAffectedRows > 0) {
                isDeleted = true;
                JOptionPane.showMessageDialog(null,"The delete was successful.");
            }

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        //8. Cerrar la conexión
        ConfigDB.closeConnection();

        return isDeleted;
    }

    @Override
    public List<Object> findAllBook() {

        //1. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();

        //2. Inicializar la lista donde se guardaran los registros que devuelve la BD
        List<Object> listBooks = new ArrayList<>();


        try{
            //3. Escribir la sentencia SQL
            String sql = "SELECT * FROM books ORDER BY books.id_books ASC;";

            //4. Utilizar PrepareStatement
            PreparedStatement objPrepareStatement = (PreparedStatement) objConnection.prepareStatement(sql);

            //5. Ejecutar el Query o el prepare
            ResultSet objResult = (ResultSet) objPrepareStatement.executeQuery();

            //6. Obtener los resultados
            while (objResult.next()){
                //Creamos una instacia de book
                Book objBook = new Book();

                //Llenamos nuestro objeto con lo que devuelve la base de datos (ResultSet)
                objBook.setId(objResult.getInt("id_book"));
                objBook.setTitle(objResult.getString("title"));
                objBook.setYearPublication(objResult.getDate("year_publication"));
                objBook.setPrice(objResult.getDouble("price"));

                //Finalmente agregar el book a la lista
                listBooks.add(objBook);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data acquisition Error");
        }

        //7. Cerramos la conexión
        ConfigDB.closeConnection();

        return listBooks;
    }

    @Override
    public Object findByIdBook(int id) {

        //1. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();
        Book objBook = null;

        try {
            //2. Sentencia SQL
            String sql = "SELECT * FROM books WHERE id_books = ?;";

            //3. Preparar el statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);

            //4. Damos valor al ?
            objPrepare.setInt(1,id);

            //5. Ejecutamos el query
            ResultSet objResult = objPrepare.executeQuery();

            //6. Mientras haya un registro siguiente entonces
            while (objResult.next()) {
                objBook = new Book();
                objBook.setId(objResult.getInt("id_books"));
                objBook.setTitle(objResult.getString("title"));
                objBook.setYearPublication(objResult.getDate("year_publication"));
                objBook.setPrice(objResult.getDouble("price"));
            }

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        //7. Cerramos la conexión
        ConfigDB.closeConnection();

        return objBook;
    }

    @Override
    public List<Book> findByNameBook(String name) {

        //1. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();
        List<Book> listBook = new ArrayList<>();

        try {
            //2. Sentencia SQL
            String sql = "SELECT * FROM books WHERE title_books LIKE ?;";

            //3. Preparar el statement
            PreparedStatement objPreparedStatement = objConnection.prepareStatement(sql);

            //4. Damos valor al ?
            objPreparedStatement.setString(1, "%" + name + "%");

            //5. Ejecutamos el query
            ResultSet objResult = objPreparedStatement.executeQuery();

            //6. Mientras haya un registro siguiente entonces
            while (objResult.next()) {

                Book objBook = new Book();
                objBook.setId(objResult.getInt("id_books"));
                objBook.setTitle(objResult.getString("title"));
                objBook.setYearPublication(objResult.getDate("year_publication"));
                objBook.setPrice(objResult.getDouble("price"));
                listBook.add(objBook);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "The book does not exist");
        } finally {
            //7. Cerramos la conexión
            ConfigDB.closeConnection();
        }

        return listBook;

    }

    @Override
    public List<Author> findByNameAuthor(String name) {

        //1. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();
        List<Author> listAuthor = new ArrayList<>();

        try {
            //2. Sentencia SQL
            String sql = "SELECT * FROM authors WHERE name_author LIKE ?;";

            //3. Preparar el statement
            PreparedStatement objPreparedStatement = objConnection.prepareStatement(sql);

            //4. Damos valor al ?
            objPreparedStatement.setString(1, "%" + name + "%");

            //5. Ejecutamos el query
            ResultSet objResult = objPreparedStatement.executeQuery();

            //6. Mientras haya un registro siguiente entonces
            while (objResult.next()) {

                Author objAuthor = new Author();
                objAuthor.setId(objResult.getInt("id_authors"));
                objAuthor.setName(objResult.getString("name_author"));
                objAuthor.setNationality(objResult.getString("nationality"));
                listAuthor.add(objAuthor);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "The author does not exist");
        } finally {
            //7. Cerramos la conexión
            ConfigDB.closeConnection();
        }

        return listAuthor;

    }

}