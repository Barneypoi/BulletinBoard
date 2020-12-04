package com.example.myapplication;

import java.io.Serializable;

public class News implements Serializable {
    public String id;
    public String title;
    public String author;
    public String publishTime;
    public int type;
    public String cover;
    public String[] covers;

}
