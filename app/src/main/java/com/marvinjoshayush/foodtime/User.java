package com.marvinjoshayush.foodtime;


import java.util.ArrayList;

public class User{

    public String firstname,lastname,email;
    public String choices;

    public User(){
    }

    public User(String firstname,String lastname,String email){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getChoices() {
        return choices;
    }



    public void setChoices(String preference) {
        this.choices = preference;
    }


}
