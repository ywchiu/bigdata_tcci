recommend_result = LOAD 'small_recommend/part-*' USING PigStorage() AS (userid:int, recommend:chararray);
STORE recommend_result INTO 'mydata' USING org.apache.pig.backend.hadoop.hbase.HBaseStorage('mycf:recommend_list');
