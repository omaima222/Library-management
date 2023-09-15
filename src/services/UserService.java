package services;

import models.Book;
import models.User;
import repositories.BookDao;
import repositories.UserDao;
import utils.MyJDBC;
import utils.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {

     public static ArrayList<User> getAllUsers(){
         return UserDao.getUsers("1");
     }

     public static boolean addUser(){
         // input cin
         String cin = Validation.validateStr("cin");
         // check if its unique
         User user =  UserDao.getUser("cin='"+cin+"'");
         if(user!=null){
             System.out.println("!!! The user "+user.getFirstName()+" "+user.getLastName()+" already has this cin !!!");
             return false;
         }

         // input first name
         String firstName = Validation.validateStr("first name");

         // input last name
         String lastName = Validation.validateStr("last name");

         return UserDao.addUser(cin,firstName,lastName);
     }

}
