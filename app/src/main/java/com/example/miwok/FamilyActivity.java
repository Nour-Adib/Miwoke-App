package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    /**
     * The OnAudioFocusListener tells the app what to do depending on the focus state.
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        /**
         * Create and setup the {@link AudioManager} to request audio focus
         */
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /**
         * Creating an {@link ArrayList} to hold the number values.
         */
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әta", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        /**
         * Adapter knows how to create layouts for each item in the List using the simple_list_item_1.xml layout resource
         * defines in the Android framework.
         * This list item layout contains a single {@link TextView}, which the adapter will set to display a single word.
         */
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);

        /**
         * Find the {@link ListView} object in the View hierarchy of the Activity.
         * There should be a {@link ListView} with the view ID called "list", which is declared in
         * "activity_number.xml" layout file.
         */
        ListView listView = (ListView) findViewById(R.id.familyList);

        /**
         * Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
         * {@link ListView} will display list items for each in the list of words.
         * Do this by calling setAdapter method on the {@link ListView} object and pass in
         * 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
         */
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                /**
                 * Get the {@link Word} object at the given position the user clicked on.
                 */
                Word word = words.get(position);

                releaseMediaPlayer();

                /**
                 * Request audio focus for playback.
                 */
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                /**
                 * If the Audio focus is granted.
                 */
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    /**
                     * Create and setup the {@link MediaPlayer} for the audio resource associated with the current word.
                     */
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceID());

                    /**
                     * Start the audio file
                     */
                    mMediaPlayer.start();

                    /**
                     * Adding OnComplstionListener to {@link mMediaPlayer}
                     */
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        /**
         *  If the media player is not null, then it may be currently playing a sound.
         */
        if (mMediaPlayer != null) {

            /**
             * Regardless of the current state of the media player, release its resources ecause we no longer need it.
             */
            mMediaPlayer.release();

            /**
             * Set the media player back to null. For the code, we've decided that
             * setting the {@link MediaPlayer} to null is an easy way to tell that the {@link MediaPlayer}
             * is not configured to play an audio file at the moment.
             */
            mMediaPlayer = null;

            /**
             * Abandon the audio focus
             */
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    /**
     * Release the audio resources when the activity is stopped.
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}