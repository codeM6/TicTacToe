package P2P;

import P2P.Communication.Move;

public class GameManager {

    /** This method checks if someone won the game.
     * @param matrix: the matrix to evaluate if someone has won.
     * @return true if someone won, otherwise false. */
    public boolean isGoal(String[][] matrix) {

        if (
                matrix[0][0].equals("X") && matrix[0][1].equals("X") && matrix[0][2].equals("X")
                        || matrix[1][0].equals("X") && matrix[1][1].equals("X") && matrix[1][2].equals("X")
                        || matrix[2][0].equals("X") && matrix[2][1].equals("X") && matrix[2][2].equals("X")

                        || matrix[0][0].equals("X") && matrix[1][0].equals("X") && matrix[2][0].equals("X")
                        || matrix[0][1].equals("X") && matrix[1][1].equals("X") && matrix[2][1].equals("X")
                        || matrix[0][2].equals("X") && matrix[1][2].equals("X") && matrix[2][2].equals("X")

                        || matrix[0][0].equals("X") && matrix[1][1].equals("X") && matrix[2][2].equals("X")
        ) return true;

        if (
                matrix[0][0].equals("O") && matrix[0][1].equals("O") && matrix[0][2].equals("O")
                        || matrix[1][0].equals("O") && matrix[1][1].equals("O") && matrix[1][2].equals("O")
                        || matrix[2][0].equals("O") && matrix[2][1].equals("O") && matrix[2][2].equals("O")

                        || matrix[0][0].equals("O") && matrix[1][0].equals("O") && matrix[2][0].equals("O")
                        || matrix[0][1].equals("O") && matrix[1][1].equals("O") && matrix[2][1].equals("O")
                        || matrix[0][2].equals("O") && matrix[1][2].equals("O") && matrix[2][2].equals("O")

                        || matrix[0][0].equals("O") && matrix[1][1].equals("O") && matrix[2][2].equals("O")
        ) return true;

        return false;
    }

    /** Adds a move to a matrix.
     * @param matrix: the matrix to add to.
     * @param move: Move object, contains the move row and column.
     * @see Move
     * @param whoCalledThis: "X" or "O".
     * @return the matrix with the new move.*/
    public String[][] addMoveToMatrix(String[][] matrix, Move move, String whoCalledThis) {

        if (whoCalledThis.equals("X")) {
            if (matrix[move.getRow()][move.getCol()].equals("")) {
                matrix[move.getRow()][move.getCol()] = "X";
                return matrix;
            }
        }

        if (whoCalledThis.equals("O")) {
            if (matrix[move.getRow()][move.getCol()].equals("")) {
                matrix[move.getRow()][move.getCol()] = "O";
                return matrix;
            }
        }

        return null;
    }

    /** Parses a play into a move object.
     * @param play: int given by the player.
     * @return a Move based on the play. */
    public Move parsePlayToMove(int play) {
        switch (play) {
            case 1 -> { return new Move(0, 0); }
            case 2 -> { return new Move(0, 1); }
            case 3 -> { return new Move(0, 2); }
            case 4 -> { return new Move(1, 0); }
            case 5 -> { return new Move(1, 1); }
            case 6 -> { return new Move(1, 2); }
            case 7 -> { return new Move(2, 0); }
            case 8 -> { return new Move(2, 1); }
            case 9 -> { return new Move(2, 2); }
            default -> { return null; }
        }
    }

    /** Evaluates if the move is valid or not. Checks for existence of a play in a position.
     * @param move: Move to evaluate.
     * @param matrix: Matrix to evaluate the move in.
     * @return true or false. */
    public boolean validateMove(Move move, String[][] matrix) {
        return matrix[move.getRow()][move.getCol()].equals("");
    }

    public String evaluateWinner(String[][] matrix) {
        if (
                matrix[0][0].equals("X") && matrix[0][1].equals("X") && matrix[0][2].equals("X")
                        || matrix[1][0].equals("X") && matrix[1][1].equals("X") && matrix[1][2].equals("X")
                        || matrix[2][0].equals("X") && matrix[2][1].equals("X") && matrix[2][2].equals("X")

                        || matrix[0][0].equals("X") && matrix[1][0].equals("X") && matrix[2][0].equals("X")
                        || matrix[0][1].equals("X") && matrix[1][1].equals("X") && matrix[2][1].equals("X")
                        || matrix[0][2].equals("X") && matrix[1][2].equals("X") && matrix[2][2].equals("X")

                        || matrix[0][0].equals("X") && matrix[1][1].equals("X") && matrix[2][2].equals("X")
        ) return "X";

        if (
                matrix[0][0].equals("O") && matrix[0][1].equals("O") && matrix[0][2].equals("O")
                        || matrix[1][0].equals("O") && matrix[1][1].equals("O") && matrix[1][2].equals("O")
                        || matrix[2][0].equals("O") && matrix[2][1].equals("O") && matrix[2][2].equals("O")

                        || matrix[0][0].equals("O") && matrix[1][0].equals("O") && matrix[2][0].equals("O")
                        || matrix[0][1].equals("O") && matrix[1][1].equals("O") && matrix[2][1].equals("O")
                        || matrix[0][2].equals("O") && matrix[1][2].equals("O") && matrix[2][2].equals("O")

                        || matrix[0][0].equals("O") && matrix[1][1].equals("O") && matrix[2][2].equals("O")
        ) return "O";

        return "draw";
    }
}
