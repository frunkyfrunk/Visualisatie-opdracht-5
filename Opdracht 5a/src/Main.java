import processing.core.PApplet;
import processing.core.PFont;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Frank Verhagen on 25-6-2016.
 * This class contains the PApplet, here the program is drawn
 * All the Processing parts of the code are in this class
 */
public class Main extends PApplet {
    private CSV csv = new CSV("Kwartierstaat.csv"); //creating a CSV object from the file Kwartierstaat.csv
    private ArrayList<FamilyMember> familyMembers = new ArrayList<FamilyMember>(); //List with all familymembers
    private ArrayList<MemberPosition> positions = new ArrayList<MemberPosition>(); // List with all positions of the familymembers
    private LinkedHashSet<Connection> uniqueConnections; //List of all unique connections between parents and children
    private PFont times; //Font used


    public static void main(String Args[]){
        PApplet.main(new String[] { Main.class.getName() } );
    }

    public void settings(){
        size(1800,900);
    }

    public void setup(){
        //Trying to parse the CSV file to familymembers
        try { familyMembers = csv.readFamilyMembers(); } catch (IOException e) {}

        //Using New york times as font
        times = createFont("Times New Roman",24);
        textFont(times, 12);
        drawFamilyTree(familyMembers);
    }

    public void draw(){
        background(255);
        drawFamilyConnections(uniqueConnections); // Setting up the familyconnections with all the connections
        drawFamilyTree(familyMembers); //Draw the family tree from the members

        textSize(25);
        text("Current familymember:", 25,height-135);

        //List used to check if the mouses position matches one of the familymembers' positions
        List<MemberPosition> hovers = positions.stream().filter(a -> a.getPositionX() <= mouseX && mouseX <= a.getPositionX()+200
                && a.getPositionY() <= mouseY && mouseY <= a.getPositionY()+40).collect(Collectors.toList());

        //If the hovers list is not empty the hover code will be executed
        if(!hovers.isEmpty()){
            //Get the first in the list of hovered members
            MemberPosition hoveredMemberPosition = hovers.get(0);
            FamilyMember hoveredMember = hoveredMemberPosition.getFamilyMember();
            fill(0);

            //Name, mothers name and fathers name of the member
            String name = hoveredMember.getName();

            //Default value is unknown
            String mother = "Unknown";
            String father = "Unknown";

            //If there's no information about the mother or father it will be catched
            try{
                mother = familyMembers.stream().filter(p -> p.getId() == hoveredMember.getMother()).collect(Collectors.toList()).get(0).getName();
                father = familyMembers.stream().filter(p -> p.getId() == hoveredMember.getFather()).collect(Collectors.toList()).get(0).getName();
            } catch (IndexOutOfBoundsException e){}

            //Draw information about member
            textSize(25);
            text(name, 25,height-105);
            text("Mother: "+ mother, 25,height-75);
            text("Father: "+ father, 25,height-45);
        }



    }

    private void drawFamilyTree(ArrayList<FamilyMember> familyMembers) {
        // Used to prevent duplicates
        LinkedHashSet<Couple> uniqueCouples = new LinkedHashSet<Couple>();
        // Used to prevent duplicates
        uniqueConnections = new LinkedHashSet<Connection>();

        //The 'owner' of the family tree. The person on which this tree is based
        FamilyMember familyTreeOwner = new FamilyMember(0, "default", "", 1,2);
        for(FamilyMember familyMember: familyMembers) {
            int id = familyMember.getId();

            //Check if there is a father and a mother of this member in the familyMembers list
            List<FamilyMember> motherMatches = familyMembers.stream().filter(a -> a.getId() == familyMember.getMother()).collect(Collectors.toList());
            List<FamilyMember> fatherMatches = familyMembers.stream().filter(a -> a.getId() == familyMember.getFather()).collect(Collectors.toList());

            FamilyMember mother = null;
            FamilyMember father = null;
            Couple couple;
            if(! motherMatches.isEmpty()){
                //Create mother object
                mother = motherMatches.get(0);
            }
            if(! fatherMatches.isEmpty()){
                //Create father object
                father = fatherMatches.get(0);
            }
            if(father != null && mother != null) {
                //Create new couple with mother and father and child
                couple = new Couple(mother, father, id);

                //Create connection with child and couple
                Connection connection = new Connection(familyMember, couple);
                uniqueCouples.add(couple); // Used to prevent duplicates
                uniqueConnections.add(connection);// Used to prevent duplicates
            }
        }
        for(FamilyMember familyMember: familyMembers){
            int id = familyMember.getId();
            //Check if the familymember is a father or a mother
            Collection<Couple> fatherMatches = uniqueCouples.stream().filter(p -> p.getFather().getId()== id).collect(Collectors.toList());
            Collection<Couple> motherMatches = uniqueCouples.stream().filter(p -> p.getMother().getId()== id).collect(Collectors.toList());
            //If the member is not a father or mother then it's the family tree owner.
            if(fatherMatches.isEmpty() && motherMatches.isEmpty()){
                familyTreeOwner = familyMember;
            }
        }

        for(Couple couple: uniqueCouples){
            //Setup every couple
            int positionY = getPositionY(couple.getChildID());
            int positionX = (int) getPositionX(couple.getChildID());
            FamilyMember mother = couple.getMother();
            FamilyMember father = couple.getFather();
            drawFamilyMember(mother, positionX, positionY);
            drawFamilyMember(father, positionX, positionY-50);

        }
        // Setup family tree owner
        drawFamilyMember(familyTreeOwner, 0 ,0);
        textSize(40);
        text("Family Tree of " + familyTreeOwner.getName(),width/2-250,40);
        textSize(25);
        text("Hover familymember with mouse to see full name",width/2-250,70);


    }


    // Get the Y axis position for the familymember
    private int getPositionY(int id) {
        if(id == 1 ){
            return 150;
        } else if(id  <= 3){
            return 320;
        } else if(id <= 7){
            return 490;
        } else if(id <=15) {
            return 680;
        } else{
            return 750;
        }
    }

    // Get the X axis position for the familymember
    private double getPositionX(int id) {
        if(id == 1 ){
            return 0;
        } else if(id  <= 3){
            return (id%2*250)-125;
        } else if(id <= 7){
            return (id%4*300)-420;
        } else if(id <=11) {
            return (id%8*210)-700;
        }else if(id == 12) {
            return (5*210)-700;
        }else if(id == 13) {
            return (4*210)-700;
        }else if(id == 14) {
            return (7*210)-700;
        } else if(id == 15) {
            return (6*210)-700;
        } {
            return 5;
        }
    }
    //Setup the familymember
    private void drawFamilyMember(FamilyMember member, int positionX, int positionY) {
        //positionY is the distance of the family member to the youngest generation. 0=youngest, 1=parents, 2=grandparents, etc..
        //positionX is the position in the row. 0 is the middle position.
        String name = member.getShortName();
        int rectWidth = 200;
        int rectHeight = 40;
        int rectPosX = width/2-(rectWidth/2)+positionX;
        int rectPosY = height-positionY-100;
        fill(255);
        rect(rectPosX, rectPosY, rectWidth, rectHeight);
        positions.add(new MemberPosition(member, rectPosX,rectPosY));
        int textPosX = rectPosX+(rectWidth/10)-10;
        int textPosY = rectPosY+25;
        fill(0);
        textSize(20);
        text(name, textPosX,textPosY);
    }

    //Draw connection lines between couples and childs
    private void drawFamilyConnections(LinkedHashSet<Connection> connections) {
        strokeWeight(10);
        stroke(140,101,33);
        for (Connection connection:connections) {
            MemberPosition motherPosition = positions.stream().filter(p -> p.getFamilyMember().getId() == connection.getParents().getMother().getId()).collect(Collectors.toList()).get(0);
            MemberPosition fatherPosition = positions.stream().filter(p -> p.getFamilyMember().getId() == connection.getParents().getFather().getId()).collect(Collectors.toList()).get(0);
            MemberPosition childPosition= positions.stream().filter(p -> p.getFamilyMember().getId() == connection.getChild()).collect(Collectors.toList()).get(0);
            if(motherPosition.getPositionX() < 200 || motherPosition.getPositionX() > width-300){
                line(fatherPosition.getPositionX() + 100, motherPosition.getPositionY()+20, fatherPosition.getPositionX() + 100, childPosition.getPositionY() +20);
                line(fatherPosition.getPositionX() + 100, childPosition.getPositionY() +20, childPosition.getPositionX() + 100, childPosition.getPositionY() +20);
            }
            else if(motherPosition.getPositionX() < width/2 || motherPosition.getPositionY() > height/2-100){
                line(fatherPosition.getPositionX() + 100, motherPosition.getPositionY()+20, fatherPosition.getPositionX() + 100, childPosition.getPositionY()+20);
                line(fatherPosition.getPositionX() + 100, childPosition.getPositionY()+20, childPosition.getPositionX() + 100, childPosition.getPositionY()+20);
            } else {
                line(fatherPosition.getPositionX() + 100, motherPosition.getPositionY()+20, fatherPosition.getPositionX() + 100, childPosition.getPositionY()+20);
                line(fatherPosition.getPositionX() + 100, childPosition.getPositionY()+20, childPosition.getPositionX() + 100, childPosition.getPositionY()+20);
            }
        }
        strokeWeight(1);
        stroke(0);
    }
}
