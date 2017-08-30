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
    private BufferedWriter writer;
    private final String fileName;

    public LineSink(final WriteIO context, final String fileName) {
        super(context);
        this.fileName = fileName;
    }

    @Override
    public Writeable open(final WriteIO context) {
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Problem opening file : " +fileName, e);
        }
    }

    @Override
    public WriteResult write(LineWriteContext context) {
        LOG.info(context.getData());
        try {
            writer.write(context.getData());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Problem writing to file", e);
        }
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public WriteResult write(WriteContext write) {
        return null;
    }
}
