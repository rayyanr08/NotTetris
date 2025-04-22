import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener {

    private boolean[][] grid;
    private BrickLayout input;
    private boolean isFallingStarted = false;

    public DrawPanel() {
        this.addMouseListener(this);
        input = new BrickLayout("src/bricks", 40, false);
        grid = new boolean[30][40];
    }

    private void updateGrid() {
        int[][] layout = input.getBrickLayout();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 40; j++) {
                grid[i][j] = layout[i][j] == 1;
            }
        }
    }

    @Override
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

    @Override
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
                        if (!hasMoreFalling) {
                        break;
                        }
                        lastUpdate = currentTime;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
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
