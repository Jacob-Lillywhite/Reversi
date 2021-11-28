import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class GamePanel extends JPanel {
    //region Game Variables
        //region UI Variables
        static final int SCREEN_WIDTH =  600;
        static final int SCREEN_HEIGHT = 600;
        static final int UNIT_SIZE = 600/8;    // This determines how big everything is.
        Color boardColor = new Color(39, 119, 20);
        int clickX;
        int clickY;
        int playersTurn = 1;
        int[][] boardMatrix = { {0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 1, 2, 0, 0, 0},
                                {0, 0, 0, 2, 1, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0}};
        //endregion
        //region Snake Variables
        // These arrays hold the x and y values for the snake.
        //endregion
        //region Goal Variables

        //region Game State Variables
        boolean running = false;
        //endregion
    //endregion
    //region GamePanel
    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(boardColor);
        this.setFocusable(true);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // IF the space is empty.
                // AND
                // IF there's a valid move
                if(boardMatrix[e.getX()/UNIT_SIZE][e.getY()/UNIT_SIZE] == 0) {
                    boardMatrix[e.getX() / UNIT_SIZE][e.getY() / UNIT_SIZE] = playersTurn;
                    if(!flipCheck(e.getX()/UNIT_SIZE, e.getY()/UNIT_SIZE)){
                        boardMatrix[e.getX() / UNIT_SIZE][e.getY() / UNIT_SIZE] = 0;
                    }else{
                        if (playersTurn == 1) {
                            playersTurn = 2;
                        } else {
                            playersTurn = 1;
                        }
                    }
                }
                draw(getGraphics());
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
        });
        startGame();
    }
    //endregion
    //region Game State Methods
    public void startGame(){
        running = true;
    }
    //endregion
    //region Game Creation Methods
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        if (running) {
            for (int i = 0; i < 8; i++) {
                graphics.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                graphics.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            for (int i = 0; i < boardMatrix.length; i++) {
                for (int j = 0; j < boardMatrix[0].length; j++) {
                    switch (boardMatrix[i][j]) {
                        case 0:
                            graphics.setColor(boardColor);
                            graphics.fillArc(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, 0, 360);
                            break;
                        case 1:
                            graphics.setColor(Color.black);
                            graphics.fillArc(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, 0, 360);
                            break;
                        case 2:
                            graphics.setColor(Color.white);
                            graphics.fillArc(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, 0, 360);
                            break;
                    }
                }
            }

        }
    }

    public boolean flipCheck(int x, int y){
        // Check clockwise starting at left to check the 8 directions around a piece
        Stack<Integer> xStack = new Stack<>();
        Stack<Integer> yStack = new Stack<>();
        boolean isValidMove = false;

        //LEFT CHECK
        xStack.clear();
        yStack.clear();
        if(x > 0) {
            if(checkLeft(x, y, xStack)){
                if(!xStack.empty() || !yStack.empty()){
                    isValidMove = true;
                }
                    while (!xStack.empty()) {
                        boardMatrix[xStack.pop()][y] = playersTurn;
                    }
            }
        }

        // DIAGONAL TOP LEFT CHECK
        xStack.clear();
        yStack.clear();
        if(x > 0 && y > 0) {
            if(checkTopLeft(x, y, xStack, yStack)){
                if(!xStack.empty() || !yStack.empty()){
                    isValidMove = true;
                }
                    while (!(xStack.empty() && yStack.empty())) {
                        boardMatrix[xStack.pop()][yStack.pop()] = playersTurn;
                    }
            }
        }

        // TOP CHECK
        xStack.clear();
        yStack.clear();
        if(y > 0) {
            if(checkTop(x, y, yStack)){
                if(!xStack.empty() || !yStack.empty()){
                    isValidMove = true;
                }
                    while (!yStack.empty()) {
                        boardMatrix[x][yStack.pop()] = playersTurn;
                    }
            }
        }

        // DIAGONAL TOP RIGHT CHECK
        xStack.clear();
        yStack.clear();
        if(x < 7 && y > 0) {
            if(checkTopRight(x, y, xStack, yStack)){
                if(!xStack.empty() || !yStack.empty()){
                    isValidMove = true;
                }
                    while (!(xStack.empty() && yStack.empty())) {
                        boardMatrix[xStack.pop()][yStack.pop()] = playersTurn;
                    }
            }
        }

        // RIGHT CHECK
        xStack.clear();
        yStack.clear();
        if(x < 7) {
            if(checkRight(x, y, xStack)){
                if(!xStack.empty() || !yStack.empty()){
                    isValidMove = true;
                }
                    while (!xStack.empty()) {
                        boardMatrix[xStack.pop()][y] = playersTurn;
                    }
            }
        }

        // DIAGONAL BOTTOM RIGHT CHECK
        xStack.clear();
        yStack.clear();
        if(x < 7 && y < 7) {
            if(checkBottomRight(x, y, xStack, yStack)){
                if(!xStack.empty() || !yStack.empty()){
                    isValidMove = true;
                }
                    while (!(xStack.empty() && yStack.empty())) {
                        boardMatrix[xStack.pop()][yStack.pop()] = playersTurn;
                    }
            }
        }

        // BOTTOM CHECK
        xStack.clear();
        yStack.clear();
        if(y < 7) {
            if(checkBottom(x, y, yStack)){
                if(!xStack.empty() || !yStack.empty()){
                    isValidMove = true;
                }
                    while (!yStack.empty()) {
                        boardMatrix[x][yStack.pop()] = playersTurn;
                    }
            }
        }

        // DIAGONAL BOTTOM LEFT CHECK
        xStack.clear();
        yStack.clear();
        if(x > 0 && y < 7) {
            if(checkBottomLeft(x, y, xStack, yStack)){
                if(!xStack.empty() || !yStack.empty()){
                    isValidMove = true;
                }
                while (!(xStack.empty() && yStack.empty())) {
                    boardMatrix[xStack.pop()][yStack.pop()] = playersTurn;
                }
            }
        }

        // Lastly update the graphics.
        return isValidMove;
    }

    private boolean checkLeft(int x, int y, Stack<Integer> xStack){
        boolean flip = false;
        for (int i = x-1; i >= 0; i--){
            if(boardMatrix[i][y] != boardMatrix[x][y] && boardMatrix[i][y] != 0){
                xStack.push(i);
            }
            if(boardMatrix[i][y] == 0){
                break;
            }
            if(boardMatrix[i][y] == boardMatrix[x][y]){
                flip = true;
                break;
            }
        }
        return flip;
    }

    private boolean checkTopLeft(int x, int y, Stack<Integer> xStack, Stack<Integer> yStack){
        boolean flip = false;
        int j = y-1;
        for (int i = x-1; i >= 0; i--){
            if (boardMatrix[i][j] != boardMatrix[x][y] && boardMatrix[i][j] != 0) {
                xStack.push(i);
                yStack.push(j);
            }
            if(boardMatrix[i][j] == 0){
                break;
            }
            if(boardMatrix[i][j] == boardMatrix[x][y]){
                flip = true;
                break;
            }
            if(j>0) {
                j--;
            }

        }
        return flip;
    }

    private boolean checkTop(int x, int y, Stack<Integer> yStack){
        boolean flip = false;
        for (int i = y - 1; i >= 0; i--){
            if(boardMatrix[x][i] != boardMatrix[x][y] && boardMatrix[x][i] != 0){
                yStack.push(i);
            }
            if(boardMatrix[x][i] == 0){
                break;
            }
            if(boardMatrix[x][i] == boardMatrix[x][y]){
                flip = true;
                break;
            }
        }
        return flip;
    }

    private boolean checkTopRight(int x, int y, Stack<Integer> xStack, Stack<Integer> yStack){
        boolean flip = false;
        int j = y - 1;
        for (int i = x + 1; i <= 7; i++){

            if (boardMatrix[i][j] != boardMatrix[x][y] && boardMatrix[i][j] != 0) {
                xStack.push(i);
                yStack.push(j);
            }
            if(boardMatrix[i][j] == 0){
                break;
            }
            if(boardMatrix[i][j] == boardMatrix[x][y]){
                flip = true;
                break;
            }
            if(j>0) {
                j--;
            }

        }
        return flip;
    }

    private boolean checkRight(int x, int y, Stack<Integer> xStack){
        boolean flip = false;
        for (int i = x + 1; i <= 7; i++){
            if(boardMatrix[i][y] != boardMatrix[x][y] && boardMatrix[i][y] != 0){
                xStack.push(i);
            }
            if(boardMatrix[i][y] == 0){
                break;
            }
            if(boardMatrix[i][y] == boardMatrix[x][y]){
                flip = true;
                break;
            }
        }
        return flip;
    }

    private boolean checkBottomRight(int x, int y, Stack<Integer> xStack, Stack<Integer> yStack){
        boolean flip = false;
        int j = y + 1;
        for (int i = x + 1; i <= 7; i++){

            if (boardMatrix[i][j] != boardMatrix[x][y] && boardMatrix[i][j] != 0) {
                xStack.push(i);
                yStack.push(j);
            }
            if(boardMatrix[i][j] == 0){
                break;
            }
            if(boardMatrix[i][j] == boardMatrix[x][y]){
                flip = true;
                break;
            }
            if(j<7) {
                j++;
            }

        }
        return flip;
    }

    private boolean checkBottom(int x, int y, Stack<Integer> yStack){
        boolean flip = false;
        for (int i = y + 1; i <= 7 ; i++){
            if(boardMatrix[x][i] != boardMatrix[x][y] && boardMatrix[x][i] != 0){
                yStack.push(i);
            }
            if(boardMatrix[x][i] == 0){
                break;
            }
            if(boardMatrix[x][i] == boardMatrix[x][y]){
                flip = true;
                break;
            }
        }
        return flip;
    }

    private boolean checkBottomLeft(int x, int y, Stack<Integer> xStack, Stack<Integer> yStack){
        boolean flip = false;
        int j = y + 1;
        for (int i = x - 1; i >= 0; i--){

            if (boardMatrix[i][j] != boardMatrix[x][y] && boardMatrix[i][j] != 0) {
                xStack.push(i);
                yStack.push(j);
            }
            if(boardMatrix[i][j] == 0){
                break;
            }
            if(boardMatrix[i][j] == boardMatrix[x][y]){
                flip = true;
                break;
            }
            if(j<7) {
                j++;
            }
        }
        return flip;
    }
}
