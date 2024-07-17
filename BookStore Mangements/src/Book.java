/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Y.CHHAPORNROTH
 */
class Book {
    private String id;
    private String title;
    private String author;
    private int stock;
    private String date;

    public Book(String id, String title, String author, int stock, String date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getStock() {
        return stock;
    }

    public String getDate() {
        return date;
    }
}