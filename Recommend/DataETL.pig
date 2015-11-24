rawJsonData = LOAD '/user/cloudera/ecout/*' USING JsonLoader('item:chararray, uuid:chararray, rating:chararray'); 
processData = FILTER rawJsonData BY item IS NOT NULL;
transformData = FOREACH processData GENERATE uuid AS uuid, item AS item, (int) rating AS rating;
STORE transformData INTO '/user/cloudera/ecprocess' using PigStorage(',');
