/**
 * Written by Kushagra Gupta for CS6326 04/03/20
 *
 * Model class to store player scores
 */
package com.example.touchshapes;

public class PlayerScores {
    public String name;
    public String score;
    public String date;

    public PlayerScores(String name, String score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }
}
