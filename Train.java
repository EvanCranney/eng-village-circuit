public class Train extends Thread {

    private TrainStop source;
    private TrainStop destination;
    private Group group;

    Train(TrainStop source, TrainStop destination) {
        this.source = source;
        this.destination = destination;
    }

    public void run() {
        while (!isInterrupted()) {
            // pick up group from source
            group = source.leave();

            // travel to destination
            try { sleep(Params.JOURNEY_TIME); }
            catch (InterruptedException e) { }

            // drop of group
            destination.enter(group);

            // travel back home
            try { sleep(Params.JOURNEY_TIME); }
            catch (InterruptedException e) { }
        }
    }
}
