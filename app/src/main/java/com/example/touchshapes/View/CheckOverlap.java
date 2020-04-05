/**
 * Written by Kushagra Gupta for Assignment 5 for CS6326 04/03/20
 *
 * This module implements three methods.
 * One method implements the Algorithm to check if two shapes overlap. The Algorithm
 * is not working when Shape1 and Shape 2 are Circle and Square and working otherwise.
 * Second method has the algorithm to check if coordinates of touch lie within a
 * shape.
 * Third method just checks if the shape is removed from list.
 *
 */
package com.example.touchshapes.View;

import java.util.ArrayList;

public class CheckOverlap {

    // Accessible method to get the function to check overlapping
    public boolean getCheckShapeOverlapping(float tCord, float lCord, float wid, String shType, ArrayList<ShapeProperties> shpeList) {
        if (shpeList.isEmpty()) {
            return false;
        } else {
            return checkShapeOverlapping(tCord, lCord, wid, shType, shpeList);
        }
    }

    // Accessible method to get the function to return index of touched shape
    public int returnIndexOfTouchedShape(float xCord, float yCord, ArrayList<ShapeProperties> shpList) {
        return checkIfShapeIsTouched(xCord, yCord, shpList);
    }

    // Accessible method to get the function to check if shape is removed from list
    public boolean returnCheckIfRemoved(ShapeProperties shapeRemoved, ArrayList<ShapeProperties> updatedList){
        return checkIfRemoved(shapeRemoved, updatedList);
    }

    // Returns true if two shapes are overlapping
    private boolean checkShapeOverlapping(float t, float l, float len, String sType, ArrayList<ShapeProperties> sList) {
        boolean matchFound = false;
        for (ShapeProperties shape : sList) {
            if (sType.equals("Square") && shape.type.equals("Square")){
                float x1 = (t+(len/2));
                float y1 = (l+(len/2));
                float x2 = (shape.top + (shape.width/2));
                float y2 = (shape.left + (shape.width/2));
                float xdist = Math.abs(x1 - x2);
                float ydist = Math.abs(y1 - y2);
                if ((xdist <= ((len/2)+(shape.width/2))) && (ydist <= ((len/2)+(shape.width/2)))){
                    matchFound = true;
                    break;
                }
            } else if (sType.equals("Circle") && shape.type.equals("Circle")){
                float xdistCircle = Math.abs(t - shape.top);
                float ydistCircle = Math.abs(l - shape.left);
                if ((xdistCircle <= (len/2 + shape.width/2)) && (ydistCircle <= (len/2 + shape.width/2))){
                    matchFound = true;
                    break;
                }
            } else if (sType.equals("Square") && shape.type.equals("Circle")){
                float xD = (t+(len/2));
                float yD = (l+(len/2));
                float xDist1 = Math.abs(xD - shape.top);
                float yDist1 = Math.abs(yD - shape.left);
                if ((xDist1 <= ((len/2)+(shape.width/2))) && (yDist1 <= ((len/2)+(shape.width/2)))){
                    matchFound = true;
                    break;
                }
            } else if (sType.equals("Circle") && shape.type.equals("Square")){
                float xD1 = (shape.top+(shape.width/2));
                float yD1 = (shape.left+(shape.width/2));
                float xDist2 = Math.abs(xD1 - t);
                float yDist2 = Math.abs(yD1 - l);
                if ((xDist2 <= ((len/2)+(shape.width/2))) && (yDist2 <= ((len/2)+(shape.width/2)))){
                    matchFound = true;
                    break;
                }
            }
        }
        return matchFound;
    }

    // Returns an index within th size of shapes List if a shape is touched otherwise a greater value that List size
    private int checkIfShapeIsTouched(float x, float y, ArrayList<ShapeProperties> shList) {
        int indexOfShape = shList.size() + 1;
        for (ShapeProperties s : shList) {
            if ((x > s.top && x < s.top + s.width) &&
                    (y > s.left && y < s.left + s.width)) {
                indexOfShape = shList.indexOf(s);
                break;
            }
        }
        return indexOfShape;
    }

    // Method returns true if a shape has been successfully removed from list
    private boolean checkIfRemoved(ShapeProperties sRemoved, ArrayList<ShapeProperties> uList){
        if (uList.contains(sRemoved)){
            return false;
        } else {
            return true;
        }
    }
}
