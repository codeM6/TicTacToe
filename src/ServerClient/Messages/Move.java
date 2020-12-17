package ServerClient.Messages;

import java.io.Serializable;

public class Move implements Serializable {

    public int moveX;
    public int moveY;

    public Move(int moveX, int moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public int getMoveX() { return moveX; }
    public int getMoveY() { return moveY; }
}
