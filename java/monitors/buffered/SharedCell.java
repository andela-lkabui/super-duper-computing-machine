package monitors.buffered;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;

public class SharedCell extends JFrame{
  public SharedCell() {
    super("Demonstrating Thread Synchronization.");
    JTextArea outputArea = new JTextArea(20, 30);
    getContentPane().add(new JScrollPane(outputArea));
    setSize(500, 500);
    show();

    HoldIntegerSynchronized sharedObject =
      new HoldIntegerSynchronized(outputArea);
    ProduceInteger producer = new ProduceInteger(sharedObject, outputArea);
    ConsumeInteger consumer = new ConsumeInteger(sharedObject, outputArea);

    producer.start();
    consumer.start();
  }

  public static void main(String args[]) {
    SharedCell application = new SharedCell();
    application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
