package com.mooracle.interactivestory.model;

public class Page {
    /*member variables definition:*/
    private int imageId; /*<- image id to refer to data of image to be displayed by presenter in a page*/
    private int textId; /*<- This is used to access string.xml resources*/
    private Choice choice1;
    private Choice choice2;
    /*NOTE: Since we only set two choices for each page this practice is used but for more choices for each page we
    * need to consider an array of Choices*/

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public Choice getChoice1() {
        return choice1;
    }

    public void setChoice1(Choice choice1) {
        this.choice1 = choice1;
    }

    public Choice getChoice2() {
        return choice2;
    }

    public void setChoice2(Choice choice2) {
        this.choice2 = choice2;
    }
}
