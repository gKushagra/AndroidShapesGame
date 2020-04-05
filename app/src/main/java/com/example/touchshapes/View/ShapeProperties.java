/**
 * Written by Kushagra Gupta for CS6326 04/03/20
 * Model class for storing shape properties.
 */
package com.example.touchshapes.View;

public class ShapeProperties {
    public float top;
    public float left;
    public float width;
    public String type;
    public String color;

    public ShapeProperties(float top, float left, float width, String type, String color) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.type = type;
        this.color = color;
    }
}
