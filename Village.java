/*
 *
 */

public class Village extends TrainStop {

    private int id;

    Village(int id) {
        super();
        this.id = id;
    }

    public String toString() {
        return "village " + Integer.toString(id);
    }

    public void arrive(Group group) {
        super.arrive(group);
        System.out.println(group.toString() + " enters " + toString());
    }

    public Group depart() {
        if (super.getGroup() != null) {
            System.out.println(super.getGroup() + " leaves " + toString());
        }
        return super.depart();
    }
}
