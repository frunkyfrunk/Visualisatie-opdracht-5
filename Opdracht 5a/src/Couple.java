/**
 * Created by Frank Verhagen on 6-7-2016.
 * This class is used for storing a couple
 */
public class Couple {
    private FamilyMember mother;
    private FamilyMember father;
    private int childID;
    private int position;

    public Couple(FamilyMember mother, FamilyMember father, int childID){
        this.mother = mother;
        this.father = father;
        this.childID = childID;
    }


    public FamilyMember getMother() {
        return mother;
    }

    public FamilyMember getFather() {
        return father;
    }

    public int getChildID() {
        return childID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
