package ServerClient;

public class Address {

    private String ip;
    private int port;

    /** Utility class to store create Address object. Felt bold might delete later.
     * @param ip: ip for address.
     * @param port: port for address. */
    public Address(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIP() { return ip; }

    public int getPort() { return port; }

}
