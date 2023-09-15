
package models;

import java.util.Date;

public class BorrowingList {

    public int id;
    private int quantity;
    private Date borrowingDate;
    private Date returnDate;
    private Book book;
    private User user;

    public BorrowingList(int id){
        this.id = id;
    }

    // Getters

    public int getId(){ return this.id; }

    public Date getBorrowingDate(){
        return this.borrowingDate;
    }

    public Date getReturnDate(){
        return this.returnDate;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public User getUser(){
        return this.user;
    }

    public Book getBook(){
        return this.book;
    }

    // Setters

    public void setBorrowingDate(Date borrowingDate){
        this.borrowingDate = borrowingDate;
    }

    public void setReturnDate(Date returnDate){this.returnDate = returnDate;}

    public void setQuantity(int quantity){this.quantity = quantity;}

    public void setUser(User user){this.user = user;}

    public void setBook(Book book){
        this.book = book;
    }


    @Override
    public String toString(){
        return user.getCin()+"                "+user.getFirstName()+" "+user.getLastName()+"                "+book.getTitle()
                +"                "+book.getISBNNumber()+"                "+borrowingDate+"                "+returnDate;
    }


}
