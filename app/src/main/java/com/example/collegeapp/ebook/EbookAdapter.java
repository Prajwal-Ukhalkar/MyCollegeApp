package com.example.collegeapp.ebook;

import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;

import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    private Context context;
    private List<EbookData> list;


    public EbookAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item_layout,parent,false);
        return new EbookViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {

      holder.ebookName.setText ( "PDF");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData ( Uri.parse ( list.get ( position ).getPdfUrl () ) );
                context.startActivity ( intent );
            }
        });

        holder.ebookDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrl()));
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }



    public class EbookViewHolder extends RecyclerView.ViewHolder {

        private  TextView ebookName;
        private final ImageView ebookDownload;


        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);

            ebookName = itemView.findViewById(R.id.ebookName);
            ebookDownload = itemView.findViewById(R.id.ebookDownload);

        }
    }

}
