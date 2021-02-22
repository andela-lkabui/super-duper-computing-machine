package monitors.buffered;

import javax.swing.*;

public class ProduceInteger extends Thread {
  private HoldIntegerSynchronized sharedObject;
  private JTextArea outputArea;

  public ProduceInteger(HoldIntegerSynchronized shared, JTextArea output) {
    super("ProduceInteger");
    sharedObject = shared;
    outputArea = output;
  }

  public void run() {
    for (int count = 0; count <= 10; count++) {
      try{
        Thread.sleep((int) (Math.random() * 500));
      }
      catch(InterruptedException ie) {
        System.err.println(ie.toString());
      }
      sharedObject.setSharedInt(count);
    }
    SwingUtilities.invokeLater(new UpdateThread(
      outputArea, "\n" + getName() + " finished producing values " +
      "\n Terminating " + getName() + "\n"));
  }
}
