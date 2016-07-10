/**
 * Created by Frank Verhagen on 6-7-2016.
 * This class is used for storing a connection line between child and couple
 */
public class Connection {
    private Couple parents;
    private int child;
    private int startX;
    private int startY;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public Connection(FamilyMember child, Couple parents){
        this.parents = parents;
        this.child = child.getId();
    }

    public Couple getParents() {
        return parents;
    }

    public int getChild() {
        return child;
    }
}
