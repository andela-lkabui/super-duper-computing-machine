package monitors.sync;

public class HoldIntegerSynchronized {
  private int sharedInt = -1;
  private boolean writeable = true;

  public synchronized void setSharedInt(int value) {
    while (!writeable) {
      try {
        System.err.println("Thread " + Thread.currentThread().getName() +
          " wants to wait because sharedInt is write locked at the moment.");
        wait();
      }
      catch(InterruptedException ie) {
        ie.printStackTrace();
      }
    }
    System.err.println(Thread.currentThread().getName() +
      " setting sharedInt to " + value);
    sharedInt = value;
    writeable = false;
    notify();
  }

  public synchronized int getSharedInt() {
    while (writeable) {
      try {
        System.err.println("Thread " + Thread.currentThread().getName() +
        " wants to wait because sharedInt is read locked at the moment.");
        wait();
      }
      catch(InterruptedException ie) {
        ie.printStackTrace();
      }
    }
    writeable = true;
    notify();
    System.err.println(Thread.currentThread().getName() +
      " retrieving sharedInt value " + sharedInt);
    return sharedInt;
  }
}
