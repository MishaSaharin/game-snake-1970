package game.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    private int score;
    private static final int GOAL = 28;
    private boolean isGameStopped;
    private Apple apple;
    public static final int HEIGHT = 15;
    public static final int WIDTH = 15;
    private int turnDelay;
    private Snake snake;

    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        turnDelay = 300;
        score = 0;
        setScore(score);
        createNewApple();
        isGameStopped = false;
        drawScene();
        setTurnTimer(turnDelay);

    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.YELLOW, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    public void onTurn(int step) {
        if (apple.isAlive == false) {
            score = score + 5;
            setScore(score);
            turnDelay = turnDelay - 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        snake.move(apple);
        if (snake.isAlive == false) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    public void setTurnTimer(int timeMs) {
        super.setTurnTimer(timeMs);
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (key == Key.SPACE && isGameStopped == true) {
            createGame();
        }
    }

    private void createNewApple() {
        apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        while (snake.checkCollision(apple)) {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "GAME OVER", Color.BLUE, 55);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "YOU WIN", Color.BLUE, 55);
    }
}
