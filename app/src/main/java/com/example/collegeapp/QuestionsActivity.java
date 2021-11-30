package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    public static final String FILE_NAME = "EXAM";
    public static final String KEY_NAME = "QUESTIONS";
    FirebaseDatabase database = FirebaseDatabase.getInstance ();
    DatabaseReference myRef = database.getReference ();

    private TextView question, no_indicator;
    private FloatingActionButton bookmark_btn;
    private LinearLayout options_container;
    private Button next_btn, share_btn;
    private int COUNT = 0;
    private ArrayList<QuestionModel> list;
    private int position = 0;
    private int score = 0;
    private String setId;
    private Dialog dialog;
    private List<QuestionModel> bookmarksList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int MATCHED_QUE_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_questions );
        getSupportActionBar ().hide ();

        question = findViewById ( R.id.question );
        no_indicator = findViewById ( R.id.no_indicator );
        bookmark_btn = findViewById ( R.id.bookmark_btn );
        options_container = findViewById ( R.id.options_container );
        next_btn = findViewById ( R.id.next_btn );

        preferences = getSharedPreferences ( FILE_NAME, Context.MODE_PRIVATE );
        editor = preferences.edit ();
        gson = new Gson();
        getBookmarks ();


        bookmark_btn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (modelMatch ()){
                    bookmarksList.remove ( MATCHED_QUE_POSITION );
                    bookmark_btn.setImageDrawable ( getDrawable (  R.drawable.bookmark_border) );
                }else{
                    bookmarksList.add ( list.get ( position ) );
                    bookmark_btn.setImageDrawable ( getDrawable (  R.drawable.bookmarks) );
                }
            }
        } );


        setId = getIntent ().getStringExtra ( "setId");
        dialog = new Dialog ( this );
        dialog.setContentView ( R.layout.loading );
        dialog.getWindow ().setBackgroundDrawable ( getDrawable ( R.drawable.rounded_corners ) );
        dialog.getWindow ().setLayout ( LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable ( false );

        list = new ArrayList<> ();

        dialog.show ();
        myRef.child ( "SETS" ).child (setId)
                .addListenerForSingleValueEvent ( new ValueEventListener () {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren ()){
                            String id = dataSnapshot1.getKey ();
                            String question = dataSnapshot1.child ( "question" ).getValue ().toString ();
                            String a = dataSnapshot1.child ( "optionA" ).getValue ().toString ();
                            String b = dataSnapshot1.child ( "optionB" ).getValue ().toString ();
                            String c = dataSnapshot1.child ( "optionC" ).getValue ().toString ();
                            String d = dataSnapshot1.child ( "optionD" ).getValue ().toString ();
                            String correctANS = dataSnapshot1.child ( "correctANS" ).getValue ().toString ();

                            list.add ( new QuestionModel ( id,question,a,b,c,d,correctANS,setId ) );
                        }

                        if (list.size () > 0){
                            for (int i = 0; i < 4; i++) {
                                options_container.getChildAt ( i ).setOnClickListener ( new View.OnClickListener () {
                                    @Override
                                    public void onClick(View v) {
                                        checkAns ( (Button) v );
                                    }
                                } );
                            }
                            playAnim ( question, 0, list.get ( position ).getQuestion () );

                            next_btn.setOnClickListener ( new View.OnClickListener () {
                                @Override
                                public void onClick(View v) {
                                    next_btn.setEnabled ( false );
                                    next_btn.setAlpha ( 0.7f );
                                    enableOPtion (true);
                                    position++;
                                    if (position == list.size ()) {
                                        Intent intent = new Intent (QuestionsActivity.this,ScoreActivity.class);
                                        intent.putExtra ( "score",score );
                                        intent.putExtra ( "total",list.size () );
                                        startActivity ( intent );
                                        finish ();
                                        return;
                                    }
                                    COUNT = 0;
                                    playAnim ( question, 0, list.get ( position ).getQuestion () );
                                }
                            } );

                        }else {
                            finish ();
                            Toast.makeText ( QuestionsActivity.this, "No questions", Toast.LENGTH_SHORT ).show ();
                        }
                        dialog.dismiss ();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText ( QuestionsActivity.this, error.getMessage (), Toast.LENGTH_SHORT ).show ();
                        dialog.dismiss ();
                        finish ();
                    }
                } );

    }

    @Override
    protected void onPause() {
        super.onPause ();
        storeBookmarks ();
    }

    private void playAnim(View view, int value, final String data) {
        view.animate ().alpha ( value ).scaleX ( value ).scaleY ( value ).setDuration ( 500 ).setStartDelay ( 100 )
                .setInterpolator ( new DecelerateInterpolator () ).setListener ( new Animator.AnimatorListener () {
            @Override
            public void onAnimationStart(Animator animation) {
                String option = "";
                if (value == 0 && COUNT < 4) {
                    if (COUNT == 0) {
                        option = list.get ( position ).getA ();
                    } else if (COUNT == 1) {
                        option = list.get ( position ).getB ();

                    } else if (COUNT == 2) {
                        option = list.get ( position ).getC ();

                    } else if (COUNT == 3) {
                        option = list.get ( position ).getD ();


                    }
                    playAnim ( options_container.getChildAt ( COUNT ), 0, option );
                    COUNT++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0) {
                    try {
                        ((TextView) view).setText ( data );
                        no_indicator.setText ( position+1+"/"+list.size () );
                        if (modelMatch ()){
                            bookmark_btn.setImageDrawable ( getDrawable (  R.drawable.bookmarks) );

                        }else{
                            bookmark_btn.setImageDrawable ( getDrawable (  R.drawable.bookmark_border) );
                        }

                    } catch (ClassCastException ex) {
                        ((Button) view).setText ( data );

                    }
                    view.setTag ( data );
                    playAnim ( view, 1, data );
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        } );
    }

    private void checkAns(Button selectedOpt) {
        enableOPtion (false);
        next_btn.setEnabled ( true );
        next_btn.setAlpha ( 1 );
        if (selectedOpt.getText ().toString ().equals ( list.get ( position ).getAnswer () )) {
            score++;
            selectedOpt.setBackgroundTintList ( ColorStateList.valueOf ( Color.parseColor ( "#4CAF50" ) ) );
        } else {
            selectedOpt.setBackgroundTintList ( ColorStateList.valueOf ( Color.parseColor ( "#ff0000" ) ) );
            Button correctOption = (Button) options_container.findViewWithTag ( list.get ( position ).getAnswer () );
            correctOption.setBackgroundTintList ( ColorStateList.valueOf ( Color.parseColor ( "#4CAF50" ) ) );


        }

    }

    private void enableOPtion(boolean enable) {
        for (int i = 0; i < 4; i++) {
            options_container.getChildAt ( i ).setEnabled ( enable );
            if (enable){
                options_container.getChildAt ( i ).setBackgroundTintList ( ColorStateList.valueOf ( Color.parseColor ( "#F3F1F1" ) ) );


            }
        }
    }

    private void getBookmarks(){

       String json = preferences.getString ( KEY_NAME,"" );
        Type type = new TypeToken<List<QuestionModel>>(){}.getType ();
        bookmarksList = gson.fromJson ( json,type );

        if (bookmarksList == null){
            bookmarksList = new ArrayList<> ();
        }
    }

    private boolean modelMatch(){
        boolean matched = false;
        int i = 0;
        for ( QuestionModel model : bookmarksList){
            if (model.getQuestion ().equals ( list .get ( position ).getQuestion ())
            && model.getAnswer ().equals ( list.get ( position ).getAnswer () )
            && model.getSet ().equals ( list.get ( position ).getSet ())){
                matched = true;
                MATCHED_QUE_POSITION = i;
            }
            i++;
        }
        return matched;
    }

    private  void storeBookmarks(){

        String json = gson.toJson ( bookmarksList );
        editor.putString ( KEY_NAME,json );
        editor.commit ();
    }

}