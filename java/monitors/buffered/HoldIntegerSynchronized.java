package monitors.buffered;

import java.text.DecimalFormat;
import javax.swing.*;

public class HoldIntegerSynchronized {
  private int sharedInt[] = {-1, -1, -1, -1, -1};
  private boolean writeable = true;
  private boolean readable = false;
  private int readLocation = 0, writeLocation = 0;
  private JTextArea outputArea;

  public HoldIntegerSynchronized(JTextArea output) {
    outputArea = output;
  }

  public synchronized void setSharedInt(int value) {
    while (!writeable) {
      try {
        SwingUtilities.invokeLater(new UpdateThread(
          outputArea, " WAITING TO PRODUCE " + value
        ));
        wait();
      }
      catch(InterruptedException ie) {
        System.err.println(ie.toString());
      }
    }
    sharedInt[writeLocation] = value;
    readable = true;

    SwingUtilities.invokeLater(new UpdateThread(
      outputArea, "\n Produced " + value + " into cell " + writeLocation
    ));
    writeLocation = (writeLocation + 1) % 5;

    SwingUtilities.invokeLater(new UpdateThread(
      outputArea, "\t write " + writeLocation + "\t read " + readLocation
    ));

    displayBuffer(outputArea, sharedInt);

    if (writeLocation == readLocation) {
      writeable = false;
      SwingUtilities.invokeLater(new UpdateThread(
        outputArea, "\n BUFFER FULL"
      ));
    }
    notify();
  }

  public synchronized int getSharedInt() {
    int value;
    while (!readable) {
      try {
        SwingUtilities.invokeLater(new UpdateThread(
          outputArea, " WAITING TO CONSUME "
        ));
        wait();
      }
      catch(InterruptedException ie) {
        System.err.println(ie.toString());
      }
    }
    writeable = true;
    value = sharedInt[readLocation];
    SwingUtilities.invokeLater(new UpdateThread(
      outputArea, "\n Consumed " + value + " from cell " + readLocation
    ));

    readLocation = (readLocation + 1) % 5;

    SwingUtilities.invokeLater(new UpdateThread(
      outputArea, "\t write " + writeLocation + "\t read " + readLocation
    ));

    displayBuffer(outputArea, sharedInt);

    if (readLocation == writeLocation) {
      readable = false;
      SwingUtilities.invokeLater(new UpdateThread(
        outputArea, "\n BUFFER EMPTY"
      ));
    }
    notify();
    return value;
  }

  public void displayBuffer(JTextArea outputArea, int buffer[]) {
    DecimalFormat formatNumber = new DecimalFormat(" #;-#");
    StringBuffer outputBuffer = new StringBuffer();

    for (int count = 0; count < buffer.length; count++) {
      outputBuffer.append(" " + formatNumber.format(buffer[count]));
    }
    SwingUtilities.invokeLater(new UpdateThread(
      outputArea, "\t buffer: " + outputBuffer
    ));
  }
}
