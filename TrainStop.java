/**
 * @author ecranney
 *
 * Interface for a train stop; requires implementation methods to put a
 * group of tourists into the stop ("enter") or take a group out of the
 * stop ("leave").
 */

public interface TrainStop {

    // put a group into the train stop
    public void enter(Group group);

    // take a group from the train stop
    public Group leave();
}
