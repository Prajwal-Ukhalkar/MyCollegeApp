package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance ();
    DatabaseReference myRef = database.getReference ();

    private Dialog dialog;

    Toolbar toolbar;
    private RecyclerView recyclerView;
    public static List<CategoryModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_categories );
        getSupportActionBar ().hide ();

        dialog = new Dialog ( this );
        dialog.setContentView ( R.layout.loading );
        dialog.getWindow ().setLayout ( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow ().setBackgroundDrawable ( getDrawable ( R.drawable.rounded_corners ) );
        dialog.setCancelable ( false );

        toolbar = findViewById (R.id.toolbar);
        recyclerView = findViewById (R.id.rv);

        list = new ArrayList<> ();


        recyclerView.setLayoutManager ( new GridLayoutManager ( this,1 ) );
        CategoryAdaptor adaptor = new CategoryAdaptor ( list );
        recyclerView.setAdapter ( adaptor );
        dialog.show ();
        myRef.child ( "Categories" ).addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren ()){
                    List<String > sets = new ArrayList<> ();

                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.child ( "sets" ).getChildren ()){
                        sets.add ( dataSnapshot2.getKey () );
                    }
                    list.add ( new CategoryModel (dataSnapshot1.child ( "name" ).getValue ().toString (),
                            dataSnapshot1.child ( "url" ).getValue ().toString (),
                            sets,
                            dataSnapshot1.getKey (  )
                    ));
                }
                adaptor.notifyDataSetChanged ();  //refresh the adapter...
                dialog.dismiss ();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( CategoriesActivity.this, error.getMessage (), Toast.LENGTH_SHORT ).show ();
                dialog.dismiss ();
                finish ();

            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == android.R.id.home){
            finish ();
        }
        return super.onOptionsItemSelected ( item );
    }
}