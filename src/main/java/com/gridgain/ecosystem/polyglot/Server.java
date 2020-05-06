package com.gridgain.ecosystem.polyglot;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class Server {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start(AppConfig.getConfiguration());

    }
}
