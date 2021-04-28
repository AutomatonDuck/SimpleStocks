package com.example.simplestocks;

import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public ArrayList favorites;
    public User(){

    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public void setFavs(ArrayList favorites){
        this.favorites = favorites;
    }
}
