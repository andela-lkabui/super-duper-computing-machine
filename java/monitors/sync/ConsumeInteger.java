package monitors.sync;

public class ConsumeInteger extends Thread{
  private HoldIntegerSynchronized sharedObject;

  public ConsumeInteger(HoldIntegerSynchronized shared) {
    super("ConsumeInteger");
    sharedObject = shared;
  }

  public void run() {
    int value, sum = 0;

    do {
      try {
        Thread.sleep((int) Math.random() * 3000);
      }
      catch(InterruptedException ie) {
        System.err.println(ie.toString());
      }
      value = sharedObject.getSharedInt();
      sum += value;
    } while(value != 10);

    System.err.println(getName() + " retrieved values totalling: " +
      sum + "\n Terminating " + getName());
  }
}
