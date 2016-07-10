/**
 * Created by Frank Verhagen on 8-7-2016.
 * Class used for the positions list
 */
public class MemberPosition {
    private FamilyMember familyMember;
    private int positionX;
    private int positionY;
    public MemberPosition(FamilyMember familyMember, int positionX, int positionY){
        this.familyMember = familyMember;
        this.positionX = positionX;
        this.positionY = positionY;

    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
