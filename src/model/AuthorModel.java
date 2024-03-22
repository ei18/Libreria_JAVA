package model;

import database.CRUDAuthor;
import database.ConfigDB;
import entity.Author;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorModel implements CRUDAuthor {

    @Override
    public Object insertAuthor(Object object) {

        //1. Abrir conexión
        Connection objConnection = ConfigDB.openConnection();

        //2. Castear el objeto
        Author objAuthor = (Author) object;

        try {
            //3. Crear el SQL
            String sql = "INSERT INTO authors(name_author, nationality) VALUES (?, ?)";

            //4. Preparar el statement
            PreparedStatement objPrepare = (PreparedStatement) objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            //5. Asignar los ?
            objPrepare.setString(1, objAuthor.getName());
            objPrepare.setString(2, objAuthor.getNationality());

            //6. Ejecutamos el Query
            objPrepare.execute();

            //7. Obtener el resultado
            ResultSet objResult = objPrepare.getGeneratedKeys();
            while (objResult.next()) {
                objAuthor.setId(objResult.getInt(1));
            }

            //8. Cerramos el prepareStatement
            objPrepare.close();
            JOptionPane.showMessageDialog(null," Author insertion was successful.");

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error adding Author " + e.getMessage());
        }
        //9. Cerramos la conexión
        ConfigDB.closeConnection();
        return objAuthor;
    }

    @Override
    public boolean updateAuthor(Object object) {

        //1. Abrir la conexión
        Connection objConecction = ConfigDB.openConnection();

        //2. Convertir el objeto
        Author objAuthor = (Author) object;

        //3. Variable bandera para saber si se actualizó
        boolean isUpdated = false;

        try {
            //4. Creamos la sentencia SQL
            String sql = "UPDATE authors SET name_author = ?, nationality = ? WHERE id_authors = ?;";

            //5. Preparamos el statement
            PreparedStatement objPrepare = objConecction.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            //6. Dar valor a los ? parámetros de Query
            objPrepare.setString(1, objAuthor.getName());
            objPrepare.setString(2, objAuthor.getNationality());
            objPrepare.setInt(3, objAuthor.getId());


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
    public boolean deleteAuthor(Object object) {
        //1. Convertir el objeto a la entidad
        Author objAuthor = (Author) object;

        //2. Variable boolena para medir el estado de la eliminación
        boolean isDeleted = false;

        //3. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();

        try {
            //4. Escribir la sentencia SQL
            String sql = "DELETE FROM authors WHERE authors.id_authors = ?;";

            //5. Preparamos el statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            //6. Asignamos el valor al ?
            objPrepare.setInt(1, objAuthor.getId());

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
    public List<Object> findAllAuthor() {

        //1. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();

        //2. Inicializar la lista donde se guardaran los registros que devuelve la BD
        List<Object> listAuthors = new ArrayList<>();


        try{
            //3. Escribir la sentencia SQL
            String sql = "SELECT * FROM authors ORDER BY authors.id_authors ASC;";

            //4. Utilizar PrepareStatement
            PreparedStatement objPrepareStatement = (PreparedStatement) objConnection.prepareStatement(sql);

            //5. Ejecutar el Query o el prepare
            ResultSet objResult = (ResultSet) objPrepareStatement.executeQuery();

            //6. Obtener los resultados
            while (objResult.next()){
                //Creamos una instacia de author
                Author objAuthor = new Author();

                //Llenamos nuestro objeto con lo que devuelve la base de datos (ResultSet)
                objAuthor.setId(objResult.getInt("id_authors"));
                objAuthor.setName(objResult.getString("name_author"));
                objAuthor.setNationality(objResult.getString("nationality"));

                //Finalmente agregar el coder a la lista
                listAuthors.add(objAuthor);
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data acquisition Error");
        }

        //7. Cerramos la conexión
        ConfigDB.closeConnection();

        return listAuthors;
    }

    @Override
    public Object findByIdAuthor(int id) {

        //1. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();
        Author objAuthor = null;

        try {
            //2. Sentencia SQL
            String sql = "SELECT * FROM authors WHERE id_authors = ?;";

            //3. Preparar el statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);

            //4. Damos valor al ?
            objPrepare.setInt(1,id);

            //5. Ejecutamos el query
            ResultSet objResult = objPrepare.executeQuery();

            //6. Mientras haya un registro siguiente entonces
            while (objResult.next()) {
                objAuthor = new Author();
                objAuthor.setId(objResult.getInt("id_authors"));
                objAuthor.setName(objResult.getString("name_author"));
                objAuthor.setNationality(objResult.getString("nationality"));
            }

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        //7. Cerramos la conexión
        ConfigDB.closeConnection();

        return objAuthor;
    }

    @Override
    public List<Author> findByNameAuthor(String nameAuthor) {

        //1. Abrir la conexión
        Connection objConnection = ConfigDB.openConnection();
        List<Author> listAuthor = new ArrayList<>();

        try {
            //2. Sentencia SQL
            String sql = "SELECT * FROM authors WHERE name_author LIKE ?;";

            //3. Preparar el statement
            PreparedStatement objPreparedStatement = objConnection.prepareStatement(sql);

            //4. Damos valor al ?
            objPreparedStatement.setString(1, "%" + nameAuthor + "%");

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
