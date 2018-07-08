package d.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class MyPanel extends JPanel implements KeyListener {

    private BufferedImage box;
    private final int mypanelHeight = 20, mypanelWidth = 10;
    private final int boxSize = 30;
    private int[][] mypanel = new int[mypanelHeight][mypanelWidth];
    private boolean gameOver = false;
    //shapes
    private Lego[] legos = new Lego[shapes.size()];
    private Lego currentLego;
    //timer
    private Timer timer;
    private final int FPS = 60;
    private final int delay = 1000/FPS;

    private static java.util.List<int[][]> shapes = Arrays.asList(new int[][]{{1,1,1}, {1,0,0}}, new int[][]{{1,1,1,1}}, new int[][]{{1,1,1}, {0,0,1}}, new int[][]{{1,1,1}, {0,1,0}},
            new int[][]{{0,1,1}, {1,1,0}}, new int[][]{{1,1,0}, {0,1,1}}, new int[][]{{1,1}, {1,1}} );

    public MyPanel() {
        try {
            box = ImageIO.read(MyPanel.class.getResource("color.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }});
        timer.start();
        makeLego();
    }

    public void update(){
        currentLego.update();
        if(gameOver){
            timer.stop();
        }
    }

    public Lego createLego(int x){
        return new Lego(box.getSubimage(boxSize*x,0,boxSize, boxSize), shapes.get(x), this, x+1 );
    }

    public void makeLego(){
        int drawLots = (int)(Math.random()*shapes.size());
        currentLego = createLego(drawLots);
        checkGameOver();
        }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentLego.render(g);
        for (int row = 0; row < mypanel.length; row++ ){
            for (int col = 0; col < mypanel[row].length; col++){
                if(mypanel[row][col] != 0)
                    g.drawImage(box.getSubimage((mypanel[row][col]-1)*boxSize,0, boxSize, boxSize),col*boxSize, row*boxSize, null);
            }
        }
        // drawing lines on board
        for (int i = 0; i < mypanelHeight; i++) {
            g.drawLine(0, i * boxSize, boxSize * mypanelHeight, i * boxSize);
        }
        for (int j = 0; j < mypanelWidth; j++) {
            g.drawLine(j * boxSize, 0, j * boxSize, boxSize * mypanelHeight);
        }
    }

    public int getBlockSize(){
        return boxSize;
    }

    public int[][] getMypanel() {
        return mypanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            currentLego.setPositionOfX(-1);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            currentLego.setPositionOfX(1);
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            currentLego.setSpeedDown();
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            currentLego.rotateLego();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            currentLego.setSpeedNormal();
    }

    public void checkGameOver(){
        for (int row = 0; row < currentLego.getCoords().length; row++ )
            for (int col = 0; col < currentLego.getCoords()[row].length; col++) {
                if (currentLego.getCoords()[row][col] != 0) {
                    // columns + starting shape points
                    if (mypanel[row][col + 3] != 0) {
                        gameOver = true;
                    }
                }
            }
    }
}