package com.labkod.karikatup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ComicAdapter extends ArrayAdapter<Comic> {
    public ComicAdapter(Context context, ArrayList<Comic> comics) {
        super(context, 0, comics);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comic comic = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.asdasd, parent, false);
        }
        // Lookup view for data population
        TextView mComicUser = (TextView) convertView.findViewById(R.id.comicUser);
        TextView mComicDate = (TextView) convertView.findViewById(R.id.comicDate);
        ImageView mComicImage = (ImageView) convertView.findViewById(R.id.comicImage);
        TextView mComicLike = (TextView) convertView.findViewById(R.id.comicLike);
        TextView mComicComment = (TextView) convertView.findViewById(R.id.comicComment);
        TextView mComicAuthor = (TextView) convertView.findViewById(R.id.comicAuthor);
        // Populate the data into the template view using the data object
        mComicUser.setText(comic.username);
        mComicDate.setText(comic.date);
        new DownloadImageTask((ImageView) convertView.findViewById(R.id.comicImage)).execute(comic.image);
        mComicLike.setText(comic.likeCount.toString());
        mComicComment.setText(comic.commentCount.toString());
        mComicAuthor.setText(comic.author);
        // Return the completed view to render on screen
        return convertView;
    }
}
