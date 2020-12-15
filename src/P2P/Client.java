package P2P;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream input;

    public Client(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());

        Message obj = new Message(0, "Bernardo Amorim");

        out.writeObject(obj);
        out.flush();

        try {
            if (input.readObject() instanceof Message) {
                Message msg = (Message) input.readObject();
                System.out.println(obj.getDescr());
            }
        }
        catch (Exception err) { err.printStackTrace(); }

        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException { new Client("127.0.0.1", 4000); }
}
