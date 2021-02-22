package monitors.unsync;

public class SharedCell {
  public static void main(String args[]) {
    HoldIntegerUnsynchronized sharedObject = new HoldIntegerUnsynchronized();
    ProduceInteger producer = new ProduceInteger(sharedObject);
    ConsumeInteger consumer = new ConsumeInteger(sharedObject);

    producer.start();
    consumer.start();
  }
}
