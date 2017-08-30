package com.streamlio.localfilesystem;

import com.streamlio.api.source.RichSource;
import com.streamlio.io.ReadIO;
import com.streamlio.io.Readable;
import com.streamlio.io.ReaderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LineSource extends RichSource<StringLineReadResult, ReaderContext, ReadIO> {

    private static final Logger LOG = LoggerFactory.getLogger(LineSink.class);

    private final String fileName;
    private Readable<StringLineReadResult, ReaderContext> reader;

    public LineSource(ReadIO context, String fileName) {
        super(context);
        this.fileName = fileName;
    }

    @Override
    public Readable<StringLineReadResult, ReaderContext> open(ReadIO context) {
        this.reader = new LocalFileReader(fileName);
        return this.reader;
    }

    @Override
    public StringLineReadResult query(ReaderContext context) {
        return this.reader.query(context);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

}
