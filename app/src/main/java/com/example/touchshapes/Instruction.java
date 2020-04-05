/**
 * Written by Kushagra Gupta for CS6326 04/03/20
 *
 * This module has methods to return random shape and color.
 */
package com.example.touchshapes;

import java.util.Random;

public class Instruction {
    private final String[] colorsList = { "Red", "Orange", "Yellow", "Green", "Blue", "Purple", "White"};
    private final String[] shapesList = { "Square", "Circle" };
    private Random r =  new Random();
    // Accessible method to get shape
    public String getShape(){
        return shapeType();
    }
    // Accessible method to get color
    public String getColor(){
        return shapeColor();
    }
    // Generate random color
    private String shapeColor(){
        int i = r.nextInt(colorsList.length);
        return colorsList[i];
    }
    // Generate random shape
    private String shapeType(){
        int i = r.nextInt(shapesList.length);
        return shapesList[i];
    }
}
