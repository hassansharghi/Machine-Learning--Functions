package uoit.hassan.CEPMonitor;

import java.util.Map;

import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09;

/**
 * Created by Hassan Sharghi 
 * 24/5/2017
 */
public class CEPMonitor {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        ParameterTool parameterTool = ParameterTool.fromArgs(args);

        // Use ingestion time => TimeCharacteristic == EventTime + IngestionTimeExtractor
        //The job draws checkpoints periodically.
        //In case of a failure, the streaming dataflow will be restarted from the latest completed checkpoint.
        env.enableCheckpointing(1000).
            setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // Input stream of monitoring events
        //https://ci.apache.org/projects/flink/flink-docs-release-1.2/dev/event_timestamp_extractors.html
        DataStream<Measurement> messageStream = env
                .addSource(new FlinkKafkaConsumer09<>(
                                    parameterTool.getRequired("topic"),
                                    new MeasurementDeserializer(),
                                    parameterTool.getProperties()))
                //.assignTimestampsAndWatermarks(new IngestionTimeExtractor<>());
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Measurement>() {                	
                	@Override
        	        public long extractAscendingTimestamp(Measurement element) {
        	            return element.getCreationTime();
                	}
        	});
                	
     

        DataStream<Measurement> partitionedInput = messageStream.keyBy(
                new KeySelector<Measurement, String>() {
                    @Override
                    public String getKey(Measurement value) throws Exception {
                        return value.getUserID();
                    }
        });
       // .timeWindow(Time.seconds(5));
       // .window(ProcessingTimeSessionWindows.withGap(Time.minutes(10)));
        
      //*********** Pattern length 3- proximity ******************************   
        //Query 1: Find behavior of users who perform first "A-1" action, then "A-2" action and next "A-3" action within 10 seconds.
               
                Pattern<Measurement, ?> alarmPattern = Pattern.<Measurement>begin("first") 
                 		.subtype(TempMeasurement.class)
                        .where(evt -> evt.getAction().equals("A-1")  )                          
                        .next("second")
                        .subtype(TempMeasurement.class)
                        .where(evt -> evt.getAction().equals("A-2") ) 
                        .next("third")
                        .subtype(TempMeasurement.class)
                        .where(evt -> evt.getAction().equals("A-3") )    
                        //.within(Time.minutes(4)); 
                		.within(Time.seconds(10));  

                 //Match and Create a pattern stream from alarmPattern
                 PatternStream<Measurement> patternStream = CEP.pattern(partitionedInput, alarmPattern);

                 // Generate warning (capture user and actions) for each matched alarm pattern
                 DataStream<SuspiciousPatternAlarm> alarms = patternStream.select(new PatternSelectFunction<Measurement, SuspiciousPatternAlarm>() {
                     @Override
                     public SuspiciousPatternAlarm select(Map<String, Measurement> pattern) throws Exception {
                     	TempMeasurement first = (TempMeasurement) pattern.get("first");            	
                     	TempMeasurement second = (TempMeasurement) pattern.get("second"); 
                     	TempMeasurement third = (TempMeasurement) pattern.get("third");

                       return new SuspiciousPatternAlarm(first.getUserID() , String.valueOf(first.getID())+ "," + String.valueOf(second.getID()), first.getRes());
                     }
                 });
          //*******************************************************  
      
      
        alarms.map(v -> v.toString()).writeAsText(parameterTool.getRequired("out"), WriteMode.OVERWRITE);
        messageStream.map(v -> v.toString()).print();
     
        env.execute("Flink User Behavior monitoring job");

    }
}
