import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickLayout {

    private ArrayList<Brick> bricks;
    private int[][] brickLayout;
    private int cols;

    public BrickLayout(String fileName, int cols, boolean dropAllBricks) {
        this.cols = cols;
        ArrayList<String> fileData = getFileData(fileName);
        bricks = new ArrayList<Brick>();
        for (String line : fileData) {
            String[] points = line.split(",");
            int start = Integer.parseInt(points[0]);
            int end = Integer.parseInt(points[1]);
            Brick b = new Brick(start, end);
            bricks.add(b);
        }
        brickLayout = new int[30][cols];
        if (dropAllBricks) {
            while (bricks.size() != 0) {
                doOneBrick();
            }
        }
    }

    public int[][] getBrickLayout() {
        return brickLayout;
    }

    public boolean dropOneBrick() {
        boolean ifChanged = false;


        if (!bricks.isEmpty()) {
            Brick b = bricks.remove(0);
            b.setHeight(0);
            fallingBricks.add(b);
            ifChanged = true;
        }


        ArrayList<Brick> landed = new ArrayList<>();
        for (Brick b : fallingBricks) {
            int newRow = b.getHeight() + 1;
            if (canMoveDown(b, newRow)) {
                b.setHeight(newRow);
                ifChanged = true;
            } else {
                placeBrick(b);
                landed.add(b);
            }
        }

        fallingBricks.removeAll(landed);
        return ifChanged;
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
        int[][] graph = new int[brickLayout.length][brickLayout[0].length];

        for (int i = 0; i < brickLayout.length; i++) {
            for (int j = 0; j < brickLayout[0].length; j++) {
                graph[i][j] = brickLayout[i][j];
            }
        }

        for (Brick b : fallingBricks) {
            int row = b.getHeight();
            for (int i = b.getStart(); i <= b.getEnd(); i++) {
                if (row >= 0 && row < brickLayout.length) {
                    graph[row][i] = 1;
                }
            }
        }

        return graph;
    }

    public ArrayList<String> getFileData(String fileName) {
        File f = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }
        ArrayList<String> fileData = new ArrayList<String>();
        while (s.hasNextLine())
            fileData.add(s.nextLine());

        return fileData;
    }
}
