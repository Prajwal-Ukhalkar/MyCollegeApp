package com.example.collegeapp.ebook;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EbookActivity extends AppCompatActivity {
    private RecyclerView ebookRecycler;
    private DatabaseReference reference,dbRef;
    private List<EbookData> list;
    private EbookAdapter adapter;

    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        getSupportActionBar ().setTitle ( "Ebooks" );

        shimmerFrameLayout = findViewById ( R.id.shimmerFrameLayout );
        shimmerLayout = findViewById ( R.id.shimmer_layout );
        shimmerLayout.setVisibility ( View.GONE );

        ebookRecycler = findViewById(R.id.ebookRecycler);
        reference = FirebaseDatabase.getInstance().getReference().child("PDF");

        getData();

    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    EbookData data = snapshot.getValue(EbookData.class);
                    list.add(data);
                    adapter = new EbookAdapter(EbookActivity.this,list);
                    ebookRecycler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                    ebookRecycler.setAdapter(adapter);
                    shimmerLayout.setVisibility ( View.GONE );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EbookActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });

    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer ();
        super.onPause ();

    }


    @Override
    protected void onResume() {
        shimmerFrameLayout.stopShimmer ();
        super.onResume ();


    }


}
