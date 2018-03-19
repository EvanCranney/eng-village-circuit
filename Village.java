public class Village implements TrainStop {

    private int id;
    private Group group;

    Village(int id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return group != null;
    }

    public synchronized void enter(Group group) {
        while (isOccupied()) {
            try { wait(); }
            catch (InterruptedException e) { }
        }
        this.group = group;
        System.out.println(group.toString() + " enters " + toString());
        notifyAll();
    }

    public synchronized Group leave() {
        while (!isOccupied()) {
            try { wait(); }
            catch (InterruptedException e) { }
        }
        System.out.println(group.toString() + " leaves " + toString());
        Group tmp = this.group;
        this.group = null;
        notifyAll();
        return tmp;
    }

    public String toString() {
        return "village " + Integer.toString(id);
    }
}
