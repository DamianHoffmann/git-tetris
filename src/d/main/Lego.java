package d.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lego   {
    private BufferedImage box;
    private Mypanel mypanel;
    private int[][] coords;
    private int color;
    private int x, y;
    private int deltaX;
    private int currentSpeed;
    private int normalSpeed = 600, SpeedDown = 50;
    private long lastTime, time;
    private boolean endOfLine = false, endOfMove = false;

    public Lego(BufferedImage box, int[][] coords, Mypanel mypanel, int color){
        this.box = box;
        this.coords = coords;
        this.mypanel = mypanel;
        this.color = color;
        // starting points
        x = 3;
        y = -1;
        time = 0;
        lastTime = 0;
        currentSpeed = normalSpeed;
    }

    public void update (){
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(endOfLine) {
            if(time>currentSpeed) {
                for (int row = 0; row < coords.length; row++) {
                    for (int col = 0; col < coords[row].length; col++) {
                        if (coords[row][col] != 0) {
                            mypanel.getMypanel()[y + row][x + col] = color;
                        }}}
                clearLine();
                mypanel.makeLego();
            }}
        // dropping box down
        if(y + 1 + coords.length <=20){
            for (int row = 0; row < coords.length; row++ ){
                for (int col = 0; col < coords[row].length; col++) {
                    if(coords[row][col] !=0){
                        if(mypanel.getMypanel()[y+ row + 1][x +col] != 0){
                            endOfLine = true;
                        }}}}
        if (time > currentSpeed)
        {
            y++;
          time = 0;
        }

        } else{

        endOfLine = true;
        }

        if (x + deltaX + coords[0].length <= 10 && x +deltaX >= 0) {
            for (int row = 0; row < coords.length; row++ ){
                for (int col = 0; col < coords[row].length; col++) {
                    if(coords[row][col] !=0){
                       if (mypanel.getMypanel()[y+row][x + col +deltaX] !=0){
                           endOfMove = false;
                           System.out.println(mypanel.getMypanel()[y+row][x + col +deltaX] );
                       }}}}
                if(endOfMove)
            x += deltaX;
        }
        endOfMove = true;
        deltaX=0;
    }

    public void render (Graphics g){
        for(int row = 0; row < coords.length; row++){
            for(int col = 0; col <coords[row].length; col++){
                if(coords[row][col] != 0) {
                    g.drawImage(box, col * mypanel.getBlockSize() + x*mypanel.getBlockSize(),
                            row * mypanel.getBlockSize() +y*mypanel.getBlockSize(), null);
                }}}}

    private void clearLine(){
        int checkHeight = mypanel.getMypanel().length - 1;
        for(int i = checkHeight; i > 0; i--){
            int count = 0;
            for(int j = 0; j < mypanel.getMypanel()[0].length ; j++){
                if(mypanel.getMypanel()[i][j] != 0) {
                    count++;
                }
                mypanel.getMypanel()[checkHeight][j] = mypanel.getMypanel()[i][j];
                }
                if(count < mypanel.getMypanel()[0].length){
                checkHeight--;
                }}}

    private int [][] transposeLego(int[][] matrix) {
        int newMatrix[][] = new int[matrix[0].length][matrix.length];
                for (int i = 0; i <matrix[0].length; i++) {
                    for (int j = 0  ; j < matrix.length; j++) {
                        newMatrix[i][j] = matrix[j][i];
                        }}
        return newMatrix;
    }

    private int[][] reverseLego(int[][] matrix) {
        int mid = matrix.length / 2;
        for (int i = 0; i < mid; i++) {
            int[] m = matrix[i];
            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = m;
        }
        return matrix;
    }


    public void rotateLego() {
        if(endOfLine){
            return;
        }
        int clearlego[][] = null;

        clearlego = reverseLego(coords);
        clearlego = transposeLego(clearlego);



        if (x + clearlego[0].length >= 11 || x + clearlego[0].length < 0 || y + clearlego.length > 20) {
            return;
        }
        for(int row = 0; row < clearlego.length; row++){
            for(int col = 0; col<clearlego[0].length; col++){
                if(mypanel.getMypanel()[y +row][x+col] !=0){
                    return;
                }
            }
        }
        coords = clearlego;
    }


    // moving box horizontally
    public void setDeltaX(int deltaX){
        this.deltaX = deltaX;
    }

    public void setSpeedDown() {
        currentSpeed = SpeedDown;
    }

    public void setSpeedNormal() {
        currentSpeed = normalSpeed;
    }


    public int[][] getCoords() {
        return coords;
    }

    public BufferedImage getBox() {
        return box;
    }

    public int getColor() {
        return color;
    }






}



