package com.example.holleworld.DATA;

import android.widget.EditText;

public class Search {
    EditText EText;
    boolean isInSearch;
    int CurrentIndex;

    public EditText getEText() {
        return EText;
    }

    public void setEText(EditText EText) {
        this.EText = EText;
    }

    public boolean isInSearch() {
        return isInSearch;
    }

    public void setInSearch(boolean inSearch) {
        isInSearch = inSearch;
    }

    public int getCurrentIndex() {
        return CurrentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        CurrentIndex = currentIndex;
    }

    public Search(EditText ET){
        EText=ET;
    }
}
