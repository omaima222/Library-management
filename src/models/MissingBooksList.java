
package models;


public class MissingBooksList {
    public int id;
    private int quantity;
    private Book book;

    private User user;

    private String missing_date;
    public MissingBooksList(int id){
        this.id = id;
    }

    // Getters

    public int getQuantity(){
        return  this.quantity;
    }

    public Book getBook(){
        return  this.book;
    }

    public User getUser(){
        return  this.user;
    }

    public String getMissingDate(){
        return  this.missing_date;
    }


    // Setters

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setBook(Book book){
        this.book = book;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setMissingDate(String missing_date){
        this.missing_date = missing_date;
    }
}
