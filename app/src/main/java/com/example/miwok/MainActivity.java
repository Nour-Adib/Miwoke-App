package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    /**
     * Called when the app is launched
     * Displays the specified layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Assigning the TextView to a variable "numbers"
         */
        TextView numbers = (TextView) findViewById(R.id.numbers);
        /**
         * Setting the onClickListener to the TextView
         */
        numbers.setOnClickListener(new View.OnClickListener(){
            /**
             * Sends User to the NumberActivity Screen
             * @param view
             */
            @Override
            public void onClick(View view){
                Intent numbersIntent = new Intent(MainActivity.this, NumberActivity.class);
                startActivity(numbersIntent);
            }
        });
        TextView family = (TextView) findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener(){
            /**
             * Sends User to the FamilyActivity Screen
             * @param view
             */
            @Override
            public void onClick(View view){
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });
        TextView colors = (TextView) findViewById(R.id.colors);
        colors.setOnClickListener(new View.OnClickListener(){
            /**
             * Sends User to the ColorsActivity Screen
             * @param view
             */
            @Override
            public void onClick(View view){
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });
        TextView phrases = (TextView) findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener(){
            /**
             * Sends User to the PhrasesActivity Screen
             * @param view
             */
            @Override
            public void onClick(View view){
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });
    }

}