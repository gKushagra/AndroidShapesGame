/**
 * Written by Kushagra Gupta for CS6326 04/03/20
 *
 * This is the Main activity. It shows the objective,
 * and a note regarding instructions.
 * Once OK button is pressed, Game Activity is started and
 * instruction is passed to it using Bundle in Intent.
 */
package com.example.touchshapes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.touchshapes.View.ShapeProperties;

public class MainActivity extends AppCompatActivity {

    private String playerName;
    private EditText nameEntered;
    private Button okBtn;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView instr = (TextView) findViewById(R.id.instr);
        instr.setPadding(50, 50, 50, 50);
        instr.setGravity(Gravity.CENTER);
        Context context = getApplicationContext();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        instr.setHeight(metrics.heightPixels / 3);
        Instruction getInstr = new Instruction();
        String currShape = getInstr.getShape();
        String currColor = getInstr.getColor();
        String currentInstr = "Touch only "+currColor+" "+"Shape's";
        instr.setText(currentInstr);

        TextView note = (TextView) findViewById(R.id.note);
        String noteText = "Note: \nGame will end when you touch 5 correct shapes. \n" +
                "\nIf you don't find the required color, just touch any other shape.\n"+"" +
                "\nAlso, squares may or may not get touched";
        note.setText(noteText);
        note.setPadding(10,10,10,10);
        note.setGravity(Gravity.CENTER);

        Bundle bundle = new Bundle();
        bundle.putString("shape", currShape);
        bundle.putString("color", currColor);
        bundle.putString("currentInstr", currentInstr);
        final Intent i = new Intent(MainActivity.this, GameActivity.class);
        i.putExtras(bundle);
        okBtn = (Button) findViewById(R.id.ok_button);
        okBtn.setGravity(Gravity.CENTER);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }

    // Method to create a menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Create the menu item in Action bar
        MenuInflater inflater = getMenuInflater(); // Get an instance of the menu inflater
        inflater.inflate(R.menu.add, menu); // Populate the Add new score menu item on action bar
        return super.onCreateOptionsMenu(menu);
    }
    // If a menu item is selected from Action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // If the add new menu item is clicked on the Action bar, start the SecondActivity to enter new score.
        startActivity(new Intent(MainActivity.this, HighScoresActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
