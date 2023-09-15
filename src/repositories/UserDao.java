package repositories;

import utils.MyJDBC;
import models.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    public static Connection cnn = MyJDBC.cnn();

    public static User getUser(String condition){

        String getUserSql = "select * from user where "+condition+" LIMIT 1";

        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery(getUserSql);
            if(!resultSet.next()) return null;
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

    public static ArrayList<User> getUsers(String condition){

        String getUserSql = "select * from user where "+condition;
        ArrayList<User> users = new ArrayList<>();
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery(getUserSql);
            while (resultSet.next()){
                User user = new User(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setCin(resultSet.getString("cin"));
                users.add(user);
            }
            if(users.isEmpty()) return null;
            return users;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addUser(String cin,String firstName,String lastName){
        String sql = "INSERT INTO user (cin, first_name, last_name) " +
                "VALUES (?,?,?)";

        try{
            PreparedStatement st = cnn.prepareStatement(sql);
            st.setString(1, cin);
            st.setString(2, firstName);
            st.setString(3, lastName);

            int row = st.executeUpdate();
            if(row == 1) return true;
            else return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static int countUsers(){
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("select * from user");
            int count = 0;
            while(resultSet.next()){
                count++;
            }
            return  count;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
