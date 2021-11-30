package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.collegeapp.authentication.LoginActivity;
import com.example.collegeapp.ebook.EbookActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.File;
import java.util.Objects;
import java.util.PrimitiveIterator;

import me.ibrahimsn.lib.SmoothBottomBar;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private MenuItem share;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected;
    private final String CHECKEDITEM = "checked_item";
    private FirebaseAuth auth;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences ( "themes",MODE_PRIVATE );
        editor = sharedPreferences.edit ();
        auth = FirebaseAuth.getInstance ();

        FirebaseMessaging.getInstance ().subscribeToTopic ( "notification" );





        switch (getCheckedItem ()){
            case 0:
                // This class is used to apply dark and light themes in android
                AppCompatDelegate.setDefaultNightMode ( MODE_NIGHT_FOLLOW_SYSTEM );
                break;

            case 1:
                AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_YES );

                break;

            case 2:
                AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_NO );

                break;

        }





        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,R.id.frame_layout);
        share = findViewById ( R.id.share );

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        navigationView.setItemIconTintList(null); //to display icons in original color


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate ( R.menu.option_menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        if (item.getItemId () == R.id.logout){
            auth.signOut ();
            openLogin();
        }
        return true;

    }

    private void openLogin() {
        startActivity ( new Intent (MainActivity.this, LoginActivity.class ) );
        finish ();
    }

    @Override
    protected void onStart() {
        super.onStart ();
        if (auth.getCurrentUser () == null){
            openLogin ();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_developer:
                startActivity(new Intent(this, DeveloperActivity2.class));
                break;

                case R.id.navigation_vedio:
                    gotoUrl1 ( "https://www.youtube.com/watch?v=IYslOhTCMgQ&t=32s" );
                    break;


            case R.id.navigation_rate:
                Toast.makeText(this, "Upload app to playstore first", Toast.LENGTH_SHORT).show();
                break;

                case R.id.navigation_ebook:
                    startActivity(new Intent(this, EbookActivity.class));

                break;

                case R.id.navigation_theme:
                    startActivity(new Intent(this, MainActivity2.class));
                break;

                case R.id.navigation_website:
                    gotoUrl("https://www.gpabad.ac.in/");
                    break;

                case R.id.share:
                    try{
                        Intent intent = new Intent (Intent.ACTION_SEND);
                        intent.setType ( "text/plain" );
                        intent.putExtra ( Intent.EXTRA_SUBJECT,"share demo" );
                        String msg = "https://play.google.com/store/apps/details?=" + BuildConfig.APPLICATION_ID+ "\n\n";
                        intent.putExtra ( Intent.EXTRA_TEXT,msg );
                        startActivity ( Intent.createChooser ( intent,"Share Via" ) );
                    }catch (Exception e){
                        Toast.makeText ( this, "Something went wrong", Toast.LENGTH_SHORT ).show ();

                    }
                        break;

            case R.id.navigation_color:
                try {
                    showDialog();
                } catch (Exception e) {
                    e.printStackTrace ();
                }
                break;

        }
        return true;
    }

    private void showDialog() {

        String[] themes = this.getResources ().getStringArray ( R.array.theme );

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder ( this );
        builder.setTitle ( "Select Theme" );
        builder.setSingleChoiceItems ( R.array.theme, getCheckedItem (), new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                selected = themes[i];
                checkedItem = i;
            }
        } );

        builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (selected == null){
                    selected = themes[i];
                    checkedItem = i;

                }

                switch (selected){
                    case "System default":
                        // This class is used to apply dark and light themes in android
                        AppCompatDelegate.setDefaultNightMode ( MODE_NIGHT_FOLLOW_SYSTEM );
                        break;

                    case "Dark":
                        AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_YES );

                        break;

                    case "Light":
                        AppCompatDelegate.setDefaultNightMode ( AppCompatDelegate.MODE_NIGHT_NO );

                        break;

                }
                setCheckedItem ( checkedItem );

            }
        } );

        builder.setNegativeButton ( "Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss ();
            }
        } );

        AlertDialog dialog = builder.create ();
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    private void gotoUrl1(String s) {
        Uri uri = Uri.parse ( s );
        startActivity ( new Intent (Intent.ACTION_VIEW,uri) );

    }

    private int getCheckedItem(){
        return sharedPreferences.getInt ( CHECKEDITEM,0 );
    }

    private void setCheckedItem(int i){
        editor.putInt ( CHECKEDITEM,i );
        editor.apply ();

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse ( s );
        startActivity ( new Intent (Intent.ACTION_VIEW,uri) );
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen ( GravityCompat.START )){
        drawerLayout.closeDrawer ( GravityCompat.START );
        }else{
            super.onBackPressed ();
        }
    }
}