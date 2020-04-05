/**
 * Written by Kushagra Gupta for Assignment 5 CS6326 04/03/20
 *
 * This module implements a Custom View. There are several helper methods to
 * assist in drawing and updating the canvas.
 * I have maintained a list of shapes and used it to draw the canvas.
 * I am shapes coordinates of shapes randomly using the height and width
 * of Custom View as bounds for random function.
 * A reference to Timer Text View is added to update the timer when correct shapes
 * are touched.
 * Shape and Color are also generated randomly.
 * The games ends when 5 correct shapes are touched.
 * Sometimes the squares are not getting touched which may be due to a glitch
 * in overlap checking algorithm which shows a circle and square overlapping.
 *
 */
package com.example.touchshapes.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.touchshapes.AddScoreActivity;
import com.example.touchshapes.GameActivity;
import com.example.touchshapes.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CustomView extends View {
    // Declare variables and instances of different classes
    private float[] displayDimen;
    private View v;
    private float borderWidth;
    private Paint paint;
    private RectF rect;
    private Random rand;
    private ArrayList<ShapeProperties> shapesList;
    private CheckOverlap checkOverlap;
    private String currentShape;
    private String currentColor;
    private int correctClickCounter;
    private TextView shapesCount;

    // Custom View Methods (Not Touched)
    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    // Method is called to initialize the declared variable and instances
    public void init(@Nullable AttributeSet set) {
        v = findViewById(R.id.custom_view);
        displayDimen = new float[2];
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new RectF();
        rand = new Random();
        shapesList = new ArrayList<>();
        checkOverlap = new CheckOverlap();
        getCurrentShapeAndColor();
        correctClickCounter = 0;
    }

    // Add a reference to Touched Shapes Count Text View
    public void refToTextView(TextView tv1) {
        shapesCount = tv1;
    }

    // OnDraw method to draw shapes on canvas
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        getCanvasDimension();
        getCanvasBorderWidth();
        initializeShapesList();
        if (!shapesList.isEmpty()) {
            for (ShapeProperties s : shapesList) {
                setShapeColor(s.color);
                switch (s.type) {
                    case "Circle":
                        canvas.drawCircle(s.top, s.left, s.width / 2, paint);
                        break;
                    case "Square":
                        rect.top = s.top;
                        rect.left = s.left;
                        rect.bottom = s.top + s.width;
                        rect.right = s.left + s.width;
                        canvas.drawRect(rect, paint);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    // This method is called continuously as until we return true
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float Xtouch = event.getX();
        float Ytouch = event.getY();
        int indx = checkIfShapeTouched(Xtouch, Ytouch);
        if (indx <= shapesList.size()) {
            if (shapeRemoved(indx)) {
                /*Toast.makeText(getContext(), "Shape removed", Toast.LENGTH_SHORT).show();*/
                if (addNewShapeToList()) {
                    /*Toast.makeText(getContext(), "New Shape Added", Toast.LENGTH_SHORT).show();*/
                    v.invalidate();
                }
            }
        }
        return true;
    }

    // Helper Method to add shapes to list
    private void initializeShapesList() {
        if (shapesList.isEmpty()) {
            int totalNoOfShapes = getNumberOfShapes();
            for (int i = 0; i < totalNoOfShapes; ) {
                float topCord = returnAnchorX();
                float leftCord = returnAnchorY();
                float radSide = returnSideOrRadius();
                String shapeTyp = returnShape();
                String colorOfShape = returnColor();
                if (!checkOverlap.getCheckShapeOverlapping(topCord, leftCord, radSide, shapeTyp, shapesList)) {
                    shapesList.add(new ShapeProperties(topCord, leftCord, radSide, shapeTyp, colorOfShape));
                    i++;
                }
            }
        }
    }

    // Helper method to add a new shape to list when a shape is popped
    private boolean addNewShapeToList() {
        boolean sAdded = false;
        while (!sAdded) {
            float topCord = returnAnchorX();
            float leftCord = returnAnchorY();
            float radSide = returnSideOrRadius();
            String shapeTyp = returnShape();
            String colorOfShape = returnColor();
            if (!checkOverlap.getCheckShapeOverlapping(topCord, leftCord, radSide, shapeTyp, shapesList)) {
                shapesList.add(new ShapeProperties(topCord, leftCord, radSide, shapeTyp, colorOfShape));
                sAdded = true;
            }
        }
        return sAdded;
    }

    // Helper Method to remove Shape from List
    private boolean shapeRemoved(int indexOfT) {
        ShapeProperties sRemd = shapesList.remove(indexOfT);
        checkIfCorrectShapeIsRemoved(sRemd); // Check if the shape removed is the shape to be touched
        return checkOverlap.returnCheckIfRemoved(sRemd, shapesList);
    }

    // Helper method to check if correct shape is removed
    private void checkIfCorrectShapeIsRemoved(ShapeProperties removedShape) {
        if (/*removedShape.type.equals(currentShape) && */removedShape.color.equals(currentColor)) {
            correctClickCounter += 1;
            shapesCount.setText(String.valueOf(correctClickCounter));
            if (correctClickCounter == 5){
                ((GameActivity) getContext()).returnStartNewActivity();
            }
        }
    }

    // Helper method to get current shape and color from activity bundle
    private void getCurrentShapeAndColor() {
        Bundle transporter = ((GameActivity) getContext()).getIntent().getExtras();
        assert transporter != null;
        currentShape = transporter.getString("shape");
        currentColor = transporter.getString("color");
    }

    // Helper method to check if coordinates touched on screen lie within a shape
    private int checkIfShapeTouched(float xTouchCord, float yTouchCord) {
        return checkOverlap.returnIndexOfTouchedShape(xTouchCord, yTouchCord, shapesList);
    }

    // Method to get the dimensions of the canvas
    private void getCanvasDimension() {
        displayDimen[0] = v.getWidth();
        displayDimen[1] = v.getHeight();
    }

    // Method to calculate the padding of canvas to avoid overlapping with border
    private void getCanvasBorderWidth() {
        borderWidth = 62 * getContext().getResources().getDisplayMetrics().density;
    }

    // Helper method to return the total number of shapes
    private int getNumberOfShapes() {

        return rand.nextInt(7) + 5;
    }

    // Helper method to set paint color of shape
    private void setShapeColor(String clr) {
        switch (clr) {
            case "Red":
                paint.setColor(Color.RED);
                break;
            case "Orange":
                paint.setColor(getResources().getColor(R.color.ORANGE));
                break;
            case "Yellow":
                paint.setColor(Color.YELLOW);
                break;
            case "Blue":
                paint.setColor(Color.BLUE);
                break;
            case "Green":
                paint.setColor(Color.GREEN);
                break;
            case "Purple":
                paint.setColor(getResources().getColor(R.color.PURPLE));
                break;
            case "White":
                paint.setColor(Color.WHITE);
                break;
            default:
                paint.setColor(Color.GRAY);
                break;
        }
    }

    // Helper methods to return shape properties
    private float returnAnchorX() {
        return rand.nextInt((int) ((displayDimen[0] - borderWidth) - (borderWidth + 50))) + borderWidth;
    }

    private float returnAnchorY() {
        return rand.nextInt((int) ((displayDimen[1] - borderWidth) - (borderWidth + 50))) + borderWidth;
    }

    private float returnSideOrRadius() {
        float temp = rand.nextInt(30) + 30;
        return temp * getContext().getResources().getDisplayMetrics().density;
    }

    private String returnShape() {
        String[] shapesList = {"Square", "Circle"};
        return shapesList[rand.nextInt(shapesList.length)];
    }

    private String returnColor() {
        String[] colorsList = {"Red", "Orange", "Yellow", "Green", "Blue", "Purple", "White"};
        return colorsList[rand.nextInt(colorsList.length)];
    }
}
