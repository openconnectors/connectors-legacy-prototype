package com.streamlio.io;

public class StringLineReadResult implements ReadResult {

    private long lineNum;
    private String data;

    public StringLineReadResult(long lineNum, String data) {
        this.lineNum = lineNum;
        this.data = data;
    }

    public long getLineNum() {
        return lineNum;
    }

    public void setLineNum(long lineNum) {
        this.lineNum = lineNum;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StringLineReadResult{" +
                "lineNum=" + lineNum +
                ", data='" + data + '\'' +
                '}';
    }
}
