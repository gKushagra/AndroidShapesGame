/**
 * Written by Kushagra Gupta for CS6326 04/03/20
 *
 * Implemented a Custom Sorter to sort the scores(time) if two dates are equal,
 * and if two dates are snot same then sort according to dates.
 *
 * Sorting according to: Earliest data and Smallest Time.
 */
package com.example.touchshapes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ScoreSorter implements Comparator<PlayerScores> {
    @Override
    public int compare(PlayerScores o1, PlayerScores o2) {
        int result = 0; // Temp var to store the result
        // Get a Date instance of DateFormat, SHORT refers to: MM/dd/yyyy
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        // Get a Time instance of SimpleDateFormat
        SimpleDateFormat tm = new SimpleDateFormat("mm:ss", Locale.US);
        try {
            Date d1 = df.parse(o1.date);    // Convert string date input1 to Date format
            Date d2 = df.parse(o2.date);    // Convert string date input2 to Date format
            Date t1 = tm.parse(o1.score);   // Convert string time input1 to Time format
            Date t2 = tm.parse(o2.score);   // Convert string time input2 to Time format
            assert d1 != null;
            if (d1.equals(d2)){ // If the two dates are equal, compare the times
                assert t2 != null;
                result = t1.compareTo(t2); // Compare if time1 one comes after time2
            } else {
                assert d2 != null;
                result = d2.compareTo(d1); // If the dates are not equal, we compare them
            }

        } catch (ParseException e) { // Catch the exception in case there is error parsing the string inputs
            e.printStackTrace();
        }
        return result;
    }
}