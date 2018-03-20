/**
 * @author ecranney
 *
 * A village, which can be visited by groups of tourists. Can only be
 * visited by a single group of tourists at any given time.
 */

public class Village implements TrainStop {

    private int id;             // unique village identifier
    private Group group;        // the group occupying the village

    Village(int id) {
        this.id = id;
    }

    // checks whether the village is already occupied by a group
    public boolean isOccupied() {
        return group != null;
    }

    // puts a group of tourists into the village; if occupied, waits until
    //   the group in currently in the village leaves
    public synchronized void enter(Group group) {
        // wait till the village is unoccupied
        while (isOccupied()) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                // empty
            }
        }
        // occupy the village
        this.group = group;
        System.out.println(group.toString() + " enters " + toString());
        notifyAll();
    }

    // removes (and returns) the group currently in the village; if
    //   not currently occupied, waits until a group has arrived
    public synchronized Group leave() {
        // wait till a group arrives
        while (!isOccupied()) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                // empty
            }
        }
        // leave the village
        System.out.println(group.toString() + " leaves " + toString());
        Group tmp = this.group;
        this.group = null;
        notifyAll();
        return tmp;
    }

    // returns string representation of village
    public String toString() {
        return "village " + Integer.toString(id);
    }
}
