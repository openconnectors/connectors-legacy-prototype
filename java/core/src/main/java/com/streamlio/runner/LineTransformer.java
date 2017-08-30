package com.streamlio.runner;

import com.streamlio.localfilesystem.LineWriterContext;
import com.streamlio.localfilesystem.StringLineReadResult;

import java.io.IOException;

public class LineTransformer extends Mapper<StringLineReadResult,LineWriterContext> {

    @Override
    public void setup() {

    }

    @Override
    public LineWriterContext transform(StringLineReadResult readResult) {
        return new LineWriterContext(readResult.getData());
    }

    @Override
    public void close() throws IOException {

    }
}
