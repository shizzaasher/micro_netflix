package com.example.affirmations.adapter;

import com.example.affirmations.activities.MovieScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.affirmations.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<Integer> mVotes = new ArrayList<>();
    private ArrayList<String> mLanguages = new ArrayList<>();
    private ArrayList<String> mOverviews = new ArrayList<>();
    private ArrayList<Float> mRatings = new ArrayList<>();
    private Context mContext;

    public ItemAdapter(Context context, ArrayList<String> names, ArrayList<String> imageURLs, ArrayList<String> dates, ArrayList<Integer> votes, ArrayList<String> languages, ArrayList<String> overviews, ArrayList<Float> ratings) {
        mNames = names;
        mImageUrls = imageURLs;
        mDates = dates;
        mVotes = votes;
        mLanguages = languages;
        mOverviews = overviews;
        mContext = context;
        mRatings = ratings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);
        holder.text.setText(mNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            text = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(mContext, MovieScreen.class);
            intent.putExtra("title", mNames.get(position));
            intent.putExtra("url", mImageUrls.get(position));
            intent.putExtra("release_date", mDates.get(position));
            intent.putExtra("vote_count", mVotes.get(position));
            intent.putExtra("original_language", mLanguages.get(position));
            intent.putExtra("overview", mOverviews.get(position));
            intent.putExtra("vote_average", mRatings.get(position));
            mContext.startActivity(intent);
        }
    }
}