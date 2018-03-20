/**
 * @author ecranney
 *
 * A cable car, transports groups of tourists from the valley (where
 * groups arrive) to the terminus (where groups begin their journey)
 * through the villages.
 */

class CableCar implements TrainStop {

    // number of groups
    private Group group;            // group occupying the car

    // number of groups currently in Concurrencia
    private int num_groups;

    // flag indicating if car is currently in valley
    private boolean inValley;

    // flag indicating if group in car has already been to villages
    //   (as opposed to having just arrived in the valley)
    private boolean isReturning;

    CableCar() {
        inValley = true;
        isReturning = false;
        num_groups = 0;
    }

    // checks whether the cable car is already occupied
    public boolean isOccupied() {
        return group != null;
    }

    // checks whether the maximum number of groups is already present
    //   in Concurrencia
    public boolean atCapacity() {
        return num_groups >= Params.MAX_GROUPS;
    }

    // travel between the valley and terminus - used by Operator
    public synchronized void travel() {
        // if at Valley:
        //   make sure that any returning group has been consumed
        // if at Terminus:
        //   make sure that any departing group has been picked up
        while ((inValley && isOccupied() && isReturning) ||
               (!inValley && isOccupied() && !isReturning)) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                // empty
            }
        }

        // travel between valley and terminus (or vice versa)
        try {
            if (inValley) {
                System.out.println("cable car ascends");
            } else {
                System.out.println("cable car descends");
            }
            inValley = !inValley;
            // simulate travel time of the cable car
            wait(Params.OPERATE_TIME);
        }
        catch (InterruptedException e) {
            // empty
        }
        notifyAll();
    }

    // loads group into cable car from a train - used by the train
    //   connecting the last village to the terminus 
    public synchronized void enter(Group group) {
        // wait till unoccupied AND in valley AND not returning
        while (isOccupied() || inValley || !isReturning) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                // empty
            }
        }
        // then fill with group
        System.out.println(group.toString() +
            " enters the cable car to go down");
        this.group = group;

        // group is returning from villages
        isReturning = true;
 
        notifyAll();
    }

    // removes group from cable car - used by the train connecting
    //   terminus to first village
    public synchronized Group leave() {
        // wait till occupied AND in valley
        while (!isOccupied() || inValley) {
            try {
                wait();
            } 
            catch (InterruptedException e) {
                // empty
            }
        }
        // then remove group
        System.out.println(group.toString() + " leaves the cable car");
        Group tmp = this.group;
        this.group = null;

        // any group that enters now will be returning from villages        
        isReturning = true;

        notifyAll();
        return tmp;
    }

    // simulates a group arriving in the valley by filling the cable car -
    //   used by Producer
    public synchronized void arrive(Group group) {
        // wait till not occupied AND in valley AND not at max num groups 
        while (isOccupied() || !inValley || atCapacity()) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                // empty
            }
        }
        // then generate new group
        System.out.println(group.toString() + " enters cable car to go up");
        this.group = group;

        // group is departing to villages (not returning from them)
        isReturning = false;

        // increment number of groups in Concurrencia
        num_groups += 1;

        notifyAll();
    }

    // simulates a group leaving the valley after returning in the cable car
    //   - used by Consumer
    public synchronized void depart() {
        // wait till occupied AND in valley AND group is returning
        while (!isOccupied() || !inValley || !isReturning) {
            try { wait(); }
            catch (InterruptedException e) { }
        }
        // then consume the group
        System.out.println(group.toString() + " departs");
        this.group = null;

        // any group to enter now will be departing to villages
        isReturning = false;

        // decrement number of groups currently in Concurrencia
        num_groups -= 1;

        notifyAll();
    }
}
