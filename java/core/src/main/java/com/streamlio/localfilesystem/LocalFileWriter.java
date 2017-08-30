package com.streamlio.localfilesystem;

import com.streamlio.io.WriteResult;
import com.streamlio.io.Writeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class LocalFileWriter implements Writeable<WriteResult, LineWriterContext> {

    private static final Logger LOG = LoggerFactory.getLogger(LocalFileWriter.class);

    private BufferedWriter writer;
    private AtomicLong linesWritten;
    private final String fileName;

    public LocalFileWriter(final String fileName){
        this.fileName = fileName;
        try {
            writer = new BufferedWriter(new FileWriter(this.fileName));
        } catch (IOException e) {
            throw new RuntimeException("Problem opening file " + this.fileName, e);
        }
        linesWritten = new AtomicLong(0);
    }

    @Override
    public WriteResult write(LineWriterContext context) {
        LOG.info(context.getData());
        try {
            writer.write(context.getData());
            writer.flush();
            return new SimpleWriteAck();
        } catch (IOException e) {
            LOG.error("Problem writing to file, " + linesWritten.get() + " lines written", e);
            return new SimpleWriteError();
        }
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
