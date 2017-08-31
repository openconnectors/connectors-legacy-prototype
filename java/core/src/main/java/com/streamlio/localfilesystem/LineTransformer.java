package com.streamlio.localfilesystem;

import com.streamlio.runner.Mapper;

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
