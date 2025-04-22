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

    public void updateGrid() {
        int[][] layout = input.getBrickLayout();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 40; j++) {
                grid[i][j] = layout[i][j] == 1;
            }
        }
    }

    
     protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateGrid();

        Graphics2D g2 = (Graphics2D) g;
        int x = 10;
        int y = 10;

        for (int row = 0; row < 30; row++) {
            for (int col = 0; col < 40; col++) {
                g.drawRect(x, y, 20, 20);
                if (grid[row][col]) {
                    g2.setColor(Color.RED);
                    g2.fillRect(x, y, 20, 20);
                    g2.setColor(Color.BLACK);
                }
                x += 25;
            }
            y += 25;
            x = 10;
        }
    }
    public void mouseClicked(MouseEvent e) {
        if (!isFallingStarted && e.getButton() == MouseEvent.BUTTON1) {
            isFallingStarted = true;
            Thread fallingThread = new Thread(() -> {
                long lastUpdate = System.currentTimeMillis();
                while (true) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastUpdate >= 99) {
                        boolean hasMoreFalling = input.dropOneBrick();
                        updateGrid();
                        repaint();
                        if (!hasMoreFalling) break;
                        lastUpdate = currentTime;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException x) {
                        x.printStackTrace();
                    }
                }
            });

            fallingThread.start();
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
