/**
 * Created by Frank Verhagen on 10-7-2016.
 * The Relationship class is used for storing a possible Relationship between two employees
 * It implements the Java Comparable class so it can be ordered on amount of messages in a TreeSet
 */
public class Relationship implements Comparable<Relationship> {
    private String EmployeeA;
    private String EmployeeB;
    private int messages;

    public Relationship(Connection connection) {
        EmployeeA = connection.getEmployeeB();
        EmployeeB = connection.getEmployeeA();
        messages = connection.getMessages();
    }

    public String getEmployeeB() {
        return EmployeeB;
    }

    public int getMessages() {
        return messages;
    }

    public String getEmployeeA() {
        return EmployeeA;
    }

    @Override
    public int compareTo(Relationship relationship) {
        if (messages >= relationship.messages) {
            //If the amount of messages is the same or less it returns -1 which means: "Put the comparing object before this object in the list"
            return -1;
        } else { //If the amount of messages is more it returns 1
            return 1;
        }

    }
}
