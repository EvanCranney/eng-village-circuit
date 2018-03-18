/*
 * 
 */

public class TrainStop {

    // the group waiting at the stop
    private Group group;

    TrainStop() {
        group = null;
    }

    // check if train stop is already occupied by a group
    public boolean isOccupied() {
        return group != null;
    }

    // get the group currently at the stop
    protected Group getGroup() {
        return group;
    }

    // group arrives at train stop
    public synchronized void arrive(Group group) {
        // group must wait until stop is unoccupied
        while (isOccupied()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        // then they can enter the stop
        this.group = group;
    }

    // group (if there is one) leaves train stop
    public synchronized Group depart() {
        // wait till there's someone to take
        while (!isOccupied()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }

        // remove the group from the train stop
        Group tmp = this.group;
        group = null;

        // and notify anyone waiting to enter
        notifyAll();
        return tmp;
    }
}
