package com.streamlio.localfilesystem;

import com.streamlio.io.Readable;
import com.streamlio.io.ReaderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class LocalFileReader implements Readable<StringLineReadResult, ReaderContext>{

    private static final Logger LOG = LoggerFactory.getLogger(LocalFileReader.class);

    private BufferedReader reader;
    private AtomicLong linesRead;

    public LocalFileReader(String fileName){
        try {
            this.reader = new BufferedReader(new java.io.FileReader(fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        linesRead = new AtomicLong(0);
    }

    @Override
    public StringLineReadResult query(ReaderContext context) {
        try {
            String line = reader.readLine();
            if (line != null) {
                long id = linesRead.incrementAndGet();
                return new StringLineReadResult(id, line);
            } else {
                LOG.info("Finished reading file, " + linesRead.get() + " lines read");
                reader.close();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            LOG.error("Problem reading file, " + linesRead.get() + " lines read", e);
        }
        return new StringLineEndReadResult();
    }

    @Override
    public boolean isOpen() {
        try {
            return reader.ready();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
