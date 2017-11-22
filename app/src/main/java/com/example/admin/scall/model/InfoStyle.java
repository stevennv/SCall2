package com.example.admin.scall.model;

/**
 * Created by Admin on 11/22/2017.
 */

public class InfoStyle {
    public InfoStyle(String id, String name, String font, int colorName, int size) {
        this.id = id;
        this.name = name;
        this.font = font;
        this.colorName = colorName;
        this.size = size;
    }

    private String id;
    private String name;
    private String font;
    private int colorName;
    private int size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
