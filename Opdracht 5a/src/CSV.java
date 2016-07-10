import java.io.*;
import java.util.ArrayList;

/**
 * Created by Frank Verhagen on 1-7-2016.
 * Object used to read the CSV file
 */
public class CSV {
    private String filename;
    public CSV(String filename){
        this.filename = filename;
    }

    public ArrayList<FamilyMember> readFamilyMembers() throws IOException  {
        ArrayList<FamilyMember> familyMembers = new ArrayList<FamilyMember>();
        DataInputStream in;
        FileInputStream fstream;
        String strLine;
        BufferedReader br;

        try{
            fstream = new FileInputStream(filename);
            in = new DataInputStream(fstream);

            //the use of "ISO-8859-1" is needed for the special characters, like scandinavian O
            br = new BufferedReader(new InputStreamReader(fstream,"ISO-8859-1"));

            //Reads every line
            while ((strLine = br.readLine()) != null) {

                String[] tokens = strLine.split(";");

                //For each line, this is tried. If it fails that means the line is not filled with the right kind of data
                try {
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    String shortenedName = tokens[2];
                    int father;
                    int mother;

                    //If there's no father it will be set to -1
                    if(tokens[3].equals("*")){
                        father = -1;
                    } else {
                        father = Integer.parseInt(tokens[3]);
                    }

                    //If there's no mother it will be set to -1
                    if(tokens[4].equals("*")){
                        mother = -1;
                    } else {
                        mother = Integer.parseInt(tokens[4]);
                    }

                    familyMembers.add(new FamilyMember(id, name,shortenedName, mother, father));
                } catch (NumberFormatException e) { // If a line is non-parseable
                }

            }
            in.close();
        }
        catch (NullPointerException e){
        }
        return familyMembers;
    }
}
