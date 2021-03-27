package com.larfree.periscope.core.log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Monitor extends OutputStream {

    private PrintStream real;
    private List<Byte> buffer;


    private ArrayList<String> logs = new ArrayList<>();

    private char lineChar;

    public Monitor(PrintStream printStream) {
        this.real = printStream;
        this.buffer = this.createByteBuffer();
        String s = System.getProperty("line.separator");
        //读取换行符
        lineChar = s.charAt(s.length() - 1);
    }

    PrintStream getReal() {
        return real;
    }

    @Override
    synchronized
    public void write(int b) throws IOException {
        buffer.add(Integer.valueOf(b).byteValue());

        //如果是换行符
        if (b == lineChar) {
            String line = makeString();
            try {
                logs.add(line);
            } catch (Throwable t) {
                onError(t, line);
            } finally {
                real.print(line);
            }
        }
    }

    protected String makeString() {
        byte[] bs = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            bs[i] = buffer.get(i);
        }
        String str = new String(bs);
        buffer = this.createByteBuffer();
        return str;
    }

    protected List<Byte> createByteBuffer() {
        return new ArrayList<Byte>(200);
    }

    protected void onError(Throwable t, String str) {
        real.print(t);
        real.print(str);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (String log : logs) {
            message.append(log);
        }
        return message.toString();
    }

}