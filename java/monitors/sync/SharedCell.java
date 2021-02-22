package monitors.sync;

public class SharedCell {
  public static void main(String args[]) {
    HoldIntegerSynchronized sharedObject = new HoldIntegerSynchronized();
    ProduceInteger producer = new ProduceInteger(sharedObject);
    ConsumeInteger consumer = new ConsumeInteger(sharedObject);

    producer.start();
    consumer.start();
  }
}
