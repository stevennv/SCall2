package com.example.admin.scall.model;

/**
 * Created by Admin on 11/22/2017.
 */

public class InfoStyle {
    public InfoStyle(int id, String name, String font, int colorName, int size) {
        this.id = id;
        this.name = name;
        this.font = font;
        this.colorName = colorName;
        this.size = size;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String font;
    private int colorName;
    private int size;


    public InfoStyle() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getColorName() {
        return colorName;
    }

    public void setColorName(int colorName) {
        this.colorName = colorName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
