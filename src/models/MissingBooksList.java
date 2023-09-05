
package models;


public class MissingBooksList {
    public int id;
    private int quantity;
    private Book book;

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


    // Setters

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setBook(Book book){
        this.book = book;
    }
}
