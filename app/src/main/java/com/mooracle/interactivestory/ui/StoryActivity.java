package com.mooracle.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.mooracle.interactivestory.R;
import com.mooracle.interactivestory.model.Page;
import com.mooracle.interactivestory.model.Story;

public class StoryActivity extends AppCompatActivity {

    /*We want to add a log in Logcat thus we need to add TAG:*/
    public static final String TAG = StoryActivity.class.getSimpleName();/*<- this will set the TAG as simple name
    of the class. If we ever refactor to rename this class the TAG value will automatically changed!*/

    private Story story; /*<- new member variable*/
    private String name;

    private ImageView storyImageView;
    private TextView storyTextView;

    private Button choice1Button;
    private Button choice2Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.storyImageView);
        storyTextView = findViewById(R.id.storyTextView);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);

        /*Get the intent from MainActivity*/
        Intent intent = getIntent(); /*<- return the intent that started this activity*/

        name = intent.getStringExtra(getString(R.string.key_name)); /*<-this using the resources"*/

        if (name == null || name.isEmpty()){name = "Friend";} /*<- this to avoid Null Pointer Exception and blanks*/

        /*we want to check it out using Logcat thus:*/
        Log.d(TAG, name); /*<- Note that variable name is set as String above*/

        story = new Story(); /*<- default constructor does not need any arguments*/

        /*loading page when activity is onCreate:*/
        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        Page page = story.getPage(pageNumber);
        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);

        String pageText = getString(page.getTextId());
        pageText = String.format(pageText, name); /*<- add user name if available none if not so it can be
        used in multiple pages!*/

        storyTextView.setText(pageText);

        /*Button text:*/
        choice1Button.setText(page.getChoice1().getTextId());
        choice2Button.setText(page.getChoice2().getTextId());
    }
}
