package com.example.quizapp;

public class ResultsModel {

    public static final String TABLE_NAME = "results";

    public static final String FIELD_RESULT_ID = "result_id";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_NUMBER = "number";
    public static final String FIELD_DIFFICULTY = "difficulty";
    public static final String FIELD_POINTS = "points";

    private int resultId;
    private String username, number, difficulty, points;

    public ResultsModel(int resultId, String username, String number, String difficulty, String points) {
        this.resultId = resultId;
        this.username = username;
        this.number = number;
        this.difficulty = difficulty;
        this.points = points;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
