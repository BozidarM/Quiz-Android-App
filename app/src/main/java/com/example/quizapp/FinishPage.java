package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishPage extends AppCompatActivity implements View.OnClickListener {

    private TextView twFinish;
    private Button btnScoreboard, btnHome;
    private String username, number, points, difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_page);

        initComponents();
    }

    private void initComponents() {

        twFinish = findViewById(R.id.twFinish);

        btnScoreboard = findViewById(R.id.btnScoreboard);
        btnScoreboard.setOnClickListener(this);
        btnHome = findViewById(R.id.btnHome2);
        btnHome.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        number = extras.getString("number");
        points = extras.getString("points");
        difficulty = extras.getString("difficulty");

        twFinish.setText(username + ", you finish with the score of " + points + " point/s.\nNumber of questions: " + number + "\nDifficulty mode: " + difficulty);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnScoreboard:
                Intent intent = new Intent(this, ScoreBoard.class);
                startActivity(intent);
                break;
            case R.id.btnHome2:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}