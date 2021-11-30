package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.collegeapp.QuestionsActivity.FILE_NAME;
import static com.example.collegeapp.QuestionsActivity.KEY_NAME;

public class BookmarkActivity extends AppCompatActivity {


    private List<QuestionModel> bookmarksList;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private RecyclerView rv_bookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_bookmark );

        rv_bookmarks = findViewById ( R.id.rv_bookmarks );


        preferences = getSharedPreferences ( FILE_NAME, Context.MODE_PRIVATE );
        editor = preferences.edit ();
        gson = new Gson();
        getBookmarks ();
        getSupportActionBar ().setTitle ( "Bookmarks" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager ( this );
        rv_bookmarks.setLayoutManager ( layoutManager );


        BookmarksAdapter adapter = new BookmarksAdapter ( bookmarksList );
        rv_bookmarks.setAdapter ( adapter );
    }

    protected void onPause() {
        super.onPause ();
        storeBookmarks ();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == android.R.id.home){
            finish ();
        }
        return super.onOptionsItemSelected ( item );
    }

    private void getBookmarks(){

        String json = preferences.getString ( KEY_NAME,"" );
        Type type = new TypeToken<List<QuestionModel>> (){}.getType ();
        bookmarksList = gson.fromJson ( json,type );

        if (bookmarksList == null){
            bookmarksList = new ArrayList<> ();
        }
    }

    private  void storeBookmarks(){

        String json = gson.toJson ( bookmarksList );
        editor.putString ( KEY_NAME,json );
        editor.commit ();
    }
}