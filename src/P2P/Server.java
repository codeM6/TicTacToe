package P2P;

import P2P.Communication.Message;
import P2P.Communication.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private ServerSocket serverSocket;
    private int id = 0;

    public static String[][] matrix = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""}
    };

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while(true) {
            GameThread thread = null;
            try { thread = new GameThread(serverSocket.accept(), id++); }
            catch (Exception err) { err.printStackTrace(); }
            thread.start();
        }
    }

    public static void main(String[] args) throws IOException { new Server(4000); }

    public class GameThread extends Thread {

        private ObjectOutputStream out;
        private ObjectInputStream in;
        private Socket clientSocket;
        int id;

        private GameManager manager;

        GameThread(Socket clientSocket, int id) {
            this.clientSocket = clientSocket;
            this.id = id;
            manager = new GameManager();
        }

        public void run() {
            try {
                in = new ObjectInputStream(clientSocket.getInputStream());
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                Scanner scanner = new Scanner(System.in);

                /* Upon connection cute message. */
                System.out.println("Sending message to enemy..\n\"How about you go first? I'm a gentleman.\"");
                out.writeObject(new Message("How about you go first? I'm a gentleman.\n"));

                int occupiedPositions = 0;

                /* In-Game stuff. */
                while(!manager.isGoal(matrix) && occupiedPositions < 9) {

                    Move enemyMove = (Move) in.readObject();

                    manager.addMoveToMatrix(matrix, enemyMove, "O");
                    occupiedPositions++;

                    if (manager.isGoal(matrix)) continue;

                    System.out.println("Well.. Your opponent just made a fancy move there, what's yours?\n");

                    for (int i = 0; i < 3; i++) {
                        for (int a = 0; a < 3; a++) {
                            if (matrix[i][a].equals("")) { System.out.print("N "); }
                            else { System.out.print(matrix[i][a] + " "); }
                        }
                        System.out.println();
                    }

                    System.out.print("\nYour move: ");

                    int play = scanner.nextInt();

                    Move move = manager.parsePlayToMove(play);

                    boolean validMove = manager.validateMove(move, matrix);

                    do {

                        System.out.println("Let's just see if everything is fine with your move..");
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

                    System.out.println("That's a good move, I'm sure your enemy will be surprised!");

                    manager.addMoveToMatrix(matrix, move, "X");
                    occupiedPositions++;

                    out.writeObject(move);
                    out.flush();
                }

                System.out.println("\nOh! The game's already over, who would say?");

                String winner = manager.evaluateWinner(matrix);

                switch(winner) {
                    case "X" -> System.out.println("X won the game. That's you! That's you!\nGood job! Hope you had fun.");
                    case "O" -> System.out.println("O won the game. That's unlucky. Maybe next time?");
                    case "draw" -> System.out.println("Oh darn.. You both aren't that good are you? Oh god..");
                    default -> System.out.println("Error. Something went wrong!");
                }

                in.close(); out.close(); clientSocket.close();
                System.exit(1);
            }
            catch (Exception err) { err.printStackTrace(); }
        }
    }
}
