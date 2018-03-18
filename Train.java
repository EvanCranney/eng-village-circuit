public class Train extends Thread {

    // pick up, drop off, and current stations
    private TrainStop source;
    private TrainStop destination;
    private TrainStop current;

    // group on the train
    private Group group;

    Train (TrainStop source, TrainStop destination) {
        this.source = this.current = source;
        this.destination = destination;
        this.group = null;
    }

    // check if train is occupied
    private boolean isOccupied() {
        return group != null;
    }

    // check if the train is at pickup location
    private boolean atSource() {
        return current == source;
    }

    // to string
    public String toString() {
        return source.toString() + "-" + destination.toString() + "train";
    }

    public void run() {
        while(!isInterrupted()) {
            if (atSource() && !isOccupied()) {
                group = current.depart();
            } else if (!atSource() && isOccupied()) {
                current.arrive(group);
                group = null;
            }

            if (atSource()) {
                current = destination;
            } else {
                current = source;
            }
        }
    }
}
