package com.example.inventory.service;

import com.example.inventory.manager.InventoryManager;

/**
 * AutoSaveService
 * ---------------------------------------------------------
 * Runs on its own background thread (Runnable + Thread) and
 * periodically persists the inventory to disk, independent of
 * the main menu thread. This mirrors the multithreading concept
 * from "Module - 2/MultiThreadedFileProcessor", applied here to
 * keep data safe even if the app exits unexpectedly.
 */
public class AutoSaveService implements Runnable {

    private final InventoryManager manager;
    private final long intervalMillis;
    private volatile boolean running = true;
    private Thread worker;

    public AutoSaveService(InventoryManager manager, long intervalMillis) {
        this.manager = manager;
        this.intervalMillis = intervalMillis;
    }

    public void start() {
        worker = new Thread(this, "AutoSaveThread");
        worker.setDaemon(true); // won't block JVM exit
        worker.start();
    }

    public void stop() {
        running = false;
        if (worker != null) {
            worker.interrupt();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(intervalMillis);
                manager.persistAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
