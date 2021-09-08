package com.knglord.project;
import android.graphics.Bitmap;
import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties

public class user {

    public String name;
    public String number;
    public String email;
    public String pass;
    public String add;
    //public Bitmap propic;

   // public void profilepic(Bitmap propic){}


    public user() {
    }

    public user(String name, String number, String email, String pass, String add) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.pass = pass;
        this.add = add;
        //this.propic=propic;
    }
}
