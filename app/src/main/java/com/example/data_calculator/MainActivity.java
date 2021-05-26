package com.example.data_calculator;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    CalendarView calendarView;
    GregorianCalendar calendar = new GregorianCalendar();
    GregorianCalendar now = new GregorianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupFAB();

        calendarView = findViewById(R.id.calendar);

        //met
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth)
                -> calendar = new GregorianCalendar( year, month, dayOfMonth ));
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int timeDiff = getDays(calendar, now);
                Snackbar.make(view, "Your friends birthday is in" + timeDiff + " days!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_about:
                showAbout();
                break;

            case R.id.action_settings:
                Intent i = new Intent(MainActivity.this,
                        SettingsActivity.class);

                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    public int getDays(GregorianCalendar g1, GregorianCalendar g2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;
        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }
        gc1.clear(GregorianCalendar.MILLISECOND);
        gc1.clear(GregorianCalendar.SECOND);
        gc1.clear(GregorianCalendar.MINUTE);
        gc1.clear(GregorianCalendar.HOUR_OF_DAY);
        gc2.clear(GregorianCalendar.MILLISECOND);
        gc2.clear(GregorianCalendar.SECOND);
        gc2.clear(GregorianCalendar.MINUTE);
        gc2.clear(GregorianCalendar.HOUR_OF_DAY);
        while (gc1.before(gc2)) {
            gc1.add(GregorianCalendar.DATE, 1);
            elapsed++;
        }
        return elapsed;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // call the super-class's method to save fields, etc.
        super.onSaveInstanceState(outState);
        outState.putInt("year", calendar.get(calendar.YEAR));
        outState.putInt("month", calendar.get(calendar.MONTH));
        outState.putInt("day", calendar.get(calendar.DAY_OF_MONTH));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        int Year = savedInstanceState.getInt("year");
        int Month = savedInstanceState.getInt("month");
        int Day = savedInstanceState.getInt("day");

        calendar = new GregorianCalendar(Year, Month, Day);

    }

    private void showAbout() {
        Utils.showInfoDialog(this, R.string.app_name, R.string.about_message);
    }
}