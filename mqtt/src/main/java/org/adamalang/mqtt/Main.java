package org.adamalang.mqtt;

public class Main {
    public static void main(String[] args) {
        // Run the broker
        Broker mainBroker = new Broker(8080);
        mainBroker.run();
    }
}
