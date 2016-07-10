/**
 * Created by Frank Verhagen on 9-7-2016.
 * The connection class is used to save a connection between two email addresses.
 */
public class Connection implements  Comparable<Connection>{
    private String employeeA;
    private String employeeB;
    private int messages;
    public Connection(String receiver, String sender) {
        this.employeeA = receiver;
        this.employeeB = sender;
        messages = 1;
    }

    public String getEmployeeA() {
        return employeeA;
    }

    public String getEmployeeB() {
        return employeeB;
    }

    public void addMessage(){
        messages++;
    }

    public int getMessages() {
        return messages;
    }

    @Override
    public int compareTo(Connection connection) {
        return 1;
    }
}
