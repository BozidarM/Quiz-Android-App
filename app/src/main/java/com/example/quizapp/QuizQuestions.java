package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class QuizQuestions extends AppCompatActivity implements View.OnClickListener {

    private String username, number, category, difficulty;
    private RequestQueue mQueue;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private TextView twPoints, twQuestion, twCurrent;
    private RadioButton rb1, rb2, rb3, rb4;
    private RadioGroup rbGroup;
    private Button btnNext;
    private  Question firstQuestion;
    private int changer;
    private int changer2 = 1;
    private ArrayList<String> allOptions = new ArrayList<>();
    private boolean answered;
    private int points = 0;
    private RadioButton selectedRb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        initComponents();
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void initComponents() {
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        number = extras.getString("number");
        category = extras.getString("category");
        difficulty = extras.getString("difficulty");

        twQuestion = findViewById(R.id.twQuestion);
        twPoints = findViewById(R.id.twPoints);
        twCurrent = findViewById(R.id.twCurrent);

        rbGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rbAnswer1);
        rb2 = findViewById(R.id.rbAnswer2);
        rb3 = findViewById(R.id.rbAnswer3);
        rb4 = findViewById(R.id.rbAnswer4);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

    }

    private void jsonParse() {
        String url = "https://opentdb.com/api.php?amount=" + number + "&category=" + category +"&difficulty=" + difficulty + "&type=multiple";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            parseJsonArray(jsonArray);

                            if (questionArrayList.size() == 0) {
                                Toast.makeText(QuizQuestions.this, "Please insert smaller number of questions or select other category.", Toast.LENGTH_LONG).show();
                            }else {
                                changer = 0;
                                startQuestions();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void startQuestions() {

        firstQuestion = questionArrayList.get(changer);
        String firstOption = firstQuestion.getOption1();
        String secondOption = firstQuestion.getOption2();
        String thirdOption = firstQuestion.getOption3();
        String fourthOption = firstQuestion.getAnswer();
        allOptions.add(firstOption);
        allOptions.add(secondOption);
        allOptions.add(thirdOption);
        allOptions.add(fourthOption);
        Collections.shuffle(allOptions);

        twQuestion.setText(android.text.Html.fromHtml(firstQuestion.getQuestion()).toString().trim().replaceAll("&(.*)+;", ""));

        rb1.setText(android.text.Html.fromHtml(allOptions.get(0)).toString().trim().replaceAll("&(.*)+;", ""));
        rb2.setText(android.text.Html.fromHtml(allOptions.get(1)).toString().trim().replaceAll("&(.*)+;", ""));
        rb3.setText(android.text.Html.fromHtml(allOptions.get(2)).toString().trim().replaceAll("&(.*)+;", ""));
        rb4.setText(android.text.Html.fromHtml(allOptions.get(3)).toString().trim().replaceAll("&(.*)+;", ""));

        twCurrent.setText("" + changer2 + " / " + number);

        answered = false;
    }

    private void changeQuestion() {
        rb1.setBackgroundResource(R.drawable.btn_border);
        rb2.setBackgroundResource(R.drawable.btn_border);
        rb3.setBackgroundResource(R.drawable.btn_border);
        rb4.setBackgroundResource(R.drawable.btn_border);
        rbGroup.clearCheck();

        allOptions.clear();

        changer++;
        int limit = Integer.parseInt(number);
        if (changer < limit) {
            changer2++;
            startQuestions();
        }else{
            Intent intent = new Intent(this, FinishPage.class);
            Bundle extras = new Bundle();

            String sPoints = String.valueOf(points);
            extras.putString("username", username);
            extras.putString("number", number);
            extras.putString("points", sPoints);
            extras.putString("difficulty", difficulty);

            Database db = new Database(this);
            db.addResult(username, number, difficulty, sPoints);

            intent.putExtras(extras);
            startActivity(intent);
        }

    }

    private void checkAnswer(){
        answered = true;
        btnNext.setText(R.string.next);

        int selectedId = rbGroup.getCheckedRadioButtonId();
        selectedRb = findViewById(selectedId);
        String radioAnswer = selectedRb.getText().toString();

        String correctAnswer = android.text.Html.fromHtml(firstQuestion.getAnswer()).toString().trim().replaceAll("&(.*)+;", "");

        if (radioAnswer.equals(correctAnswer)){
            selectedRb.setBackgroundResource(R.drawable.correct_background);
            switch(difficulty){
                case "easy":
                    points++;
                    break;
                case "medium":
                    points += 2;
                    break;
                case "hard":
                    points += 3;
                    break;
            }
            twPoints.setText(getString(R.string.points) + points);
        }else{
            selectedRb.setBackgroundResource(R.drawable.incorrect_background);

            String option1 = rb1.getText().toString();
            if (option1.equals(correctAnswer)){
                rb1.setBackgroundResource(R.drawable.correct_background);
            }

            String option2 = rb2.getText().toString();
            if (option2.equals(correctAnswer)){
                rb2.setBackgroundResource(R.drawable.correct_background);
            }

            String option3 = rb3.getText().toString();
            if (option3.equals(correctAnswer)){
                rb3.setBackgroundResource(R.drawable.correct_background);
            }

            String option4 = rb4.getText().toString();
            if (option4.equals(correctAnswer)){
                rb4.setBackgroundResource(R.drawable.correct_background);
            }

       }
    }

    private void parseJsonArray(JSONArray array){
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject result = array.getJSONObject(i);
                Question questionModel = new Question();

                questionModel.setQuestion(result.getString("question"));
                questionModel.setAnswer(result.getString("correct_answer"));

                JSONArray jArray = result.getJSONArray("incorrect_answers");
                if (jArray != null) {
                    for (int j = 0; j < jArray.length(); j++) {
                        questionModel.setOption1(jArray.getString(0));
                        questionModel.setOption2(jArray.getString(1));
                        questionModel.setOption3(jArray.getString(2));
                    }
                }

                questionArrayList.add(questionModel);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        System.out.println("ANSWERED: " + answered);
        if (!answered) {
            if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                checkAnswer();
            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            }
        } else {
            btnNext.setText("Continue");
            changeQuestion();
        }
    }
}
