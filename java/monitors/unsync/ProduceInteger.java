package monitors.unsync;

public class ProduceInteger extends Thread {
  private HoldIntegerUnsynchronized sharedObject;

  public ProduceInteger(HoldIntegerUnsynchronized shared) {
    super("ProducerInteger");
    sharedObject = shared;
  }

  public void run() {
    for (int count = 1; count <= 10; count++) {
      try {
        Thread.sleep((int) (Math.random() * 3000));
      }
      catch(InterruptedException ie) {
        System.err.println(ie.toString());
      }
      sharedObject.setSharedInt(count);
    }
    System.err.println(getName() + " finished producing values" +
      "\n Terminating " + getName());
  }
}
