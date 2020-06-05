package com.harsh.application.newsdaily;

public class NewsData {

    private String mTitle;
    private String mDescription;

    public NewsData(String title,String Description){

        mDescription=Description;
        mTitle=title;

    }

    public String getTitle(){
        return mTitle;
    }
    public String getDescription(){
        return mDescription;
    }


}
