package monitors.buffered;

import javax.swing.*;

public class ConsumeInteger extends Thread {
  private HoldIntegerSynchronized sharedObject;
  private JTextArea outputArea;

  public ConsumeInteger(HoldIntegerSynchronized shared, JTextArea output) {
    super("ConsumeInteger");
    sharedObject = shared;
    outputArea = output;
  }

  public void run() {
    int value, sum = 0;
    do {
      try {
        Thread.sleep((int) (Math.random() * 5000));
      }
      catch(InterruptedException ie) {
        System.err.println(ie.toString());
      }
      value = sharedObject.getSharedInt();
      sum += value;
    } while (value != 10);
    SwingUtilities.invokeLater(new UpdateThread(outputArea,
      "\n" + getName() + " retrieved values totalling: " +
      sum + "\n Terminating " + getName() + "\n"));
  }
}
