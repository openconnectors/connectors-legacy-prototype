/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.streamlio.localfs;

import org.streamlio.heron.TupleToMessageMapper;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Tuple;

public class LineTupleMapper implements TupleToMessageMapper<LineDataMessage> {

    @Override
    public LineDataMessage toMessage(Tuple tuple) {
        return new LineDataMessage(tuple.getLong(0), tuple.getString(1));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("id", "line"));

    }
}
