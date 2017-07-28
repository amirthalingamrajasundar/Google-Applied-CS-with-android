package com.google.engedu.puzzle8;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class PuzzleBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;
    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();

    public PuzzleBoardView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
    }

    public void initialize(Bitmap imageBitmap) {
        int width = getWidth();
        puzzleBoard = new PuzzleBoard(imageBitmap, width);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (puzzleBoard != null) {
            if (animation != null && animation.size() > 0) {
                puzzleBoard = animation.remove(0);
                puzzleBoard.draw(canvas);
                if (animation.size() == 0) {
                    animation = null;
                    puzzleBoard.reset();
                    Toast toast = Toast.makeText(activity, "Solved! ", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                puzzleBoard.draw(canvas);
                invalidate();
            }
        }
    }

    public void shuffle() {
        if (animation == null && puzzleBoard != null) {
            // Do something. Then:

            ArrayList<PuzzleBoard>neighbour;

            for(int x=0;x<NUM_SHUFFLE_STEPS;x++) {

                neighbour = puzzleBoard.neighbours();

                Log.d("test", "returned to boardview");

                puzzleBoard = neighbour.get(random.nextInt(neighbour.size()));

                puzzleBoard.reset();
                invalidate();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation == null && puzzleBoard != null) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if (puzzleBoard.resolved()) {
                            Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(event);
    }



    public void solve() {
        puzzleBoard.previousBoard=null;
        ArrayList<PuzzleBoard> solution = new ArrayList<>();
        PuzzleBoard temp;
        Comparator<PuzzleBoard> PuzzleBoardComparator = new Comparator<PuzzleBoard>() {
            @Override
            public int compare(PuzzleBoard lhs, PuzzleBoard rhs) {
                if(lhs.priority()<rhs.priority()){
                    return -1;
                }
                else if(lhs.priority()>rhs.priority()){
                    return 1;
                }
                return 0;
            }
        };


        PriorityQueue<PuzzleBoard> puzzleBoardPriorityQueue = new PriorityQueue<>(10,PuzzleBoardComparator);

        puzzleBoardPriorityQueue.add(puzzleBoard);

        while (!puzzleBoardPriorityQueue.isEmpty()){

            temp = puzzleBoardPriorityQueue.remove();

            if(temp.resolved()){

                while(temp!=null) {

                    solution.add(temp);
                    temp = temp.previousBoard;
                }

                break;
            }

            else{
                ArrayList<PuzzleBoard> neighbours=temp.neighbours();
                for(PuzzleBoard t:neighbours) {
                    puzzleBoardPriorityQueue.add(t);
                }

            }
        }
        Collections.reverse(solution);
        this.animation=solution;
        this.draw(new Canvas());



    }


}
