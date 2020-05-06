package com.gridgain.ecosystem.polyglot;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.List;

public class AppConfig {
    public static IgniteConfiguration getConfiguration() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();

        igniteConfiguration.setPeerClassLoadingEnabled(true);

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        List<String> addresses = Arrays.asList("127.0.0.1");
        ipFinder.setAddresses(addresses);

        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        discoverySpi.setIpFinder(ipFinder);
        igniteConfiguration.setDiscoverySpi(discoverySpi);

        return igniteConfiguration;
    }

}
