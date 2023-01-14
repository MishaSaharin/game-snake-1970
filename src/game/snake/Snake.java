package game.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private Direction direction = Direction.LEFT;
    public boolean isAlive = true;

    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT) && (snakeParts.get(0).x == snakeParts.get(1).x)) return;
        if ((this.direction == Direction.RIGHT) && (snakeParts.get(0).x == snakeParts.get(1).x)) return;
        if ((this.direction == Direction.UP) && (snakeParts.get(0).y == snakeParts.get(1).y)) return;
        if ((this.direction == Direction.DOWN) && (snakeParts.get(0).y == snakeParts.get(1).y)) return;
        this.direction = direction;
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x < 0 ||
                newHead.x >= SnakeGame.WIDTH ||
                newHead.y < 0 ||
                newHead.y >= SnakeGame.HEIGHT ||
                checkCollision(newHead)) {
            isAlive = false;
        } else {
            if (newHead.x == apple.x && newHead.y == apple.y) {
                apple.isAlive = false;
                snakeParts.add(0, newHead);
            } else {
                snakeParts.add(0, newHead);
                removeTail();
            }
        }
    }

    public Snake(int x, int y) {
        GameObject first = new GameObject(x, y);
        GameObject second = new GameObject(x + 1, y);
        GameObject third = new GameObject(x + 2, y);
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }

    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            if (i == 0 && isAlive) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,
                        Color.NONE, HEAD_SIGN, Color.DARKGREEN, 75);
            }
            if (i == 0 && !isAlive) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,
                        Color.NONE, HEAD_SIGN, Color.RED, 75);
            }
            if (i != 0 && isAlive) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,
                        Color.NONE, BODY_SIGN, Color.DARKGREEN, 75);
            }
            if (i != 0 && !isAlive) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y,
                        Color.NONE, BODY_SIGN, Color.RED, 75);
            }
        }
    }

    public GameObject createNewHead() {
        GameObject snakeHead = null;
        if (direction == Direction.LEFT)
            snakeHead = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
        if (direction == Direction.DOWN)
            snakeHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        if (direction == Direction.RIGHT)
            snakeHead = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        if (direction == Direction.UP)
            snakeHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        return snakeHead;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject object : snakeParts) {
            if (gameObject.x == object.x && gameObject.y == object.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}
