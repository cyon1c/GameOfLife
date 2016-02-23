package com.ethos.conwaysgameoflife;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by john.white on 2/18/16.
 */
public class Cell {

    View mView;
    boolean mAlive;

    public Cell(View view, boolean alive){
        mView = view;
        mAlive = alive;
    }

    public boolean isAlive(){return mAlive;}

    public void setAlive(boolean a, Drawable background){
        mAlive = a;
        mView.setBackground(background);
    }

    public void setBackground(Drawable background){
        mView.setBackground(background);
    }
}
