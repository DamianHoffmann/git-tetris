package d.main;

import javax.swing.JFrame;

public class Window {
    public static final int WIDTH = 315, HEIGHT =637;
    private JFrame window;
    private Mypanel mypanel;

    public Window(){
    window = new JFrame("TETRIS");
    window.setSize(WIDTH, HEIGHT);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setLocationRelativeTo(null);
    mypanel = new Mypanel();
    window.add(mypanel);
    window.addKeyListener(mypanel);
    window.setVisible(true);
     }

    public static void main(String[] args) {
	new Window();
    }}
