/**
 * @author ecranney
 *
 * An operator for the cable car. Sends the car between the valley and
 * terminus.
 */

public class Operator extends Thread {

    private CableCar cableCar;          // cable car to be operated

    Operator(CableCar cableCar) {
        this.cableCar = cableCar;
    }

    // run the operator thread - waits till the cable car is occupied,
    //   then sends the car to the opposite end of the line
    public void run() {
        while(!isInterrupted()) {
            try {
                // simulate time taken for operator to prepare to leave
                sleep(Params.operateLapse());
            }
            catch (InterruptedException e) {
                // empty
            }
            sendCar();
        }
    }

    // sends the cable car between valley and terminus (and vice versa)
    private void sendCar() {
        cableCar.travel();
    }

    
}
