package monitors.unsync;

public class HoldIntegerUnsynchronized {
  private int sharedInt = -1;

  public void setSharedInt(int value) {
    System.err.println(Thread.currentThread().getName() +
      " setting sharedInt to " + value);
    sharedInt = value;
  }

  public int getSharedInt() {
    System.err.println(Thread.currentThread().getName() +
      " retrieving sharedInt value " + sharedInt);
    return sharedInt;
  }
}
