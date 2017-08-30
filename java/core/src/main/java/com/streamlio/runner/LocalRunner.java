package com.streamlio.runner;

import com.streamlio.localfilesystem.*;

import java.io.IOException;

public class LocalRunner extends Runner<LineSource,LineTransformer,LineSink>{

    @Override
    public void setup() {
        this.source = new LineSource();
        this.sink = new LineSink();
        this.source.open(null);
        this.sink.open(null);
    }

    @Override
    public void start() {

    }

    @Override
    public void close() throws IOException {
        this.source.close();
        this.sink.close();
    }


    public static void main(String[] args) throws IOException {

        LineSource lsource = new LineSource(null, "/Users/a.ahmed/Desktop/test.txt");
        lsource.open(null);
        LineSink lsink = new LineSink(null, "/Users/a.ahmed/Desktop/test_out.txt");
        lsink.open(null);

        StringLineReadResult temp = lsource.query(null);

        while(!(temp instanceof StringLineEndReadResult)){
            lsink.write(new LineWriterContext(temp.getData()));
        }

        lsource.close();
        lsink.close();

    }


}
