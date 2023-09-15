package controllers;

import services.BookService;
import services.UserService;
import utils.Console;

public class UserController {

    public static void getAllUsers(){
        Console.printUsers(UserService.getAllUsers(), "USERS");
    }

    public static void addUser(){
            System.out.println("|________________________________ ADD A USER ________________________________|");
        if(UserService.addUser()) {
              System.out.println("|_________________________ SUCCESSFULLY ADDED USER __________________________|");
        }else System.out.println("|________________________ !!!! OPERATION FAILED !!!! ________________________|");
    }

}
