///**
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, LocalFSConVer 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied.  See the License for the
// * specific language governing permissions and limitations
// * under the License.
// */
//package com.streamlio.impl.fs;
//
//import com.streamlio.connector.SinkConnector;
//import com.streamlio.message.BaseMessage;
//import com.streamlio.common.io.util.IoUtils;
//import com.streamlio.common.util.Bytes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Properties;
//
///**
// * Write files in the following format /base-path/{date}/output-{time}
// */
//public class FileSinkConnector<T extends BaseMessage> extends SinkConnector<T> {
//
//    private static final Logger LOG = LoggerFactory.getLogger(FileSinkConnector.class);
//
//    private static final String KEY_BASE_PATH = "basepath";
//
//    private static final String DEFAULT_OUTPUT_FILE_PREFIX = "output";
//    private static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
//    private static final String DEFAULT_TIME_FORMAT = "HH-mm-ss";
//
//    private final DateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
//    private final DateFormat timeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
//
//    private String basePath;
//    private Writer writer;
//    private long bytesWritten = 0;
//    private String fileUri;
//
//    @Override
//    public void initialize(Properties properties) {
//        basePath = properties.getProperty(KEY_BASE_PATH);
//    }
//
//    @Override
//    public void processMessage(T message) throws IOException {
//        Writer writer = getWriterAndOpenIfNecessary();
//        writer.write(message);
//        bytesWritten += message.getData().length;
//    }
//
//    @Override
//    public void commit() throws Exception {
//        commitAndReset();
//    }
//
//    @Override
//    public void close() {
//        try {
//            commitAndReset();
//        } catch (IOException e) {
//            LOG.warn("failed to commit file when closing", e);
//        }
//    }
//
//    private String createFileUri() {
//        final String base = basePath.endsWith("/") ? basePath : basePath + "/";
//        final Date date = new Date();
//        return base + dateFormat.format(date) + "/" +
//                DEFAULT_OUTPUT_FILE_PREFIX + "-" +
//                timeFormat.format(date);
//    }
//
//    private Writer getWriterAndOpenIfNecessary() throws IOException {
//        if (writer == null) {
//            writer = new BytesWriter();
//            fileUri = createFileUri();
//            LOG.info("opening file {}", fileUri);
//            writer.open(createFileUri());
//        }
//
//        return writer;
//    }
//
//    private void commitAndReset() throws IOException {
//        if (writer != null) {
//            writer.flush();
//            IoUtils.close(writer);
//        }
//        if (fileUri != null) {
//            LOG.info("file {} committed size {} MB", fileUri, Bytes.toMb(bytesWritten));
//        }
//        writer = null;
//        bytesWritten = 0;
//        fileUri = null;
//    }
//}
