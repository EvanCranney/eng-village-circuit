public class Operator extends Thread {

    private CableCar cableCar;    

    Operator(CableCar cableCar) {
        this.cableCar = cableCar;
    }

    public void run() {
        while(!isInterrupted()) {
            // wait a random amount of time, then move the car
            try { sleep(Params.operateLapse()); }
            catch (InterruptedException e) { }
            sendCar();
        }
    }

    private void sendCar() {
        cableCar.travel();
    }

    
}
