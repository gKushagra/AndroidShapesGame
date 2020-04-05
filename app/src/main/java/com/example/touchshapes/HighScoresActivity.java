/**
 * Written by Kushagra Gupta for CS6326 04/03/20
 *
 * This activity show the list of top 12 High Scores.
 * First the list is initialized by reading the file.
 * Then if there is data in bundle, when starting activity from
 * add score activity, that is also added to list.
 * Then the list is displayed.
 *
 * If accessing this from Main Activity, bundle is empty, but
 * a check has been implemented for that.
 */
package com.example.touchshapes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighScoresActivity extends AppCompatActivity {

    HandlePlayerScores handleScores = new HandlePlayerScores();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // Initialize List
        if (handleScores.initializeList(this)){
            Toast.makeText(this, "Loaded File", Toast.LENGTH_SHORT).show();
        }

        try{
            // Get the score, player name and date from previous activity
            Bundle bundle = getIntent().getExtras();
            assert bundle != null;
            String newScore = bundle.getString("playerScore");
            String newDate = bundle.getString("date");
            String newName = bundle.getString("playerName");
            // Add the bundle to the list
            if (handleScores.getAddToListFunction(new PlayerScores(newName, newScore, newDate))){
                Toast.makeText(this, "Added To List", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            e.getStackTrace();
        }

        // Display the list
        if (!handleScores.returnList().isEmpty()){
            showList();
        }
    }

    // Method to create a menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Create the menu item in Action bar
        MenuInflater inflater = getMenuInflater(); // Get an instance of the menu inflater
        inflater.inflate(R.menu.home, menu); // Populate the Add new score menu item on action bar
        return super.onCreateOptionsMenu(menu);
    }
    // If a menu item is selected from Action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // If the add new menu item is clicked on the Action bar, start the SecondActivity to enter new score.
        if (handleScores.writeToFile(this)){
            startActivity(new Intent(HighScoresActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void showList(){
        LinearLayout ll = (LinearLayout) findViewById(R.id.high_scores_linear_layout);
        ArrayList<PlayerScores> temp = handleScores.returnList();
        for (PlayerScores ps : temp){
            TextView tv = new TextView(this);
            tv.setText("Name: "+ps.name+"\t Score: "+ps.score+" "+"\n "+ps.date);
            setTextViewProperties(tv);
            ll.addView(tv);
        }
    }

    // Helper method to set properties of Text View
    private void setTextViewProperties(TextView t){
        t.setGravity(Gravity.CENTER);
        t.setTextSize(20);
        t.setPadding(10,10,10,10);
        t.setBackgroundColor(Color.LTGRAY);
        t.setTextColor(Color.BLACK);
    }
}
