package com.gridgain.ecosystem.polyglot;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.graalvm.polyglot.Context;

import java.io.InputStream;

public class App {
    public static void main(String[] args) {
        App me = new App();
        me.run();
    }

    void run() {
        Ignition.setClientMode(true);
        Ignite ignite = Ignition.start(AppConfig.getConfiguration());

        InputStream in = getClass().getResourceAsStream("/test.js");
        JavaScriptComputeJob<String> job = new JavaScriptComputeJob<>(in, String.class);
        System.out.println(ignite.compute().call(job));

        String val = ignite.compute().call(
                () -> Context.create().eval("js", "print(\"Hello\"); \"Hello world!\";").asString()
        );
        System.out.println(val);

        ignite.close();
    }
}
