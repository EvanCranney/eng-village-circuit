public class Operator extends Thread {

    private CableCar cableCar;

    Operator(CableCar cableCar) {
        this.cableCar = cableCar;
    }

    public void run() {
        while(!isInterrupted()) {
            try { sleep(1000); }
            catch (InterruptedException e) { }
            if (cableCar.atTerminus()) {
                cableCar.descend();
            } else {
                cableCar.ascend();
            }
        }
    }
}
