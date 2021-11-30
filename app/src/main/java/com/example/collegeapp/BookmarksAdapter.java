package com.example.collegeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.Viewholder> {
    private List<QuestionModel> list;

    public BookmarksAdapter(List<QuestionModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate view
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.bookmark_item,parent,false );
        return new Viewholder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.setData ( list.get ( position ).getQuestion (),list.get ( position ).getAnswer () ,position);

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        private ImageButton deleteBtn;
        private TextView question,answer;
        public Viewholder(@NonNull View itemView) {
            super ( itemView );
            question = itemView.findViewById ( R.id.question );
            answer = itemView.findViewById ( R.id.answer );
            deleteBtn = itemView.findViewById ( R.id.deleteBtn );
        }
        private void setData(String question,String answer,int position){
            this.question.setText(question);
            this.answer.setText(answer);

            deleteBtn.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    list.remove ( position );
                    notifyItemRemoved ( position );
                }
            } );
        }
    }
}
