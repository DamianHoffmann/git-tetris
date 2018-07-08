package d.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class Lego   {
    private BufferedImage box;
    private MyPanel myPanel;
    private int[][] coords;
    private int color;
    private int StartPY;
    private int StartPX;
    private int positionOfX;
    private int currentSpeed;
    private int normalSpeed = 600, SpeedDown = 50;
    private long lastTime, time;
    private boolean endOfLine = false, endOfMove = false;

    public Lego(BufferedImage box, int[][] coords, MyPanel myPanel, int color){
        this.box = box;
        this.coords = coords;
        this.myPanel = myPanel;
        this.color = color;
        // starting points
        StartPX = 3;
        StartPY = -1;
        time = 0;
        lastTime = 0;
        currentSpeed = normalSpeed;
    }

    public void update (){
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        checkEndOfLine(); // check End of Line and make new Lego (if End of Line)
        checkSideMoves();

        if(endOfLine) {
                colorBoard();
                clearLine();
                myPanel.makeLego();
            }
        endOfMove = true;
        positionOfX =0;
    }

    public void checkSideMoves(){
        if (StartPX + positionOfX + coords[0].length <= 10 && StartPX + positionOfX >= 0) {
            for (int row = 0; row < coords.length; row++ ){
                for (int col = 0; col < coords[row].length; col++) {
                    if(coords[row][col] !=0){
                        if (myPanel.getMypanel()[StartPY +row][StartPX + col + positionOfX] !=0){
                            endOfMove = false;
                        }}}}
            if(endOfMove) {
                StartPX += positionOfX;
            }}}

    public void checkEndOfLine() {
        if (StartPY + 1 + coords.length <= 20) {
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    if (coords[row][col] != 0) {
                        if (myPanel.getMypanel()[StartPY + row + 1][StartPX + col] != 0) {
                            endOfLine = true;
                        }}}}
            if (time > currentSpeed){
                    StartPY++;
                    time = 0;
            }}
        else {
            endOfLine = true;
        }}

    public void colorBoard (){
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[row].length; col++) {
                if (coords[row][col] != 0) {
                    myPanel.getMypanel()[StartPY + row][StartPX + col] = color;
                }}}}

    public void render (Graphics g){
        for(int row = 0; row < coords.length; row++){
            for(int col = 0; col <coords[row].length; col++){
                if(coords[row][col] != 0) {
                    g.drawImage(box, col * myPanel.getBlockSize() + StartPX * myPanel.getBlockSize(),
                            row * myPanel.getBlockSize() + StartPY * myPanel.getBlockSize(), null);
                }}}}

    private void clearLine(){
        int checkHeight = myPanel.getMypanel().length - 1;
        for(int i = checkHeight; i > 0; i--){
            int count = 0;
            for(int j = 0; j < myPanel.getMypanel()[0].length ; j++){
                if(myPanel.getMypanel()[i][j] != 0) {
                    count++;
                }
                myPanel.getMypanel()[checkHeight][j] = myPanel.getMypanel()[i][j];
            }
            if(count < myPanel.getMypanel()[0].length){
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
        if (StartPX + clearlego[0].length > 10 || StartPX + clearlego[0].length < 0 || StartPY + clearlego.length > 20) {
                return;
        }
        for(int row = 0; row < clearlego.length; row++){
            for(int col = 0; col<clearlego[0].length; col++){
                if(myPanel.getMypanel()[StartPY +row][StartPX +col] !=0){
                    return;
                }
            }
        }
        coords = clearlego;
    }

    public void setPositionOfX(int positionOfX){
        this.positionOfX = positionOfX;
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


