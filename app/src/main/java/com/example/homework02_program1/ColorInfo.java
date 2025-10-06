package com.example.homework02_program1;

import android.graphics.Color;

public class ColorInfo {
    private String hex;
    private int red, green, blue;

    ColorInfo(String hex, int red, int green, int blue) {
        this.hex = hex;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public String getHex() {
        return hex;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
