/**
 * Written by Kushagra Gupta CS6326 04/03/20
 * This module has methods to handle a list containing scores of players.
 * It has the read and write file methods to read and write to a file.
 * Method to add a new score and return the list are also implemented.
 *
 */
package com.example.touchshapes;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class HandlePlayerScores {
    private ArrayList<PlayerScores> playerScoresList = new ArrayList<>();
    // Accessible method to read from file
    public boolean initializeList(Context c){
        return readFile(c);
    }
    // Accessible method to write a file
    public boolean writeToFile(Context c1){
        return writeFile(c1);
    }
    // Accessible method to return the list
    public ArrayList<PlayerScores> returnList(){
        return playerScoresList;
    }
    // Accessible method to add a new player score to list
    public boolean getAddToListFunction(PlayerScores p){
        return addToList(p);
    }

    // Method to add a new score to list
    private boolean addToList(PlayerScores playerScore){
        playerScoresList.add(playerScore);
        Collections.sort(playerScoresList, new ScoreSorter());
        return playerScoresList.contains(playerScore);
    }
    // Method to read a file
    private boolean readFile(Context context){

        String data = ""; // String var to store the data read from file.
        try{
            InputStream io = context.openFileInput("HighScores.txt"); // Open the file
            if (io!=null){  // If the file was successfully opened
                InputStreamReader isr = new InputStreamReader(io);  // Create an instance of InputStreamReader class
                BufferedReader br = new BufferedReader(isr);    // Create an instance of BufferedReader class
                String readString = "";// String var to store the data read from file.
                playerScoresList.clear();   // This is to clear the list to avoid duplicating the elements in the list
                while ((readString = br.readLine()) != null){   // Read a line from the file
                    String[] splitdata = readString.split("\t");    // Split the string based on tab space delimiter
                    playerScoresList.add(new PlayerScores(   // Add the player to the list
                            splitdata[0],
                            splitdata[1],
                            splitdata[2]
                    ));
                }
                io.close(); // Close the Input Output stream
                // Sort the list of players
                Collections.sort(playerScoresList, new ScoreSorter()); // Sort the list based on Custom comparator defined.
            }
            return true;
        }
        catch(FileNotFoundException e){ // In case the file does not exist
            System.out.println(e);
            return false;
        } catch(IOException e){ // Any other exception, for example reading a line from file.
            System.out.println(e);
            return false;
        }
    }
    // Method to write a file
    private boolean writeFile(Context context){
        final String TAG = "SecondActivity";
        try{
            // Create an instance of OutputStreamWriter, Mode_private means that the file can be accessed only by the calling application.
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("HighScores.txt", Context.MODE_PRIVATE));
            int noOfPlayers = Math.min(playerScoresList.size(), 12); // Determine if there are more than 12 players stored in list.
            for (int i = 0; i < noOfPlayers; i++){  // Loop through top 12 players in list
                PlayerScores player = playerScoresList.get(i);   // Get the player details
                osw.write(player.name + "\t" + player.score + "\t" + player.date+"\n"); // Write the player details to file.
            }
            osw.close(); // Close the output stream
            return true;
        }
        catch (IOException e){ // In case the file cannot be created/written.
            Log.e(TAG, "Error: "+e); // log the error
            return false;
        }
    }

}
