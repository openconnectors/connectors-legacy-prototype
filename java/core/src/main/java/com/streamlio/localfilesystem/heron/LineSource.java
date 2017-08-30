package com.streamlio.localfilesystem.heron;

import com.streamlio.api.source.RichSource;
import com.streamlio.io.*;
import com.streamlio.io.Readable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class LineSource extends RichSource<StringLineReadResult, ReaderContext, ReadIO> {

    private static final Logger LOG = LoggerFactory.getLogger(LineSink.class);

    private final String fileName;
    private AtomicLong linesRead;
    private BufferedReader reader;

    public LineSource(ReadIO context, String fileName) {
        super(context);
        this.fileName = fileName;
    }

    @Override
    public Readable open(ReadIO context) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        linesRead = new AtomicLong(0);
    }

    @Override
    public ReadResult query(ReaderContext context) {
        try {
            String line = reader.readLine();
            if (line != null) {
                long id = linesRead.incrementAndGet();
                return new StringLineReadResult(id, line);
            } else {
                LOG.info("Finished reading file, " + linesRead.get() + " lines read");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            LOG.error("Problem reading file, " + linesRead.get() + " lines read", e);
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
