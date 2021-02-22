package monitors.buffered;

import javax.swing.*;

public class UpdateThread extends Thread {
  private JTextArea outputArea;
  private String messageToOutput;

  public UpdateThread(JTextArea output, String message) {
    outputArea = output;
    messageToOutput = message;
  }

  public void run() {
    outputArea.append(messageToOutput);
  }
}
