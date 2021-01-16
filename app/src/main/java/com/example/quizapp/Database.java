package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper{
    private static final String DATABASE_FILE_NAME = "saved_news";

    public Database(Context context){super(context, DATABASE_FILE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = String.format(
                "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                ResultsModel.TABLE_NAME, ResultsModel.FIELD_RESULT_ID, ResultsModel.FIELD_USERNAME, ResultsModel.FIELD_NUMBER, ResultsModel.FIELD_DIFFICULTY, ResultsModel.FIELD_POINTS
        );

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", ResultsModel.TABLE_NAME));
        onCreate(db);
    }

    public void addResult (String username, String number, String difficulty, String points){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ResultsModel.FIELD_USERNAME, username);
        cv.put(ResultsModel.FIELD_NUMBER, number);
        cv.put(ResultsModel.FIELD_DIFFICULTY, difficulty);
        cv.put(ResultsModel.FIELD_POINTS, points);

        db.insert(ResultsModel.TABLE_NAME, null, cv);
    }

    public int deleteResults(int resultId){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(ResultsModel.TABLE_NAME,ResultsModel.FIELD_RESULT_ID + " = ?", new String[] {String.valueOf(resultId)});
    }

    public List<ResultsModel> getAllSavedResults(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT * FROM %s", ResultsModel.TABLE_NAME);
        Cursor result = db.rawQuery(query, null);
        result.moveToFirst();

        List<ResultsModel> list = new ArrayList<>(result.getCount());

        while (!result.isAfterLast()){
            int resultId = result.getInt(result.getColumnIndex(ResultsModel.FIELD_RESULT_ID));
            String username = result.getString(result.getColumnIndex(ResultsModel.FIELD_USERNAME));
            String number = result.getString(result.getColumnIndex(ResultsModel.FIELD_NUMBER));
            String difficulty = result.getString(result.getColumnIndex(ResultsModel.FIELD_DIFFICULTY));
            String points = result.getString(result.getColumnIndex(ResultsModel.FIELD_POINTS));

            ResultsModel post = new ResultsModel(resultId, username, number, difficulty, points);
            list.add(post);
            result.moveToNext();
        }
        return list;

    }

}
