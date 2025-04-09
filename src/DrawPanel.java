import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.JPanel;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener{
    private boolean[][] grid;
    private BrickLayout input;
    long startTime = System.currentTimeMillis();
    long interval = 50;
    long currentTime;


    public DrawPanel() {
        this.addMouseListener(this);
        grid = new boolean[30][40];
        set2dGrid();
        input = new BrickLayout("src/bricks",40,false);
    }

    public void set2dGrid(){
        grid = new boolean[30][40];
        for (int i = 0;i< 0.3 * (grid.length * grid[0].length);i++){
            grid[(int)(Math.random() * 30)][(int)(Math.random() * 40)] = true;
        }
    }

    public void drawBrick() {
        input.doOneBrick();
        for (int i = 0; i<input.getBrickLayout().length;i++){
            for (int j = 0; j<input.getBrickLayout()[0].length;j++){
                if (input.getBrickLayout()[i][j] == 1){
                    grid[i][j] = true;
                }
            }
        }
    }

    public void fallBrick() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long interval = 50;
        long currentTime;
        long check = 100;

        while (true) {
            currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= interval) {
                input.doOneBrick();
                startTime = currentTime;
            }
            Thread.sleep(check);
        }
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int num = 10;
        int numy = 10;


        Graphics2D g2 = (Graphics2D) g;

        for (int j = 0; j<30;j++) {
            for (int i = 0; i < 40; i++) {
                g.drawRect(num, numy, 20, 20);
                if (grid[j][i] == true){
                    g2.setColor(Color.RED);
                    g2.fillRect(num,numy,20,20);
                    g2.setColor(Color.BLACK);
                }
                num = num + 25;
            }
            numy = numy +25;
            num = 10;
        }
    }

    public void mouseClicked(MouseEvent e) {
        grid = new boolean[30][40];
        try {
            fallBrick();
        } catch (InterruptedException ex) {
            System.out.println("dont work");;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}