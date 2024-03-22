package entity;

import java.util.Date;

public class Book {
    private int idBook;
    private String title;
    private Date yearPublication;
    private double price;
    private int id;

    public Book() {

    }

    public Book(int idBook, String title, Date yearPublication, double price, int id) {
        this.idBook = idBook;
        this.title = title;
        this.yearPublication = yearPublication;
        this.price = price;
        this.id = id;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public java.sql.Date getYearPublication() {
        return (java.sql.Date) yearPublication;
    }

    public void setYearPublication(Date yearPublication) {
        this.yearPublication = yearPublication;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + idBook +
                ", title='" + title + '\'' +
                ", yearPublication=" + yearPublication +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
