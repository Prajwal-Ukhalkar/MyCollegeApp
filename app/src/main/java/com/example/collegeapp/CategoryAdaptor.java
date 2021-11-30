package com.example.collegeapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
    private List<CategoryModel>  list;

    public CategoryAdaptor(List<CategoryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.cat_item,parent,false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData ( list.get ( position ).getUrl () ,list.get ( position ).getName (), position);

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView imageView;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );
            imageView = itemView.findViewById ( R.id.image_view );
            title = itemView.findViewById ( R.id.cat_title );
        }
        private void setData(String url,String title, final int position){
            Glide.with ( itemView.getContext () ).load ( url ).into ( imageView );
            this.title.setText ( title );

            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext (),SetsActivity.class);
                    intent.putExtra ( "title",title );
                    intent.putExtra ( "position",position );
                    itemView.getContext ().startActivity ( intent );

                }
            } );


        }
    }
}
