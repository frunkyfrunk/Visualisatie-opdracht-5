/**
 * Created by Frank Verhagen on 1-7-2016.
 * This class is usded for storing a familymember
 */
public class FamilyMember {
    private String shortName;
    private String name;
    private int father;
    private int mother;
    private int id;

    public FamilyMember(int id, String name, String shortenedName, int mother, int father) {
        this.id = id;
        this.name = name;
        this.father = father;
        this.mother = mother;
        this.shortName = shortenedName;
    }


    public String getShortName() {
        return shortName;
    }

    public String getName() {
        return name;
    }

    public int getFather() {
        return father;
    }

    public int getMother() {
        return mother;
    }

    public int getId() {
        return id;
    }

}
