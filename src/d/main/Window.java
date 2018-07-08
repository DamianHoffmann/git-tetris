package d.main;

import javax.swing.JFrame;

public class Window {
    public static final int WIDTH = 315, HEIGHT =637;
    private JFrame window;
    private MyPanel myPanel;

    public Window(){
        window = new JFrame("TETRIS");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        myPanel = new MyPanel();
        window.add(myPanel);
        window.addKeyListener(myPanel);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }}