package repositories;

import utils.MyJDBC;
import models.User;
import java.sql.*;

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
