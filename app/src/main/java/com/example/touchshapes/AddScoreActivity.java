/**
 * Written by Kushagra Gupta for CS6326 04/03/20
 * This Activity implements the functionality to add score to list
 * or discard it.
 */
package com.example.touchshapes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class AddScoreActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        // Get the score from previous activity
        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String receivedScore = bundle.getString("score");

        // Set the score to the view
        TextView pScore = (TextView) findViewById(R.id.player_score);
        pScore.setText("Score: "+receivedScore);

        // Get current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        final String formattedDate = df.format(c);

        // Set current date to view
        TextView currDate = (TextView) findViewById(R.id.current_date);
        currDate.setText("Date: "+formattedDate);

        // Edit Text for player name input
        final EditText playerName = (EditText) findViewById(R.id.player_name);
        playerName.addTextChangedListener(enterName);

        // Add button when clicked takes up to high scores list
        Button buttonAdd = (Button) findViewById(R.id.add_button);
        buttonAdd.setEnabled(false);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                Intent exitIntent = new Intent(AddScoreActivity.this, HighScoresActivity.class);
                bundle1.putString("playerScore", receivedScore);
                bundle1.putString("date", formattedDate);
                bundle1.putString("playerName", playerName.getText().toString());
                exitIntent.putExtras(bundle1);
                startActivity(exitIntent);
                finish();
            }
        });

        // Cancel button when clicked takes us to Main Screen
        Button buttonCancel = (Button) findViewById(R.id.cancel_button);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddScoreActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    // Text watcher for enter player name Edit Text
    TextWatcher enterName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            EditText playerName = (EditText) findViewById(R.id.player_name);
            if (!playerName.getText().toString().isEmpty()){
                Button buttonAdd = (Button) findViewById(R.id.add_button);
                buttonAdd.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().isEmpty()){
                Button buttonAdd = (Button) findViewById(R.id.add_button);
                buttonAdd.setEnabled(false);
                EditText playerName = (EditText) findViewById(R.id.player_name);
                playerName.setText("");
            }

        }
    };
}
