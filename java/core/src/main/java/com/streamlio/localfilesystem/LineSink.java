package com.streamlio.localfilesystem;

import com.streamlio.api.sink.RichSink;
import com.streamlio.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LineSink extends RichSink<WriteResult,LineWriteContext,WriteIO> {

    private static final Logger LOG = LoggerFactory.getLogger(LineSink.class);
    private Writeable<WriteResult, LineWriteContext> writer;
    private final String fileName;

    public LineSink(final WriteIO context, final String fileName) {
        super(context);
        this.fileName = fileName;
    }

    @Override
    public Writeable<WriteResult, LineWriteContext> open(WriteIO Context) {
        this.writer = new LocalFileWriter(this.fileName);
        return this.writer;
    }

//    @Override
//    public WriteResult write(Object context) {
//        return null;
//    }

    @Override
    public void close() throws IOException {
        this.writer.close();

    }

    @Override
    public WriteResult write(Object context) {
        return null;
    }
}
