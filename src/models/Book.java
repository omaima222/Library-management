package models;

public class Book {

    public  int id;
    private String title;
    private String authorName;
    private Long ISBNNumber;
    private int quantity;

    public Book(int id) {
        this.id = id;
    }


    // Getters
    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthorName(){
        return this.authorName;
    }

    public Long getISBNNumber(){
        return this.ISBNNumber;
    }

    public int getQuantity(){
        return this.quantity;
    }

    // Setters

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }

    public void setISBNNumber(Long ISBNNumber){
        this.ISBNNumber = ISBNNumber;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return "* Id        : "+ id+"\n"+"* Title     : "+title+"\n"+"* Author    : "+authorName+"\n"+"* ISBN      : "+ISBNNumber+"\n"+"* Quantity  : "+quantity;
    }


}
