package com.example.miwok;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

public class Word {
    /**
     * mDefaultTranslation: Chosen language for the user
     * mMiwokTranslation: Miwok translation of the word
     */
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mAudioResourceID;
    private int mImageResourceID = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Constructor for the Word class
     * @param defaultTranslation = mDefaultTranslation
     * @param miwokTranslation = mMiwokTranslation
     */
    public Word(String defaultTranslation, String miwokTranslation, int AudioResourceID){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceID = AudioResourceID;
    }
    public Word(String defaultTranslation, String miwokTranslation, int ImageResourceID, int AudioResourceID){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceID = ImageResourceID;
        mAudioResourceID = AudioResourceID;
    }

    /**
     * Getter methods for the attribute
     * @return Translations/ corresponding image
     */
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }
    public int getImageResourceID(){
        return mImageResourceID;
    }
    public int getAudioResourceID(){return mAudioResourceID;}

    /**
     * Used to check if an image is given
     * @return true is provided, else returns false
     */
    public boolean hasImage(){
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mAudioResourceID=" + mAudioResourceID + '\'' +
                ", mImageResourceID=" + mImageResourceID +
                '}';
    }
}
