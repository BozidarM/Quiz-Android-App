package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner category, difficulty;
    private EditText etNumber, etUsername;
    private Button btnStart, btnScoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSpinners();
        initComponents();
    }

    private void setSpinners() {
        category = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        difficulty = findViewById(R.id.spinnerDifficulty);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(adapter2);
    }

    private void initComponents() {
        etNumber = findViewById(R.id.etNumber);
        etNumber.setText(R.string.default_number);
        etUsername = findViewById(R.id.etUsername);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        btnScoreboard = findViewById(R.id.btnScoreboard1);
        btnScoreboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                Intent intent = new Intent(this, QuizQuestions.class);
                Bundle extras = new Bundle();

                String editCategory = category.getSelectedItem().toString().substring(0, 2);
                String categoryS = "";
                if (editCategory.equals("9.")) {
                    categoryS = "9";
                } else if (editCategory.equals("Al")) {
                    categoryS = "";
                } else {
                    categoryS = editCategory;
                }

                String sNumber = etNumber.getText().toString();
                int iNumber = Integer.parseInt(sNumber);

                if (TextUtils.isEmpty(etUsername.getText())) {
                    Toast.makeText(this, "Please insert username first.", Toast.LENGTH_SHORT).show();
                } else if (iNumber > 50) {
                    Toast.makeText(this, "Number must be less than or qual to 50.", Toast.LENGTH_SHORT).show();
                } else {
                    extras.putString("username", etUsername.getText().toString());
                    extras.putString("number", etNumber.getText().toString());
                    extras.putString("category", categoryS);
                    extras.putString("difficulty", difficulty.getSelectedItem().toString().toLowerCase());

                    intent.putExtras(extras);
                    startActivity(intent);
                }
                break;

            case R.id.btnScoreboard1:
                intent = new Intent(this, ScoreBoard.class);
                startActivity(intent);
                break;
        }
    }
}