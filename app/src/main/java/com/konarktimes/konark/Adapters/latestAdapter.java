package com.konarktimes.konark.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.konarktimes.konark.DetailsActivity;
import com.konarktimes.konark.Model.Posts;
import com.konarktimes.konark.R;

import java.util.ArrayList;

public class latestAdapter extends RecyclerView.Adapter<latestAdapter.latestAdapterViewHolder> {
    Context context;
    ArrayList<Posts> posts;

    public latestAdapter(Context context, ArrayList<Posts> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public latestAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.latest_layout,viewGroup,false);
        return new latestAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull latestAdapterViewHolder holder, int i) {
        final Posts obj=posts.get(i);
        holder.title.setText(obj.getTitle());
        if(obj.getName()!=null)
        holder.category.setText(obj.getName());
        if(obj.getCreated_at()!=null)
        holder.date.setText(obj.getCreated_at());
        Glide.with(context).load(obj.getImage_url()).into(holder.image);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailsActivity.class);
                intent.putExtra("title",obj.getTitle());
                intent.putExtra("content",obj.getContent());
                intent.putExtra("image_url",obj.getImage_url());
                intent.putExtra("created_at",obj.getCreated_at());
                intent.putExtra("name",obj.getName());//Category
                intent.putExtra("summary",obj.getSummary());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class latestAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title,date,category;
        LinearLayout linearLayout;
        public latestAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            category=itemView.findViewById(R.id.category);
            linearLayout=itemView.findViewById(R.id.linear);
        }
    }
}
