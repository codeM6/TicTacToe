package P2P;

import P2P.Communication.Message;
import P2P.Communication.Move;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static String[][] matrix = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""}
    };

    public Client(String ip, int port) throws Exception {
        clientSocket = new Socket(ip, port);

        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        Scanner scanner = new Scanner(System.in);

        GameManager manager = new GameManager();

        /* Prior to the game stuff. */
        Message message = (Message) in.readObject();
        System.out.println("You received an enemy message!\n" + message.getMessage());

        int occupiedPositions = 0;

        /* In-game stuff. */
        while (!manager.isGoal(matrix) && occupiedPositions < 9) {

            System.out.println("\nAlright here's the board, what are you gonna do?\n");

            for (int i = 0; i < 3; i++) {
                for (int a = 0; a < 3; a++) {
                    if (matrix[i][a].equals("")) { System.out.print("N "); }
                    else { System.out.print(matrix[i][a] + " "); }
                }
                System.out.println();
            }

            System.out.print("\nYour play: ");

            int play = scanner.nextInt();

            Move move = manager.parsePlayToMove(play);

            boolean validMove = manager.validateMove(move, matrix);

            do {

                System.out.println("\nLet's just see if everything is fine with your move..");
                Thread.sleep(1000);
                System.out.println("\n...");
                Thread.sleep(1000);
                System.out.println("...");
                Thread.sleep(1000);
                System.out.println("...\n");

                if (!validMove) {
                    System.out.println("Ehh, cheeky move you tried there. You can't do that here buddy.\nTry again:");
                    play = scanner.nextInt();
                    move = manager.parsePlayToMove(play);
                    validMove = manager.validateMove(move, matrix);
                }

            } while (!validMove);

            System.out.println("That's a good move, I'm sure your enemy will be surprised.");

            manager.addMoveToMatrix(matrix, move, "O");
            occupiedPositions++;

            System.out.println("Sending the move to your enemy..");
            System.out.println("Sent!");
            out.writeObject(move);
            out.flush();

            if (manager.isGoal(matrix)) continue;

            Move enemyMove = (Move) in.readObject();

            manager.addMoveToMatrix(matrix, enemyMove, "X");
            occupiedPositions++;
        }

        System.out.println("Oh! The game's already over, who would say?");

        String winner = manager.evaluateWinner(matrix);

        switch(winner) {
            case "X" -> System.out.println("X won the game. That's unlucky. Maybe try next time?");
            case "O" -> System.out.println("O won the game. That's you! That's you!\nGood job man! Hope you had fun.");
            case "draw" -> System.out.println("You both aren't that good are you? Oh god..");
            default -> System.out.println("Error. Something went wrong!");
        }

        Thread.sleep(3000);

        // in.close(); out.close(); clientSocket.close();
        System.exit(1);
    }

    public static void main(String[] args) throws Exception { new Client("127.0.0.1", 4000); }
}
