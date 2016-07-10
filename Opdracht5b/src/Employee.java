/**
 * Created by Frank Verhagen on 9-7-2016.
 * The Employee class is used to store the properties of an employee of company Dutchflowers
 * It is made comparable so the Employees can be stored alphabetically in a TreeSet
 */
public class Employee implements Comparable<Employee>{
    private String email;
    private String name;

    public Employee(String email) {
        this.email = email;
        String[] splitNameFromEmail = email.split("[@]"); //Splitting the emailaddress at the @
        this.name = splitNameFromEmail[0]; //All the characters before the @ are assigned to name
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Employee employee) { //Comparing if the letter is before or behind the comparing object in the alphabet
        int compare = getName().compareTo(employee.getName());
        return compare;
    }
}
