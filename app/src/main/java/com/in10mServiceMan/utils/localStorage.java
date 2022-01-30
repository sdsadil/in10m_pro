package com.in10mServiceMan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.in10mServiceMan.models.CompleteProfile;
import com.in10mServiceMan.models.CustomerUser;

import static android.content.Context.MODE_PRIVATE;

public class localStorage {

    private final String permanentData="permanentData";
    private final String userData="userdata";
    private final  String loginKey="userloginData";
    private final  String tokenKey="userToken";
    private final  String loggedInKey="loggedInKey";
    private final  String loginUserKey="loginUserKey";
    private final  String profileKey="profileKey";
    private final  String introKey="introKey";
    private Context context;
    public localStorage(Context context){
        this.context=context;
    }

    public void setUserLogin(){
        SharedPreferences.Editor editor=context.getSharedPreferences(userData,MODE_PRIVATE).edit();
        editor.putBoolean(loggedInKey,true);
        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences prefs=context.getSharedPreferences(userData, MODE_PRIVATE);
        return prefs.getBoolean(loggedInKey,false);
    }
    public void logoutUser(){
        SharedPreferences.Editor editor=context.getSharedPreferences(userData,MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    /*public void setLoginData(FormLoginData loginData)
    {
        Gson gson = new Gson();
        String mydata=gson.toJson(loginData);
        SharedPreferences.Editor editor=context.getSharedPreferences(userData,MODE_PRIVATE).edit();
        editor.putString(loginKey,mydata);
        editor.apply();
    }*/
  /*  public FormLoginData getLoginData(){
        SharedPreferences prefs=context.getSharedPreferences(userData, MODE_PRIVATE);
        String data=prefs.getString(loginKey,"");
        if(data.equals(""))
            return null;
        Gson gson=new Gson();
        return gson.fromJson(data,FormLoginData.class);
    }*/

    public void setToken(String token)
    {

        SharedPreferences.Editor editor=context.getSharedPreferences(userData,MODE_PRIVATE).edit();
        editor.putString(tokenKey,token);
        editor.apply();
    }
    public String getToken(){
        SharedPreferences prefs=context.getSharedPreferences(userData, MODE_PRIVATE);
        String data=prefs.getString(tokenKey,"");
        if(data.equals(""))
            return null;

        return data;
    }
    public void saveLoggedInUser(CustomerUser user)
    {
        Gson gson = new Gson();
        String mydata=gson.toJson(user);
        SharedPreferences.Editor editor=context.getSharedPreferences(userData,MODE_PRIVATE).edit();
        editor.putString(loginUserKey,mydata);
        editor.apply();
    }
    public CustomerUser getLoggedInUser(){
        SharedPreferences prefs=context.getSharedPreferences(userData, MODE_PRIVATE);
        String data=prefs.getString(loginUserKey,"");
        if(data.equals(""))
            return null;
        Gson gson=new Gson();
        return gson.fromJson(data,CustomerUser.class);
    }

    public void saveCompleteCustomer(CompleteProfile user)
    {
        Gson gson = new Gson();
        String mydata=gson.toJson(user);
        SharedPreferences.Editor editor=context.getSharedPreferences(userData,MODE_PRIVATE).edit();
        editor.putString(profileKey,mydata);
        editor.apply();
    }
    public CompleteProfile getCompleteCustomer(){
        SharedPreferences prefs=context.getSharedPreferences(userData, MODE_PRIVATE);
        String data=prefs.getString(profileKey,"");
        if(data.equals(""))
            return null;
        Gson gson=new Gson();
        return gson.fromJson(data,CompleteProfile.class);
    }

    public void setIntroDone(boolean b) {
        SharedPreferences.Editor editor=context.getSharedPreferences(permanentData,MODE_PRIVATE).edit();
        editor.putBoolean(introKey,b);
        editor.apply();
    }
    public boolean getIntroDone() {
        SharedPreferences prefs=context.getSharedPreferences(permanentData, MODE_PRIVATE);
        return prefs.getBoolean(introKey,false);
    }
}
