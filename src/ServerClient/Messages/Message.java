package ServerClient.Messages;

import java.io.Serializable;

public class Message implements Serializable {

    public boolean game;
    public String winner;
    public String[][] matrix;
    public String description;
    public int idThread;

    /** Message constructor, having two types, one that contains winner and one that doesn't.
     * In client, if he receives info == 1, it means the winner has been elected.
     * @param game: ongoing game, true or false.
     * @param matrix: the matrix to play in.*/
    public Message (boolean game, String[][] matrix, int idThread) {
        this.game = game;
        this.matrix = matrix;
        this.idThread = idThread;
    }

    public Message (boolean game, String[][] matrix, int idThread, String description) {
        this.game = game;
        this.matrix = matrix;
        this.idThread = idThread;
        this.description = description;
    }

    public Message (boolean game, String winner) {
        this.game = game;
        this.winner = winner;
    }

    public boolean isGame() { return game; }

    public String getWinner() { return winner; }

    public String[][] getMatrix() { return matrix; }

    public int getIdThread() { return idThread; }

    public String getDescription() { return description; }

    @Override
    public String toString() {
        for (int i = 0; i < 3; i++) {
            for (int a = 0; a < 3; a++) {
                if (matrix[i][a].equals("")) { System.out.print("Z" + " "); }
                else { System.out.print(matrix[i][a] + " "); }
            }
            System.out.println();
        }
        System.out.println();

        return "";
    }
}