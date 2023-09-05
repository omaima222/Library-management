package models;


public class User {

    public int id;
    private String firstName;
    private String lastName;
    private String cin;

    public User(int id){
        this.id = id;
    }

    // Getters

    public String getLastName(){
        return  this.lastName;
    }

    public String getFirstName(){
        return  this.firstName;
    }

    public String getCin(){
        return  this.cin;
    }

    // Setters

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setCin(String cin){
        this.cin = cin;
    }

    @Override
    public String toString(){
        return "id: "+ id+"\n"+"first name: "+firstName+"\n"+"last name: "+lastName+"\n"+"cin: "+cin;
    }

}
