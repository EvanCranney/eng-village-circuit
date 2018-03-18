/*
 *
 */


public class CableCar extends TrainStop {

    private boolean atTerminus;

    CableCar() {
        super();
        atTerminus = false;
    }

    public void arrive(Group group) {
        if (!atTerminus) {
            System.out.println(group.toString() + " enters " + toString() + 
                " to go up");
        } else {
            System.out.println(group.toString() + " enters " + toString() +
                " to go down");
        }
        super.arrive(group);
    }

    public Group depart() {
        if (!atTerminus) {
            if (super.getGroup() != null) {
                System.out.println(super.getGroup().toString() + "departs");
            }
        }
        return super.depart();
    }

    public boolean atTerminus() {
        return atTerminus;
    }

    public void ascend() {
        if (!atTerminus) {
            atTerminus = true;
        }
    }

    public void descend() {
        if (atTerminus) {
            atTerminus = false;
        }
    }

    public String toString() {
        return "cable car";
    }
}
