/**
 * Written by Kushagra Gupta for CS6326 04/03/20
 * This is the main Activity where the Custom View is also present.
 * A timer has been implemented here to catch the player score once all correct
 * shapes are touched.
 * An option to restart the game is also implemented.
 */
package com.example.touchshapes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.touchshapes.View.CustomView;
import com.example.touchshapes.View.ShapeProperties;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private long startTime;
    private Handler timeHandler = new Handler();
    private TextView dispScore;
    private TextView shapeCount;
    private String currShape;
    private String currColor;

    public String returnShape() {
        return currShape;
    }

    public String returnColor() {
        return currColor;
    }

    public void returnStartNewActivity(){
        startNewActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        CustomView customView = (CustomView) findViewById(R.id.custom_view);
        shapeCount = (TextView) findViewById(R.id.shape_count);
        dispScore = (TextView) findViewById(R.id.timer);

        // Set the height of canvas to 2/3 of screen height dynamically
        // Will automatically adjust to all screen sizes
        setHeightOfCanvas();

        // Get the instruction and display on activity screen
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String receivedInstr = bundle.getString("currentInstr");
        displayInstructionPassed(receivedInstr);
        currShape = bundle.getString("shape");
        currColor = bundle.getString("color");

        // Display the number of correct shapes touched and score
        createScoreView();

        // Start the Timer
        startTime = System.currentTimeMillis();
        timeHandler.postDelayed(timerRunnable, 0);
        wireWidgets();

        // Pass a reference to Text Views
        customView.refToTextView(shapeCount);
    }

    /*
        Linking our TextView instance variable to a TextView in the project’s design layout,
        so that we may operate on the TextView directly.
         */
    private void wireWidgets() {
        dispScore = (TextView) findViewById(R.id.timer);
    }

    /*
    Runnable for Timer
     */
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            dispScore.setText(String.format("%d:%02d", minutes, seconds));
            timeHandler.postDelayed(this, 500);

        }
    };

    // Helper method to start a new Activity
    private void startNewActivity(){
        Bundle bundle1 = new Bundle();
        Intent exitIntent = new Intent(GameActivity.this, AddScoreActivity.class);
        bundle1.putString("score", dispScore.getText().toString());
        exitIntent.putExtras(bundle1);
        startActivity(exitIntent);
        finish();
    }

    // Helper method to set the height of canvas
    private void setHeightOfCanvas() {
        View v = findViewById(R.id.custom_view);
        Context context = getApplicationContext();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int h = metrics.heightPixels / 3;
        v.getLayoutParams().height = 2 * h;
    }

    // Helper method to display the current instruction
    private void displayInstructionPassed(String instrPassed) {
        TextView currentInstr = (TextView) findViewById(R.id.current_instr);
        currentInstr.setText(instrPassed);
        currentInstr.setGravity(Gravity.CENTER);
        currentInstr.setPadding(10, 10, 10, 10);
    }

    // Helper method to display the number of correct shapes touched and score
    private void createScoreView() {
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        dispScore = (TextView) findViewById(R.id.timer);
        dispScore.setWidth(metrics.widthPixels / 2);
        dispScore.setPadding(10, 10, 10, 10);
        dispScore.setGravity(Gravity.CENTER);

        shapeCount = (TextView) findViewById(R.id.shape_count);
        shapeCount.setWidth(metrics.widthPixels / 2);
        shapeCount.setPadding(10, 10, 10, 10);
        shapeCount.setGravity(Gravity.CENTER);
        shapeCount.setText("0");
    }

    // Method to create a menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Create the menu item in Action bar
        MenuInflater inflater = getMenuInflater(); // Get an instance of the menu inflater
        inflater.inflate(R.menu.new_game, menu); // Populate the Add new score menu item on action bar
        return super.onCreateOptionsMenu(menu);
    }
    // If a menu item is selected from Action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // If the add new menu item is clicked on the Action bar, start the SecondActivity to enter new score.
        startActivity(new Intent(GameActivity.this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
