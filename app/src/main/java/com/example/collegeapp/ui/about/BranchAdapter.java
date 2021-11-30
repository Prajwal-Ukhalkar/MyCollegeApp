package com.example.collegeapp.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.collegeapp.R;

import java.util.List;

public class BranchAdapter extends PagerAdapter {
    Context context;

    public BranchAdapter(Context context, List<BranchModel> list) {
        this.context = context;
        this.list = list;
    }

    private List<BranchModel> list;     //angular brackets madhe to class dyaycha jyaca data aplyala firestore madhun add karaycha age..examle (ithe firestore madhun Branchadapter class che attrutes(img,title desc get karache ahet))

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.branch_item_layout, container, false);
        ImageView branchIcon;
        TextView branchTitle, branchDesc;

        branchIcon = view.findViewById(R.id.branch_icon);
        branchTitle = view.findViewById(R.id.branch_title);
        branchDesc = view.findViewById(R.id.branch_decs);

        branchIcon.setImageResource(list.get(position).getImg());

        branchTitle.setText(list.get(position).getTitle());

        branchDesc.setText(list.get(position).getDescription());

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            super.destroyItem(container, position, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        container.removeView((View) object);
    }
}


