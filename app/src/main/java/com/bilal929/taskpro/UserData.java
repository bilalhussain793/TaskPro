package com.bilal929.taskpro;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    // section 1 for current signed in user
    static String userid = "";
    static String password = "";
    static String username = "";
    static String chatWith = "";
    static String chatName = "";
    static String accountType="";
    static String userEmail = "";
    static String userContact="";
    static String URL="https://notify-38a1e.firebaseio.com";

    // section 2 for the posted tasks in earn_money list

    static ArrayList<String> title=new ArrayList<String>();
    static ArrayList<String> userid_post=new ArrayList<String>();
    static ArrayList<String> location=new ArrayList<String>();
    static ArrayList<String> price=new ArrayList<String>();
    static ArrayList<String> k_key=new ArrayList<String>();
    static ArrayList<String> task_type=new ArrayList<String>();
    static ArrayList<String> task_desc=new ArrayList<String>();
    static int position=0;
    static String ttype="";


    void clear(){
        title.clear();
        userid_post.clear();
        location.clear();
        price.clear();
        k_key.clear();
        task_type.clear();
        task_desc.clear();
    }



}
