package com.example.mobile_client;


public class File_icon
{
    // initial file  context
    private String name;
    private int iconId;
    public File_icon(String name, int iconId)
    {
        this.name = name;
        this.iconId = iconId;
    }
    public String getName()
    {
        return name;
    }
    public  int getIconId()
    {
        return iconId;
    }
}

