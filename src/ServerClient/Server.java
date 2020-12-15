package ServerClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** Class to implement Client to Client technology. Since the whole method is dealt by both clients directly
 * the ip is fragile and not hidden. Implements ServerClientThread for the Runnable thread.
 * @see ServerClientThread */
public class Server {

    public ServerSocket serverSocket;
    public int id = 0;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            ServerClientThread sct = null;
            try { sct = new ServerClientThread(serverSocket.accept(), id++); }
            catch (IOException err) { err.printStackTrace(); }
            sct.start();
        }
    }

    public static void main(String[] args) throws Exception { new Server(4000); }

    /** In this type of technology this class has to handle all information and repeatedly send tokens of
     * confirmation to the client so they know what's the opponent's play and when can they play. */
    public class ServerClientThread extends Thread {

        private Socket clientSocket;
        private int id;

        public ServerClientThread(Socket s, int id) {
            this.clientSocket = s;
            this.id = id;
        }

        public void run() {
            try {

            }
            catch (Exception err) { err.printStackTrace(); }
        }
    }
}