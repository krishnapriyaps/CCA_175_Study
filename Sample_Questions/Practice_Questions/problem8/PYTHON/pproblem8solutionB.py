from pypark import SparkConf, SparkContext
from pyspark.sql import HiveContext

conf =  SparkConf()
sc = SparkContext(conf=conf)

## Create Hive context object

sql = HiveContext(sc)

## Create new table and Load table : family.SAMPLE_07_2
sql.sql("CREATE TABLE if not exists family.SAMPLE_07_2 (code string, description string,total_emp int, salary int) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','")
sql.sql("LOAD DATA LOCAL INPATH \"/home/cloudera/Study/cca_probalems/problem8/sampledataproblem8_t1.txt\" INTO TABLE family.SAMPLE_07_2")

## Create New table : Union - family.SAMPLE_07_2 and family.SAMPLE_08 (already created in hive)
sql.sql("create table family.Employee100K2_2 AS select * from (select code,description,total_emp,salary from family.SAMPLE_07 where salary >= 100000 UNION ALL  select code,description,total_emp,salary from family.SAMPLE_08 where salary >= 100000) T")
