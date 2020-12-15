package P2P;

public class Message {

    public int id;
    public String descr;

    public Message(int id, String descr) {
        this.id = id;
        this.descr = descr;
    }

    public String getDescr() {
        return descr;
    }
}
