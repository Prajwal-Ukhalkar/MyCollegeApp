package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.GridView;

import java.util.List;

public class SetsActivity extends AppCompatActivity {
    private GridView gridView;
    private List<String > sets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_sets );
        getSupportActionBar ().setTitle ( getIntent ().getStringExtra ( "title" ) );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );

        sets = CategoriesActivity.list.get (getIntent ().getIntExtra ( "position", 0 )).getSets ();

        gridView = findViewById ( R.id.gridView );
        GridAdapter adapter = new GridAdapter ( sets,getIntent ().getStringExtra ( "title" ) );
        gridView.setAdapter ( adapter );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == android.R.id.home){
            finish ();
        }
        return super.onOptionsItemSelected ( item );
    }
}