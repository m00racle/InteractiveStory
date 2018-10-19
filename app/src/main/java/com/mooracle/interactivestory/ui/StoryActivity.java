package com.mooracle.interactivestory.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.mooracle.interactivestory.R;
import com.mooracle.interactivestory.model.Page;
import com.mooracle.interactivestory.model.Story;

import java.util.Stack;

public class StoryActivity extends AppCompatActivity {
    /*Creating Stack:*/
    private Stack<Integer> pageStack = new Stack<>();

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
        pageStack.push(pageNumber);/*<- push page number into stack to be popped out in Back navigation*/

        final Page page = story.getPage(pageNumber);

        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);
        animateStoryImageView();/*<- animate the story Image View*/


        String pageText = getString(page.getTextId());
        pageText = String.format(pageText, name); /*<- add user name if available none if not so it can be
        used in multiple pages!*/

        storyTextView.setText(pageText);

        /*Button text:*/
        if (page.isFinalPage()){
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(R.string.play_again_button_text);
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //finish(); /*<- back to main activity*/

                    pageStack.clear(); /*<- clearing the stack of previous pages*/

                    loadPage(0);/*<- restart game with same user name*/
                }
            });
        } else {
            loadButtons(page);
        }

    }

    private void animateStoryImageView() {
        /*animate the image view:*/
        ObjectAnimator fadeEffect = ObjectAnimator.ofFloat(storyImageView,"alpha", 1f, .3f, 1f);
        fadeEffect.setDuration(2000);
        /*This animation will fade out from alpha=1 to blur on alpha=0.3 and then fade in again from 0.3 to alpha = 1*/

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeEffect);

        /*set the animation listener keep it animated in loops*/
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }
        });
        animatorSet.start();
    }

    private void loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice2Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        choice2Button.setText(page.getChoice2().getTextId());

        /*repopulate stories based on the user clicks:*/
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });

        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
    }

    @Override
    public void onBackPressed() {
        pageStack.pop(); /*see the transcript for more explanation*/

        if (pageStack.isEmpty()) {
            super.onBackPressed();
        } else {
            loadPage(pageStack.pop());
        }
    }
}
