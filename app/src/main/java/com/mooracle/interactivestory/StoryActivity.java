package com.mooracle.interactivestory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class StoryActivity extends AppCompatActivity {

    /*We want to add a log in Logcat thus we need to add TAG:*/
    public static final String TAG = StoryActivity.class.getSimpleName();/*<- this will set the TAG as simple name
    of the class. If we ever refactor to rename this class the TAG value will automatically changed!*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        /*Get the intent from MainActivity*/
        Intent intent = getIntent(); /*<- return the intent that started this activity*/

        String name = intent.getStringExtra(getString(R.string.key_name)); /*<-this using the resources"*/

        if (name == null || name.isEmpty()){name = "Friend";} /*<- this to avoid Null Pointer Exception and blanks*/


        /*we want to check it out using Logcat thus:*/
        Log.d(TAG, name); /*<- Note that variable name is set as String above*/
    }
}
