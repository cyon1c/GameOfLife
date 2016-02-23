package com.ethos.conwaysgameoflife;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Observer {

    int screenWidth;
    int screenHeight;

    @Bind(R.id.parent)
    AbsoluteLayout parent;
    @Bind(R.id.sizeSeekBar)
    SeekBar sizingBar;
    @Bind(R.id.seekBarCurrent)
    TextView seekBarCurrentLabel;
    @Bind(R.id.startButton)
    Button mStartButton;
    @Bind(R.id.generateButton)
    Button mGenerateButton;
    @Bind(R.id.stopButton)
    Button mStopButton;
    @Bind(R.id.totalCyclesLabel)
    TextView totalCyclesLabel;
    @Bind(R.id.averageCyclesPerSeed)
    TextView averageCyclesLabel;
    @Bind(R.id.cyclesInSeed)
    TextView seedsInCycleLabel;
    @Bind(R.id.numberSeeds)
    TextView numSeedsLabel;

    Game newGame;
    int cellSize = 25;

    String totalCycles;
    String totalSeeds;
    String cyclesInSeed;
    String avgCyclesPerSeed;

    int mTotalCycles = 0;
    int mCurrentCycles = 0;
    int mAverageCycles = 0;
    int mTotalSeeds = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        totalCycles = getString(R.string.total_cycles);
        totalSeeds = getString(R.string.total_seeds);
        cyclesInSeed = getString(R.string.num_cycles_seeds);
        avgCyclesPerSeed = getString(R.string.average_cycles);

        sizingBar.setMax(175);
        sizingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cellSize = i + 25;
                seekBarCurrentLabel.setText("Current: " + cellSize + "px");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateGame();
            }
        });

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame.startGame();
                mTotalCycles = 0;
                mTotalSeeds = 1;
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame.stopGame();
            }
        });


        totalCyclesLabel.setText(String.format(totalCycles, 0));
        numSeedsLabel.setText(String.format(totalSeeds, 0));
        seedsInCycleLabel.setText(String.format(cyclesInSeed, 0));
        averageCyclesLabel.setText(String.format(avgCyclesPerSeed, 0));

    }

    private void generateGame(){
        if(parent.getChildCount() > 0){
            parent.removeAllViews();
        }
        int numColumns = screenWidth/cellSize;
        int numRows;

        numColumns = numColumns -1;
        numRows = numColumns;

        AbsoluteLayout.LayoutParams params;

        ArrayList<Cell> cells = new ArrayList<>();
        Context context = this;

        View temp;
        for(int i =0 ; i < numRows; i++){
            for(int j = 0; j < numColumns; j++){

                temp = new View(context);
                params = new AbsoluteLayout.LayoutParams(cellSize, cellSize, (j*cellSize), (i*cellSize));
                temp.setLayoutParams(params);
                parent.addView(temp);
                if((int)(Math.random() * 100) % 4 == 0){
                    cells.add(new Cell(temp, true));
                }else{
                    cells.add(new Cell(temp, false));
                }

            }
        }

        newGame = new Game(this.getApplicationContext(), numRows, numColumns, cells, this);
    }

    @Override
    public void update(Observable observable, Object o) {
        final Bundle b = (Bundle)o;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(b.getBoolean("counter")){
                    mCurrentCycles++;
                    mTotalCycles++;
                }else {
                    mCurrentCycles = 0;
                    mTotalSeeds += 1;
                }

                totalCyclesLabel.setText(String.format(totalCycles, mTotalCycles));
                numSeedsLabel.setText(String.format(totalSeeds, mTotalSeeds));
                seedsInCycleLabel.setText(String.format(cyclesInSeed, mCurrentCycles));
                averageCyclesLabel.setText(String.format(avgCyclesPerSeed, mTotalCycles/mTotalSeeds));
            }
        });
    }
}
