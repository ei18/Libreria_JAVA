package controller;

import entity.Author;
import model.AuthorModel;

import javax.swing.*;
import java.util.List;

public class AuthorController {

    AuthorModel objAuthorModel;

    public AuthorController(){
        this.objAuthorModel = new AuthorModel();
    }

    public void update() {
        //Listamos
        String listAuthors = this.getAll(this.objAuthorModel.findAllAuthor());

        //Pedimos el id
        int idUpdate = Integer.parseInt(JOptionPane.showInputDialog(listAuthors +"\nEnter the ID of the author to edit"));

        //Verificamos el id
        Author objAuthor = (Author) this.objAuthorModel.findByIdAuthor(idUpdate);

        if (objAuthor == null) {
            JOptionPane.showMessageDialog(null,"Author not found.");
        }else {
            String name = JOptionPane.showInputDialog(null,"Enter new name", objAuthor.getName());
            String nationality = JOptionPane.showInputDialog(null,"Enter new nationality", objAuthor.getNationality());

            objAuthor.setName(name);
            objAuthor.setNationality(nationality);

            this.objAuthorModel.updateAuthor(objAuthor);
        }
    }
    public void findByName(){

        String nameAuthor = JOptionPane.showInputDialog(null, "Enter the name of the author for search");

        String list = "List Author: \n";

        for (Object obj : this.objAuthorModel.findByNameAuthor(nameAuthor)){

            Author objAuthor = (Author) obj;
            list += objAuthor.toString() + "\n";
        }

        JOptionPane.showMessageDialog(null, list);
    }
    public void delete() {
        String listaAuthorString = this.getAll(this.objAuthorModel.findAllAuthor());

        int confirm = 1;
        int idDelete = Integer.parseInt(JOptionPane.showInputDialog(listaAuthorString + " Enter the ID of the author to delete"));
        Author objAuthor = (Author) this.objAuthorModel.findByIdAuthor(idDelete);

        if (objAuthor == null) {
            JOptionPane.showMessageDialog(null," Author not found.");
        }else {
            confirm = JOptionPane.showConfirmDialog(null,"Are your sure want to delete the author: \n" + objAuthor.toString());

            //Si el usuario escogió que sí, entonces eliminamos
            if (confirm == 0) {
                this.objAuthorModel.deleteAuthor(objAuthor);
            }
        }
    }
    public void getAll(){

        String list = this.getAll(this.objAuthorModel.findAllAuthor());

        //Mostramos toda lista
        JOptionPane.showMessageDialog(null, list);

    }
    public String getAll(List<Object> listObject) {
        String list = "List Authors \n";

        //Iteramos sobre la lista que devuelve el método find All
        for (Object obj : listObject){

            // Convertimos o castemos el objeto tipo Object a un Author
            Author objAuthor = (Author) obj;

            //Concatenamos la información
            list += objAuthor.toString()+ "\n";
        }
        return list;

    }
    public void create(){

        Author objAuthor = new Author();

        String name = JOptionPane.showInputDialog("Insert Author name: ");
        String nationality = JOptionPane.showInputDialog("Insert Author nationality: ");

        objAuthor.setName(name);
        objAuthor.setNationality(nationality);

        objAuthor = (Author) this.objAuthorModel.insertAuthor(objAuthor);

        JOptionPane.showMessageDialog(null, objAuthor.toString());

    }
}
