import processing.core.PApplet;
import processing.core.PFont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Frank Verhagen on 25-6-2016.
 * This class contains the PApplet, here the program is drawn
 * All the Processing parts of the code are in this class
 */
public class Main extends PApplet {
    private FileReader fileReader = new FileReader("email.log"); //Loads the email.log file
    private ArrayList<Email> emails = fileReader.readEmails(); //Get all emails in one list
    private PFont times; // Font
    private Statistics statistics; //Statistics class. For all calculations
    private float[] chartData; // Data array used for the pie chart
    private List<Relationship> relations; //List with all relations

    public Main() throws IOException {
    } // Needed because of the filereader

    public static void main(String Args[]){
        PApplet.main(new String[] { Main.class.getName() } );
    }

    public void settings(){
        size(1350,800);
    }

    public void setup() {
        //Using New York Times as font since the default one is ugly
        times = createFont("Times New Roman", 24);
        statistics = new Statistics(emails);

        //Loading the traffic data
        int manToManTraffic = statistics.countManToManTraffic();
        int womanToWomanTraffic = statistics.countWomanToWomanTraffic();
        int womanToManTraffic = statistics.countWomanToManTraffic();

        //The total amount of traffic
        int total = womanToManTraffic+manToManTraffic+womanToWomanTraffic;

        //Calculating the part of 360 for the pie chart
        float percentManToMan = (manToManTraffic*1.0f/total)*360;
        float percentWomanToWoman = (womanToWomanTraffic*1.0f/total)*360;
        float percentManToWoman = (womanToManTraffic*1.0f/total)*360;

        //Assign the data to the chartdata array
        chartData = new float[] { percentManToMan, percentWomanToWoman, percentManToWoman};

        //Assign the relationship data to the relations list
        relations = new ArrayList<> (statistics.getRelationships());

        //White background
        background(255);

        //Draw piechart of the traffic data
        drawPieChart(300, chartData,200,210, "Verdeling emails tussen geslacht",new String[]{"Man naar man","Vrouw naar vrouw","Vrouw naar man"});

        //Draw list of the 20 most likely relationships
        drawRelationsList(relations,700,20,"Aantal mails tussen persoon A en B, top 20", 20);
    }


    public void draw(){} //Draw is not used since everything is already drawn in the setup and no parts of it are live data

    //Method for drawing the pie chart
    public void drawPieChart(float diameter, float[] data, int posX, int posY, String title, String[] legend) {
        //Drawing title
        fill(0);
        textSize(25);
        text(title, posX-diameter/2+20, posY-diameter/2-20);

        textSize(20);
        float lastAngle = 0;
        for (int i = 0; i < data.length; i++) {

            float gray = map(i, 0, data.length, 0, 255);
            fill(gray);
            //Drawing legend boxes
            rect(posX+diameter/2+20, posY-diameter/2+(40*i), 20, 20);

            //Drawing Pie Chart
            arc(posX, posY, diameter, diameter, lastAngle, lastAngle+radians(chartData[i]));
            fill(0);

            //Drawing legend text
            int percent = Math.round(data[i]/360*100);
            text(legend[i] + " "+percent+"%", posX+diameter/2+45, posY-diameter/2+18+(40*i));
            lastAngle += radians(chartData[i]);
        }
    }

    //Method for drawing the list of the relations
    public void drawRelationsList(List<Relationship> relations, int posX, int posY, String title, int amount){
        //Drawing title
        textSize(25);
        text(title,posX, posY+20);

        //Drawing colum titles
        textSize(15);
        text("Persoon A",posX, posY+60);
        text("Persoon B",posX+240, posY+60);
        text("Aantal mails",posX+440, posY+60);

        //Drawing columns
        for (int x = 0; x <relations.size(); x++) {
            text(x+1+".    "+relations.get(x).getEmployeeA(), posX, posY+100+(30*x));
            text(relations.get(x).getEmployeeB() + "         ", posX+240, posY+100+(30*x));
            text(relations.get(x).getMessages() + " emails", posX+440, posY+100+(30*x));
        if(x == amount-1){
            break;
        }
        }
    }
}
