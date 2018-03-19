class CableCar implements TrainStop {

    private Group group;            // group occupying the car
    private boolean inValley;       // flag indicating if in valley
    private boolean isDeparting;    // flag indicating if departing

    CableCar() {
        inValley = true;
        isDeparting = true;
    }

    public boolean isOccupied() {
        return group != null;
    }

    // fill with group from village
    public synchronized void enter(Group group) {
        // wait till unoccupied AND in valley AND departing
        while (isOccupied() || !inValley) {
            try { wait(); }
            catch (InterruptedException e) { }
        }
        // then fill with group
        System.out.println(group.toString() +
            " enters the cable car to go down");
        this.group = group;
        isDeparting = true;
        notifyAll();
    }

    // empty group to go to village
    public synchronized Group leave() {
        // wait till occupied AND in valley AND and is arriving
        while (!isOccupied() || !inValley) {
            try { wait(); } 
            catch (InterruptedException e) { }
        }
        System.out.println(group.toString() + " leaves the cable car");
        notifyAll();
        isDeparting = false;
        return this.group;
    }

    // travel between the valley and terminus
    public synchronized void travel() {
        while (!isDeparting) {
            try { wait(); }
            catch (InterruptedException e) { }
        }
        try {
            if (inValley) {
                System.out.println("cable car ascends");
            } else {
                System.out.println("cable car descends");
            }
            inValley = !inValley;
            wait(Params.OPERATE_TIME);
        }
        catch (InterruptedException e) { }
        notifyAll();
    }

    // function for the producer - fill with group from valley
    public synchronized void arrive(Group group) {
        // wait till not occupied and 
        while (isOccupied() || !inValley) {
            try { wait(); }
            catch (InterruptedException e) { }
        }
        System.out.println(group.toString() + " enters cable car to go up");
        this.group = group;
        isDeparting = true;
        notifyAll();
    }

    // function for the consumer - group leaves from valley
    public synchronized void depart() {
        while (!isOccupied() || !inValley) {
            try { wait(); }
            catch (InterruptedException e) { }
        }
        System.out.println(group.toString() + " departs");
        this.group = null;
        isDeparting = false;
        notifyAll();
    }
}
