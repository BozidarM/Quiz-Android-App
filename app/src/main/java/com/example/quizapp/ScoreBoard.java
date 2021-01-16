package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ScoreBoard extends AppCompatActivity implements View.OnClickListener{

    Button btnHome, btnRemove;
    ConstraintLayout result;
    List<ResultsModel> resultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        initComponents();
    }

    private void initComponents() {
        btnHome = findViewById(R.id.btnHome2);
        btnHome.setOnClickListener(this);
        btnRemove = findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(this);

        showResults();
    }

    private void showResults() {
        Database db = new Database(this);
        resultsList = db.getAllSavedResults();

        LinearLayout mainScrollView = findViewById(R.id.mainScrollView);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (ResultsModel r : resultsList){
            result = (ConstraintLayout) inflater.inflate(R.layout.one_result, null);

            TextView twUsername = result.findViewById(R.id.twUsername);
            twUsername.setText(r.getUsername());
            TextView twNumber = result.findViewById(R.id.twNumber);
            twNumber.setText(r.getNumber());
            TextView twDifficulty = result.findViewById(R.id.twDifficulty);
            if (r.getDifficulty().equals("easy") || r.getDifficulty().equals("hard")){
                twDifficulty.setText("   " + r.getDifficulty() + "   ");
            }else {
                twDifficulty.setText(r.getDifficulty());
            }
            TextView twPoints = result.findViewById(R.id.twPoints2);
            twPoints.setText(r.getPoints());

            mainScrollView.addView(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHome2:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRemove:
                Database db = new Database(this);
                for (ResultsModel r : resultsList) {
                    db.deleteResults(r.getResultId());
                }
                Toast.makeText(this, "Results are deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}