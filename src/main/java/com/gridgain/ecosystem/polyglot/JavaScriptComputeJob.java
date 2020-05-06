package com.gridgain.ecosystem.polyglot;

import org.apache.ignite.lang.IgniteCallable;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaScriptComputeJob<V> implements IgniteCallable<V> {
    private String javaScriptCode;
    private String javaScriptCodePath;
    private CodeLocation location;
    private Class<V> type;

    public JavaScriptComputeJob(String javaScriptCode, Class<V> returnType) {
        this.javaScriptCode = javaScriptCode;
        this.type = returnType;
        this.location = CodeLocation.LOCAL;
    }

    public enum CodeLocation {
        LOCAL, REMOTE
    }

    public JavaScriptComputeJob(String filename, CodeLocation location, Class<V> returnType) {
        this.javaScriptCodePath = filename;
        this.location = location;
        if (location == CodeLocation.LOCAL) {
            loadCode(filename);
        }
        this.type = returnType;
    }

    public JavaScriptComputeJob(InputStream in, Class<V> returnType) {
        this.location = CodeLocation.LOCAL;
        loadCode(in);
        this.type = returnType;
    }

    private void loadCode(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder jsFile = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsFile.append(line);
            }
            javaScriptCode = jsFile.toString();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void loadCode(String path) {
        Path unixPath = FileSystems.getDefault().getPath(path);
        try (BufferedReader reader = Files.newBufferedReader(unixPath, Charset.forName("UTF-8"))) {
            StringBuilder jsFile = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsFile.append(line);
            }
            javaScriptCode = jsFile.toString();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public String getJavaScriptCode() {
        return javaScriptCode;
    }

    public void setJavaScriptCode(String javaScriptCode) {
        this.javaScriptCode = javaScriptCode;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public V call() throws Exception {
        if (location == CodeLocation.REMOTE) {
            loadCode(javaScriptCodePath);
        }
        Context polyglot = Context.create();
        Value retv = polyglot.eval("js", javaScriptCode);
        return retv.as(type);
    }
}
