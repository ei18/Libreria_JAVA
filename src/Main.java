import controller.AuthorController;
import database.ConfigDB;
import entity.Author;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        ConfigDB.openConnection();

        AuthorController objAuthorController = new AuthorController();

        String option = "";

        do {
            option = JOptionPane.showInputDialog("""
                        MENU
                        1. Menu authors
                        2. Menu books
                        3. EXIT
                        Choose an option
                    """);

            switch (option) {

                case "1":
                    String option2;
                    do {
                        option2 = JOptionPane.showInputDialog("""
                            MENU AUTHORS
                            1. Created a new author.
                            2. Finding all author.
                            3. Finding author for name
                            4. Updated author.
                            5. Delete author.
                            6. EXIT.                        
                            """);

                        switch (option2){
                            case "1":
                                objAuthorController.create();
                                break;

                            case "2":
                                objAuthorController.getAll();
                                break;

                            case "3":
                                objAuthorController.findByName();
                                break;

                            case "4":
                                objAuthorController.update();
                                break;

                            case "5":
                                objAuthorController.delete();
                                break;
                        }

                    }while (!option2.equals("6"));
                    break;

                case "2":
                    String option3;
                    do {
                        option3 = JOptionPane.showInputDialog("""
                            MENU BOOKS
                            1. Created a new book.
                            2. Finding author.
                            3. Updated author.
                            4. Delete author.
                            5. EXIT.                        
                            """);

                        switch (option3){
                            case "1":

                                break;

                            case "2":
                                //objAuthorController.create();
                                break;

                            case "3":

                                break;

                            case "4":

                                break;

                            case "5":

                                break;
                        }

                    }while (!option3.equals("5"));
                    break;

            }

        } while (!option.equals("3"));


    }
}
