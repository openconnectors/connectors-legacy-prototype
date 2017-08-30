package com.streamlio.localfilesystem;

import com.streamlio.api.sink.RichSink;
import com.streamlio.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LineSink extends RichSink<WriteResult,LineWriterContext,WriteIO> {

    private static final Logger LOG = LoggerFactory.getLogger(LineSink.class);
    private Writeable writer;
    private final String fileName;

    public LineSink(final WriteIO context, final String fileName) {
        super(context);
        this.fileName = fileName;
    }

    @Override
    public Writeable open(WriteIO Context) {
        this.writer = new LocalFileWriter(this.fileName);
        return this.writer;
    }

    @Override
    public boolean isOpen() {
        return writer.isOpen();
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

    @Override
    public WriteResult write(WriterContext context) {
        return this.writer.write(context);
    }
}
