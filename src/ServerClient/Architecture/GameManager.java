package ServerClient.Architecture;

public class GameManager {

    /** Checks if game is over due to:
     *      - Someone winning.
     *      - Game reaching the draw state.
     * @param matrix: matrix to evaluate.
     * @return String containing winner X, winner O or draw. */
    public String goal(String[][] matrix) {
        if (
                matrix[0][0].equals("X") && matrix[0][1].equals("X") && matrix[0][2].equals("X")
                || matrix[1][0].equals("X") && matrix[1][1].equals("X") && matrix[1][2].equals("X")
                || matrix[2][0].equals("X") && matrix[2][1].equals("X") && matrix[2][2].equals("X")

                || matrix[0][0].equals("X") && matrix[1][0].equals("X") && matrix[2][0].equals("X")
                || matrix[0][1].equals("X") && matrix[1][1].equals("X") && matrix[2][1].equals("X")
                || matrix[0][2].equals("X") && matrix[1][2].equals("X") && matrix[2][2].equals("X")

                || matrix[0][0].equals("X") && matrix[1][1].equals("X") && matrix[2][2].equals("X")
        ) return ("X");

        if (
                matrix[0][0].equals("O") && matrix[0][1].equals("O") && matrix[0][2].equals("O")
                || matrix[1][0].equals("O") && matrix[1][1].equals("O") && matrix[1][2].equals("O")
                || matrix[2][0].equals("O") && matrix[2][1].equals("O") && matrix[2][2].equals("O")

                || matrix[0][0].equals("O") && matrix[1][0].equals("O") && matrix[2][0].equals("O")
                || matrix[0][1].equals("O") && matrix[1][1].equals("O") && matrix[2][1].equals("O")
                || matrix[0][2].equals("O") && matrix[1][2].equals("O") && matrix[2][2].equals("O")

                || matrix[0][0].equals("O") && matrix[1][1].equals("O") && matrix[2][2].equals("O")
        ) return ("O");

        if (
                matrix[0][0].length() + matrix[0][1].length() + matrix[0][2].length() +
                matrix[1][0].length() + matrix[1][1].length() + matrix[1][2].length() +
                matrix[2][0].length() + matrix[2][1].length() + matrix[2][2].length()
                        == 9
        ) return ("draw");

        return null;
    }

    /** Verifies if the move made by the client is valid, by checking if the col/row was empty.
     * @param x: row in the matrix.
     * @param y: col in the matrix.
     * @param matrix: the matrix.
     * @return false or true. */
    public boolean verifyMove(int x, int y, String[][] matrix) {
        return matrix[x][y].equals("");
    }

    /** Depending on the clients id, adds X or Y to the table at the given position.
     * @param x: Row position at table.
     * @param y: Col position at table.
     * @param matrix: Matrix to add value to.
     * @param id: id of the client.
     * @return the updated matrix. */
    public String[][] addMove(int x, int y, String[][] matrix, int id) {
        if (id == 1) { matrix[x][y] = "X"; }
        else { matrix[x][y] = "O"; }
        return matrix;
    }
}