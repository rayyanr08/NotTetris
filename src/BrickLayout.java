import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickLayout {

    private ArrayList<Brick> bricks;
    private ArrayList<Brick> fallingBricks;
    private int[][] brickLayout;
    private int cols;

    public BrickLayout(String fileName, int cols, boolean dropAllBricks) {
        this.cols = cols;
        bricks = new ArrayList<>();
        fallingBricks = new ArrayList<>();
        brickLayout = new int[30][cols];

        ArrayList<String> fileData = getFileData(fileName);
        for (String line : fileData) {
            String[] points = line.split(",");
            int start = Integer.parseInt(points[0]);
            int end = Integer.parseInt(points[1]);
            bricks.add(new Brick(start, end));
        }
    }

    public boolean dropOneBrick() {
        boolean changed = false;


        if (!bricks.isEmpty()) {
            Brick b = bricks.remove(0);
            b.setHeight(0);
            fallingBricks.add(b);
            changed = true;
        }


        ArrayList<Brick> landed = new ArrayList<>();
        for (Brick b : fallingBricks) {
            int newRow = b.getHeight() + 1;
            if (canMoveDown(b, newRow)) {
                b.setHeight(newRow);
                changed = true;
            } else {
                placeBrick(b);
                landed.add(b);
            }
        }

        fallingBricks.removeAll(landed);
        return changed;
    }

    private boolean canMoveDown(Brick b, int row) {
        if (row >= brickLayout.length) return false;
        for (int i = b.getStart(); i <= b.getEnd(); i++) {
            if (brickLayout[row][i] == 1) return false;
        }
        return true;
    }

    private void placeBrick(Brick b) {
        int row = b.getHeight();
        for (int i = b.getStart(); i <= b.getEnd(); i++) {
            brickLayout[row][i] = 1;
        }
    }

    public int[][] getBrickLayout() {
        int[][] layoutCopy = new int[brickLayout.length][brickLayout[0].length];

        for (int i = 0; i < brickLayout.length; i++) {
            for (int j = 0; j < brickLayout[0].length; j++) {
                layoutCopy[i][j] = brickLayout[i][j];
            }
        }

        for (Brick b : fallingBricks) {
            int row = b.getHeight();
            for (int i = b.getStart(); i <= b.getEnd(); i++) {
                if (row >= 0 && row < brickLayout.length) {
                    layoutCopy[row][i] = 1;
                }
            }
        }
        return layoutCopy;
    }

    public ArrayList<String> getFileData(String fileName) {
        File f = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }

        ArrayList<String> fileData = new ArrayList<>();
        while (s.hasNextLine())
            fileData.add(s.nextLine());

        return fileData;
    }
}
