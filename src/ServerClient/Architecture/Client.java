package ServerClient.Architecture;

import ServerClient.Messages.Message;
import ServerClient.Messages.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client(String ip, int port) throws IOException, ClassNotFoundException {
        new ClientThread(ip, port, 1).start();
        new ClientThread(ip, port, 2).start();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException { new Client("127.0.0.1", 4000); }

    public class ClientThread extends Thread {

        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        String ip;
        int port, type;

        public ClientThread(String ip, int port, int type) {
            this.ip = ip;
            this.port = port;
            this.type = type;
        }

        /** Game handler.
         * Upon receiving a token message, the client will respond with a move then go back to sleep till another
         * token arrives.
         * When the info received by the message is one, it means there's a winner, so the client evaluates it.*/
        public void run() {
            try {
                clientSocket = new Socket(ip, port);

                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                Scanner input = new Scanner(System.in);

                Message message = (Message) in.readObject();

                while(message.isGame()) {

                    if (message.getDescription() != null) { System.out.println("\n" + message.getDescription()); }

                    if (message.getIdThread() == 1) { System.out.println("\nIt's your turn to play X.\nHere's your board:"); }
                    else { System.out.println("\nIt's your turn to play O.\nHere's your board:"); }

                    System.out.println(message);

                    int play = input.nextInt();

                    Move move = getMove(play);

                    out.writeObject(move);
                    out.flush();

                    message = (Message) in.readObject();
                }

                in.close(); out.close(); clientSocket.close();

                System.exit(1);
            }
            catch (IOException | ClassNotFoundException err) { err.printStackTrace(); }
        }

        public Move getMove(int play) {
            switch (play) {
                case 1: { return new Move(0, 0); }
                case 2: { return new Move(0, 1); }
                case 3: { return new Move(0, 2); }
                case 4: { return new Move(1, 0); }
                case 5: { return new Move(1, 1); }
                case 6: { return new Move(1, 2); }
                case 7: { return new Move(2, 0); }
                case 8: { return new Move(2, 1); }
                case 9: { return new Move(2, 2); }
                default: { return null; }
            }
        }
    }
}
