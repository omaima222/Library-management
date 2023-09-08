
package models;


public class MissingBooksList {
    public int id;
    private int quantity;
    private Book book;

    private User user;
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
}
