package services;

import models.User;
import utils.MyJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserService {
    public static Connection cnn = MyJDBC.cnn();
    public static Scanner scanner = new Scanner(System.in);
    public static User getUser(String searchBy){
        System.out.println("Enter the client's "+searchBy+" :");
        String searchValue = scanner.nextLine();

        String getUserSql = "select * from user where "+searchBy+" = ?";

        try{
            PreparedStatement userSt = cnn.prepareStatement(getUserSql);
            userSt.setString(1, searchValue);
            ResultSet resultSet = userSt.executeQuery();
            if(!resultSet.next()) {System.out.println("!!! Client not found !!!"); return null;}
            User user = new User(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setCin(resultSet.getString("cin"));

            return user;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
