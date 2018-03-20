/**
 * @author ecranney
 *
 * A train, used to transport groups of tourists between train stops:
 * picks up a group from its source station, and drops them off at the
 * destination.
 *
 */

public class Train extends Thread {

    private TrainStop source;           // train's pickup point
    private TrainStop destination;      // train's dropoff point
    private Group group;                // group currently on train

    Train(TrainStop source, TrainStop destination) {
        this.source = source;
        this.destination = destination;
    }

    // run the train thread, starts transporting groups between source
    //   and destination
    public void run() {
        while (!isInterrupted()) {
            // pick up group at source, then travel to destination
            group = source.leave();
            travel();

            // drop of group at destination, then travel back to source
            destination.enter(group);
            travel();
        }
    }

    // travel between source and destination
    public void travel() {
        try {
            // simulate travel time by sleeping
            sleep(Params.JOURNEY_TIME);
        }
        catch (InterruptedException e) {
            // empty
        }
    }
}
