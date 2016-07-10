import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Frank Verhagen on 1-7-2016.
 * Object used to read the email.log file
 */
public class FileReader {
    private String filename;
    public FileReader(String filename){
        this.filename = filename;
    }

    public ArrayList<Email> readEmails() throws IOException  {
        ArrayList<Email> emails = new ArrayList<Email>();
        DataInputStream in;
        FileInputStream fileStream;
        String strLine;
        BufferedReader br;

        try{
            fileStream = new FileInputStream(filename);
            in = new DataInputStream(fileStream);

            //the use of "ISO-8859-1" is needed for the special characters, like scandinavian O
            br = new BufferedReader(new InputStreamReader(fileStream,"ISO-8859-1"));

            //Reads every line
            while ((strLine = br.readLine()) != null) {

                String[] tokens = strLine.split(";");

                //For each line, this is tried. If it fails that means the line is not filled with the right kind of data
                try {
                    //Parse the timestamp of the email to a Java SimpleDateFormat
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date parsedDate = dateFormat.parse(tokens[0]);
                    Timestamp date = new Timestamp(parsedDate.getTime());

                    //Assigning sender and receiver
                    String sender = tokens[1];
                    String receiver = tokens[2];

                    //If parsing of the line is succeeded, an email object is added to the emails list
                    emails.add(new Email(date, sender, receiver));

                    // If a line is not parsed succesfully
                } catch (NumberFormatException e) {
                } catch (ParseException e) {}
            }
            // Stop reading
            in.close();
        }
        catch (NullPointerException e){}
        return emails;
    }
}
