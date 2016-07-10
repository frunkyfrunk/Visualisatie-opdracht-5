import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Frank Verhagen on 9-7-2016.
 * The statistics class contains all methods for calculating the data for the visualisation
 * - Gets all employees
 * - Counts all woman and man employees
 * - Gets all possible relationships
 * - Counts Man to Man, Woman to Woman and Woman to Man email traffic
 */
public class Statistics {
    private ArrayList<Email> emails;
    private TreeSet<Employee> employees;
    private ArrayList<Employee> womanEmployees;
    private ArrayList<Employee> manEmployees;

    public Statistics(ArrayList<Email> emails) {
        this.emails = emails;
        employees = getEmployees(emails);
        womanEmployees = getWomanEmployees(employees);
        manEmployees = getManEmployees(employees);
    }

    //Counts the amount of emails Men have written to each other
    public int countManToManTraffic() {
        int count = 0;
        for (Email email : emails) {
            boolean receiverIsMan = manEmployees.stream().anyMatch(t -> t.getEmail().equals(email.getReceiver()));
            boolean senderIsMan = manEmployees.stream().anyMatch(t -> t.getEmail().equals(email.getSender()));
            if(senderIsMan && receiverIsMan){
                count++;
            }
        }
        return count;
    }

    //Counts the amount of emails Women have written to each other
    public int countWomanToWomanTraffic() {
        int count = 0;
        for (Email email : emails) {
            boolean receiverIsWoman = womanEmployees.stream().anyMatch(t -> t.getEmail().equals(email.getReceiver()));
            boolean senderIsWoman = womanEmployees.stream().anyMatch(t -> t.getEmail().equals(email.getSender()));
            if(receiverIsWoman && senderIsWoman){
                count++;
            }
        }
        return count;
    }

    //Counts the amount of emails women have written to men and vice versa
    public int countWomanToManTraffic() {
        int count = 0;
        for (Email email : emails) {
            boolean receiverIsWoman = womanEmployees.stream().anyMatch(t -> t.getEmail().equals(email.getReceiver()));
            boolean senderIsWoman = womanEmployees.stream().anyMatch(t -> t.getEmail().equals(email.getSender()));
            boolean receiverIsMan = manEmployees.stream().anyMatch(t -> t.getEmail().equals(email.getReceiver()));
            boolean senderIsMan = manEmployees.stream().anyMatch(t -> t.getEmail().equals(email.getSender()));
            if(receiverIsWoman && senderIsMan || receiverIsMan && senderIsWoman){
                count++;
            }
        }
        return count;
    }

    //Gets all possible relationships between employees
    public TreeSet<Relationship> getRelationships() {
        TreeSet<Relationship> relationships = new TreeSet<Relationship>();
        TreeSet<Connection> connections = getConnections(); //This method gets all connections and removes duplicates
        for (Connection connection:connections) {
            relationships.add(new Relationship(connection)); //This Object implements Comparable so the relationships will be sorted from high to low by amount of emails
        }
        return relationships;
    }

    // Gets all connections between employees
    public TreeSet<Connection> getConnections() {
        TreeSet<Connection> connections = new TreeSet<>();
        for (Email email : emails) {
            String receiver = email.getReceiver();
            String sender = email.getSender();
            List<Connection> connectionMatches = connections.stream().filter(t -> t.getEmployeeA().equals(receiver) && t.getEmployeeB().equals(sender)
                    || t.getEmployeeA().equals(sender) && t.getEmployeeB().equals(receiver)).collect(Collectors.toList());

            if(! connectionMatches.isEmpty()){
                connections.stream().filter(t -> t.getEmployeeA().equals(receiver) && t.getEmployeeB().equals(sender)
                        || t.getEmployeeA().equals(sender) && t.getEmployeeB().equals(receiver)).forEach(connection -> connection.addMessage());

            } else {
                connections.add(new Connection(receiver, sender));
            }
        }
        return connections;
    }

    //This treeset gets all employees in an alphabetical order
    private TreeSet<Employee> getEmployees(ArrayList<Email> emails) {
        TreeSet<Employee> employees = new TreeSet<Employee>(); // Used to prevent duplicates
        for (Email email : emails) {
            Employee sender = new Employee(email.getSender());
            Employee receiver = new Employee(email.getReceiver());
            employees.add(sender);
            employees.add(receiver);
        }
        return employees;
    }

    //This ArrayList gets all 'female' employees
    private ArrayList<Employee> getWomanEmployees(TreeSet<Employee> employees) {
        ArrayList<Employee> womanEmployees = new ArrayList<Employee>();
        for (Employee employee: employees) {
            if(isFemale(employee)){
                womanEmployees.add(employee);
            }
        }
        return womanEmployees;
    }

    //This ArrayList gets all 'male' employees
    private ArrayList<Employee> getManEmployees(TreeSet<Employee> employees) {
        ArrayList<Employee> manEmployees = new ArrayList<Employee>();

        for (Employee employee: employees) {

            if(!isFemale(employee)){
                manEmployees.add(employee);
            }
        }
        return manEmployees;
    }

    //Boolean to check if an employee is female. If false it is a male
    private boolean isFemale(Employee employee) {
        switch (employee.getName().substring(0,1)){
            case "A": return true;
            case "B": return true;
            case "C": return true;
            case "D": return true;
            case "E": return true;
            case "F": return true;
            case "G": return true;
            case "H": return true;
            case "I": return true;
            case "J": return true;
            case "K": return true;
            case "L": return true;
            case "M": return true;
            case "N": return false;
            case "O": return false;
            case "P": return false;
            case "Q": return false;
            case "R": return false;
            case "S": return false;
            case "T": return false;
            case "U": return false;
            case "V": return false;
            case "W": return false;
            case "X": return false;
            case "Y": return false;
            case "Z": return false;
        }
        return false;
    }


}
