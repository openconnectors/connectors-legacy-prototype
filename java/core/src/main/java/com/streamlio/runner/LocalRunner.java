//package com.streamlio.runner;
//
//import com.streamlio.localfs.*;
//
//import java.io.IOException;
//
//public class LocalRunner extends Runner<LineFSSource,LineTransformer,LocalFSSink>{
//
//    private String sourceFileName;
//    private String targetFileName;
//
//    public LocalRunner(String sourceFileName, String targetFileName){
//        this.sourceFileName = sourceFileName;
//        this.targetFileName = targetFileName;
//    }
//
//    @Override
//    public void setup() {
//        this.source = new LineFSSource(null, sourceFileName);
//        this.sink = new LocalFSSink(null, targetFileName);
//        this.source.open(null);
//        this.sink.open(null);
//        this.mapper = new LineTransformer();
//    }
//
//    @Override
//    public void start() {
//        while(source.isOpen() && sink.isOpen()){
//            sink.write(mapper.transform(source.query(null)));
//        }
//    }
//
//    @Override
//    public void close() throws IOException {
//        this.source.close();
//        this.sink.close();
//    }
//
//    public static void main(String[] args) throws IOException {
//
//        LocalRunner lr = new LocalRunner(
//                "/Users/a.ahmed/Desktop/test.txt",
//                "/Users/a.ahmed/Desktop/test_out.txt");
//
//        lr.setup();
//        lr.start();
//        lr.close();
//    }
//
//
//}
