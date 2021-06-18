package com.example.miwok;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter {

    /**
     * Variable used to hold the color resource ID
     */
    private int mColorResourceID;

    /**
     * @param ColorResourceID background color resource ID of the View
     * @param context context of the app
     * @param words refers to objects of the Word CLass
     */
    public WordAdapter(@NonNull Context context, ArrayList<Word> words, int ColorResourceID) {

        /**
         * Calling the super class {@link ArrayAdapter} constructor.
         * We pass "0" as the resource ID because we will handle the inflating of the layout ourselves.
         */
        super(context, 0, words);

        mColorResourceID = ColorResourceID;
    }

    /**
     * Provides a View for an AdapterView (in this case listView)
     * @param position The AdapterView position that is requesting a View
     * @param convertView The recycled View that needs to be populated.
     * @param parent The parent ViewGroup that is used for the inflation
     * @return the View for the position in the AdapterView
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /**
         * Get the {@link Word} object located in this position in the list
         */
        Word currentWord = (Word) getItem(position);

        /**
         * Assigning the recycled View to a variable to manipulate it
         */
        View listItemView = convertView;

        /**
         * Check if the existing view is being reused, otherwise inflate the view.
         * The recycle View can be null (when creating the activity for the first time to fill up the screen).
         * If null, we inflate (turns xml layout files into actual View objects) a new listItemView from list_item.xml.
         */
        if(listItemView == null){
            /**
             * The first parameter in the inflate method is the ID for the xml layout resource to load.
             * The second parameter is an optional View to be the parent of the generated hierarchy (if "attachToRoot" is true), else it's simply an object that provides a set of LayoutParams (LayoutParams are used by views to tell their parents how they want to be laid out) values for root of the returned hierarchy.
             * The third parameter:
             *                      if true: the inflated hierarchy will be attached to the parent {@link parent}.
             *                      if false: {@link parent} will only be used to create the correct subclass LayoutParams of  for the root View in the xml.
             */
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        /**
         * Setting the text of the TextView "miwokTextView" to the Miwok translation.
         * We get the Miwok translation from the getMiwokTranslation method of the {@link currentWord} object.
         */
        TextView miwokTextView = listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        /**
         * Setting the text of the TextView "defaultTextView" to the default translation.
         * We get the default translation from the getDefaultTranslation method of the {@link currentWord} object.
         */
        TextView defaultTextView = listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());


        /**
         ** Setting the image of the ImageView "iconView) to the corresponding image.
         */
        ImageView iconView = listItemView.findViewById(R.id.image);

        /**
         * If an image is provide it, add it to {@link listItemView}
         * Else only add the TextViews
         */
        if(currentWord.hasImage()){
            /**
             * We ge the corresponding image from the getImageResourceID method of the {@link currentWord} object.
             */
            iconView.setImageResource(currentWord.getImageResourceID());
        }else{
            iconView.setVisibility(View.GONE);
        }

        /**
         * Assigning the LinearLayout containing the TextViews to a variable to manipulate it
         * The code contained inside the setBackgroundColor method is used to find the color the resource ID maps to
         */
        View textContainer = listItemView.findViewById(R.id.text_container);
        textContainer.setBackgroundColor(ContextCompat.getColor(getContext(), mColorResourceID));

        /**
         * @return the View with the updated information
         */
        return listItemView;
    }
}
