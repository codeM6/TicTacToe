package ServerClient.Architecture;

import ServerClient.Messages.Message;
import ServerClient.Messages.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    private ServerSocket serverSocket;
    private static HashMap<Integer, ServerThread> connections;

    public static final Object singleThreadLock = new Object();
    public static boolean firstMove = true;

    public static String[][] matrix = {
            {"", "", ""},
            {"", "", ""},
            {"", "", ""}
    };

    private int id = 0;

    /** Server constructor.
     * Creates an HashMap that contains the key-pair values to id: Connection.
     * Creates a new connection at port 4000.
     * For each connection creates a new thread and puts the connection in the hashmap.
     * @param port: port to begin the server at.*/
    public Server(int port) throws IOException {
        serverSocket =  new ServerSocket(port);
        connections = new HashMap<>();

        while(true) {
            ServerThread sct = null;
            try {
                sct = new ServerThread(serverSocket.accept(), id++);
                connections.put(id, sct);
            }
            catch (Exception err) { err.printStackTrace(); }
            sct.start();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Game started!\n");
        new Server(4000);
    }

    public class ServerThread extends Thread {

        public ObjectOutputStream out;
        private ObjectInputStream in;
        private Socket clientSocket;
        int id;

        private GameManager manager;

        ServerThread(Socket s, int id) {
            this.clientSocket = s;
            this.id = id;
            manager = new GameManager();
        }

        /** Game handler.
         * Has an output stream and an input stream.
         * The server's working by tokens. Each clients gets a token when it is his turn to play.
         * First Move = sends token to a random connection, so the first player is random.
         * While game isn't over, receives a move, validates it and notifies the enemy player of the move and the
         * current player that the move was valid. */
        public void run() {
            try {
                in = new ObjectInputStream(clientSocket.getInputStream());
                out = new ObjectOutputStream(clientSocket.getOutputStream());

                String goalFlag = null;

                synchronized (singleThreadLock) {
                    if (firstMove) {
                        firstMove = false;
                        System.out.println("There goes the first turn!\n");
                        out.writeObject(new Message(true, matrix, id));
                    }
                }

                while(goalFlag == null) {

                    Move move = (Move) in.readObject();

                    if(!manager.verifyMove(move.getMoveX(), move.getMoveY(), matrix)) {
                        System.out.println("Received invalid move: [" + move.getMoveX() + "][" + move.getMoveY() + "].\n");
                        out.writeObject(new Message(
                                true,
                                matrix,
                                -1,
                                "Your move failed because there's already a play in that position."
                        ));
                        out.flush();
                        continue;
                    }

                    System.out.println("Received move: [" + move.getMoveX() + "][" + move.getMoveY() + "]\n");
                    matrix = manager.addMove(move.getMoveX(), move.getMoveY(), matrix, id);

                    goalFlag = manager.goal(matrix);

                    if (goalFlag != null) { continue; }

                    Message message;

                    if (id == 0) { message = new Message(true, matrix, 1); }
                    else { message = new Message(true, matrix, 0); }

                    System.out.print(message);

                    ServerThread enemyThread = getEnemyThread();
                    enemyThread.writeThroughThread(message);

                }

                String winner = switch (goalFlag) {
                    case "O" -> "Player O won! That was a nice game!";
                    case "X" -> "Player X won! That was a nice game!";
                    case "draw" -> "No one won. This game's not fit to have a winner anyways.";
                    default -> "";
                };

                System.out.println("Game ended.\n" + winner);

                Message gameIsOver = new Message(false, winner);

                out.writeObject(gameIsOver); out.flush();
                getEnemyThread().writeThroughThread(gameIsOver);

                Thread.sleep(100);

                out.close();

                System.exit(1);
            }
            catch (Exception err) { err.printStackTrace(); }
        }

        /** Synchronized with other threads.
         * Given a thread, writes through it.
         * Flushes a message to the client within that thread.
         * @param message: message to send. */
        public synchronized void writeThroughThread(Message message) throws IOException {
            out.writeObject(message); out.flush();
        }

        /** Gets the enemy's thread in the connections hashMap.
         * @return A Thread, or null. */
        public ServerThread getEnemyThread() {
            if (id == 0) return connections.get(2);
            else { return connections.get(1); }
        }
    }
}