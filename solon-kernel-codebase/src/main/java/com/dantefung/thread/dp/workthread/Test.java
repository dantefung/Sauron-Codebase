package com.dantefung.thread.dp.workthread;

public class Test {
    public static void main(String[] args) {

        Channel channel = new Channel(5);
        channel.startWorkers();
        new ClientThread("aaa",channel).start();
        new ClientThread("bbb",channel).start();
        new ClientThread("ccc",channel).start();

    }
}