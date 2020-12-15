package ServerClient;

public class Message {

    private int info;
    private String description;
    private String extra;

    /** Two constructors one for when clients use the extra attribute, other when they don't it's set to empty.
     * @param info: info to communicate.
     *              - 1 game will start.
     *              - 2 your opponent disconnected. Waiting for him.
     *              - 5 Waiting for opponent to connect.
     * @param description: Description of the type. */
    public Message(int info, String description) {
        this.info = info;
        this.description = description;
        this.extra = "";
    }

    public Message(int type, String description, String extra) {
        this.info = info;
        this.description = description;
        this.extra = extra;
    }

    public int getInfo() { return info; }
    public String getDescription() { return description; }
    public String getExtra() { return extra; }

}
