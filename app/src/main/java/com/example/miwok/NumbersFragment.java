package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NumbersFragment} factory method to
 * create an instance of this fragment.
 */
public class NumbersFragment extends Fragment {

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

    /**
     ************************DEFAULT VARIABLES*************************************
     */
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NumbersFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        /**
         * Create and setup the {@link AudioManager} to request audio focus
         */
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        /**
         * Creating an {@link ArrayList} to hold the number values.
         */
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        /**
         * Adapter knows how to create layouts for each item in the List using the simple_list_item_1.xml layout resource
         * defines in the Android framework.
         * This list item layout contains a single {@link TextView}, which the adapter will set to display a single word.
         */
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        /**
         * Find the {@link ListView} object in the View hierarchy of the Activity.
         * There should be a {@link ListView} with the view ID called "list", which is declared in
         * "activity_number.xml" layout file.
         */
        ListView listView = rootView.findViewById(R.id.list);

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
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceID());

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

        return rootView;
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

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
