package ServerClient;

import java.net.Socket;
import java.util.ArrayList;

public class ServerManager {

    /** Verifies if the client is already inserted in the list and if not inserts it.
     * @param ips: list of the stored ips.
     * @param client: Socket to communicate with.
     * @return the list, altered or not. */
    public ArrayList<String> storeClientIp(ArrayList<String> ips, Socket client) {

        Boolean flag = false;

        if (ips.size() < 2) {

            String ip = client.getRemoteSocketAddress().toString();
            String parts[] = parseIp(ip);

            for(int k = 0; k < ips.size(); k++) {
                flag = ips.get(k) == ip;
            }

            if (!flag) {
                ips.add(ip);
                return ips;
            }
        }

        return ips;
    }

    /** Extracts the initial '/' from an ip and splits the ip and port at ':'.
     * @param ip: ip gotten from Socket.getRemoteSocketAddress() as a string.
     * @return an array with the two parts, ip @ index[0] and port @ index[1] */
    public String[] parseIp(String ip) {
        ip = ip.substring(1);
        String[] parts = ip.split(":");
        return parts;
    }

}
