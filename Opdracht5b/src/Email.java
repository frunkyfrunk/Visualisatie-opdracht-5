import java.sql.Timestamp;

/**
 * Created by Frank Verhagen on 9-7-2016.
 * The email class is used for storing the properties of an email
 */
public class Email {
    private Timestamp date;
    private String sender;
    private String receiver;

    public Email(Timestamp date, String sender, String receiver) {
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
