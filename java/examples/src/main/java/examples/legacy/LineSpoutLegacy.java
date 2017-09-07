package examples.legacy;


public class LineSpoutLegacy extends SourceSpoutAdapterLegacy {





}




//public class LineSpoutLegacy extends BaseRichSpout {
//    private static final Logger LOG = LoggerFactory.getLogger(LineSpoutLegacy.class);
//    private String fileName;
//    private SpoutOutputCollector _collector;
//    private BufferedReader reader;
//    private AtomicLong linesRead;
//
//    /**
//     * Prepare the spout. This method is called once when the topology is submitted
//     * @param conf
//     * @param context
//     * @param collector
//     */
//    @Override
//    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
//        linesRead = new AtomicLong(0);
//        _collector = collector;
//        try {
//            fileName= (String) conf.get("linespout.file");
//            reader = new BufferedReader(new FileReader(fileName));
//            // read and ignore the header if one exists
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void deactivate() {
//        try {
//            reader.close();
//        } catch (IOException e) {
//            LOG.warn("Problem closing file");
//        }
//    }
//
//    /**
//     * Storm will call this method repeatedly to pull tuples from the spout
//     */
//    @Override
//    public void nextTuple() {
//        try {
//            String line = reader.readLine();
//            if (line != null) {
//                long id = linesRead.incrementAndGet();
//                _collector.emit(new Values(line), id);
//            } else {
//                System.out.println("Finished reading file, " + linesRead.get() + " lines read");
//                Thread.sleep(10000);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Storm will call this method when tuples are acked
//     * @param id
//     */
//    @Override
//    public void ack(Object id) {
//    }
//
//    /**
//     * Storm will call this method when tuples fail to process downstream
//     * @param id
//     */
//    @Override
//    public void fail(Object id) {
//        System.err.println("Failed line number " + id);
//    }
//
//    /**
//     * Tell storm which fields are emitted by the spout
//     * @param declarer
//     */
//    @Override
//    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        // read csv header to get field info
//        declarer.declare(new Fields("line"));
//    }

//}