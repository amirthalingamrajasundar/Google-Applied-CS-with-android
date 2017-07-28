package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;


public class PuzzleBoard {
    private int steps=0;
    public PuzzleBoard previousBoard;
    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<PuzzleTile> tiles;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        tiles = new ArrayList<>();
        int width = bitmap.getWidth()/NUM_TILES;
        int height = bitmap.getHeight()/NUM_TILES;
        Bitmap temp;
        int j=0;
        for(int x=0;x<NUM_TILES;x++){
            for(int y=0;y<NUM_TILES;y++){
                temp = Bitmap.createBitmap(bitmap,y*width,x*height,width,height);
                temp = Bitmap.createScaledBitmap(temp,parentWidth/3,parentWidth/3,true);
                if(j!=8){
                    tiles.add(new PuzzleTile(temp,j++));
                }
                else{
                    tiles.add(null);
                }
            }
        }
    }

    PuzzleBoard(PuzzleBoard otherBoard) {

        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        this.previousBoard=otherBoard;
        this.steps=otherBoard.steps+1;
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {
        ArrayList<PuzzleBoard> neighbour = new ArrayList<>();
        int null_block=0;
        int temp=0;
        int j;
        PuzzleBoard puzzleBoard = new PuzzleBoard(this);
        for(null_block=0;null_block<NUM_TILES*NUM_TILES;null_block++){
            if(tiles.get(null_block)==null)
                break;
        }
        Log.d("test","null = "+null_block);

        for(int x[]:NEIGHBOUR_COORDS){
            temp=x[0]*NUM_TILES+x[1];
            j=null_block+temp;
            if((j>=0) && (j<=8) ){
                if (((temp == 1 && j % NUM_TILES != 0) || (temp == -1 && j % NUM_TILES != 2) || temp == 3 || temp == -3)) {
                    puzzleBoard = new PuzzleBoard(this);
                    puzzleBoard.swapTiles(null_block, j);
                    neighbour.add(puzzleBoard);
                }
            }
        }
        Log.d("test","completed loop");
        return neighbour;
    }

    public int priority() {
        int vertical=0,horizontal=0;

        for(int i=0;i<NUM_TILES*NUM_TILES;i++) {
            if (tiles.get(i) != null) {
                vertical = vertical + Math.abs(i / NUM_TILES - tiles.get(i).getNumber() / NUM_TILES);
                horizontal = horizontal + Math.abs(i % NUM_TILES - tiles.get(i).getNumber() % NUM_TILES);
            }
        }
        return vertical+horizontal+this.steps;
    }

}
