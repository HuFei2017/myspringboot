package com.learning.datasource.ddl;

import com.learning.schema.CassColumnInfo;
import com.learning.schema.KeyMap;

import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class TableCQL {

    private StringBuilder stringbuilder = new StringBuilder();
    public StringBuilder getStringbuilder() {
        return stringbuilder;
    }

    public TableCQL addCreate(String databaseName, String tableName){
        String cql = "CREATE TABLE IF NOT EXISTS ";
        cql += databaseName+"."+tableName+"(";
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addColumn(List<CassColumnInfo> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0){
            for(CassColumnInfo info : list){
                cql.append("\n ").append(info.getColumn_name()).append(" ").append(info.getColumn_type());
                if(info.isStatic())
                    cql.append(" STATIC");
                if(info.isPrimary())
                    cql.append(" PRIMARY KEY");
                cql.append(",");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1));
        }
        stringbuilder.append(cql);
        return this;
    }

    /**
     * primary_key            ::=  partition_key [ ',' clustering_columns ]
     * partition_key          ::=  column_name | '(' column_name ( ',' column_name )* ')'
     * clustering_columns     ::=  column_name ( ',' column_name )*
     * 说明:前者是分区(可以有区无桶),后者是分桶,最多嵌套两层小括号:（（XX,XX,···）,XX,XX,···）
     */
    public TableCQL addPrimary(List<List<String>> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0){
            cql.append("\n , PRIMARY KEY ");
            if(list.size() == 1) {
                if(list.get(0).size() == 1)
                    cql.append(list.get(0).get(0));
                else{
                    cql.append("(");
                    for (String str : list.get(0)) {
                        cql.append(str).append(",");
                    }
                    cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")");
                }
            }
            else {
                cql.append("(");
                for(List<String> li : list){
                    if(li.size() == 1)
                        cql.append(li.get(0));
                    else{
                        cql.append("(");
                        for (String str : li) {
                            cql.append(str).append(",");
                        }
                        cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")");
                    }
                    cql.append(",");
                }
                cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")");
            }
        }
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addRightBracket(){
        String cql = "\n )";
        stringbuilder.append(cql);
        return this;
    }

    /**
     * optionList的说明
     * option	                    kind	    default	        description
     * comment	                    simple	    none	        A free-form, human-readable comment.
     * read_repair_chance	        simple	    0.1	            The probability with which to query extra nodes
     *                                                          (e.g. more nodes than required by the consistency level) for the purpose of read repairs.
     * dclocal_read_repair_chance	simple	    0	            The probability with which to query extra nodes
     *                                                          (e.g. more nodes than required by the consistency level) belonging to the same data center
     *                                                          than the read coordinator for the purpose of read repairs.
     * gc_grace_seconds	            simple      864000	        Time to wait before garbage collecting tombstones (deletion markers).
     * bloom_filter_fp_chance	    simple	    0.00075	        The target probability of false positive of the sstable bloom filters.
     *                                                          Said bloom filters will be sized to provide the provided probability
     *                                                          (thus lowering this value impact the size of bloom filters in-memory and on-disk)
     * default_time_to_live	        simple	    0	            The default expiration time (“TTL”) in seconds for a table.
     * compaction	                map	        see below	    Compaction options.
     * compression	                map	        see below	    Compression options.
     * caching	                    map	        see below	    Caching options.
     * The compaction options must at least define the 'class' sub-option, that defines the compaction strategy class to use. The default supported class are 'SizeTieredCompactionStrategy' (STCS),
     * 'LeveledCompactionStrategy' (LCS) and 'TimeWindowCompactionStrategy' (TWCS) (the 'DateTieredCompactionStrategy' is also supported but is deprecated and 'TimeWindowCompactionStrategy'
     * should be preferred instead).Custom strategy can be provided by specifying the full class name as a string constant.
     * compression属性说明
     * Option	            Default	        Description
     * class	            LZ4Compressor	The compression algorithm to use. Default compressor are: LZ4Compressor, SnappyCompressor and
     *                                      DeflateCompressor. Use 'enabled' : false to disable compression. Custom compressor can be provided by
     *                                      specifying the full class name as a “string constant”:#constants.
     * enabled	            true	        Enable/disable sstable compression.
     * chunk_length_in_kb	64KB	        On disk SSTables are compressed by block (to allow random reads). This defines the size (in KB) of said block.
     *                                      Bigger values may improve the compression rate, but increases the minimum size of data to be read from disk for a read
     * crc_check_chance	    1.0	            When compression is enabled, each compressed block includes a checksum of that block for the purpose of
     *                                      detecting disk bitrot and avoiding the propagation of corruption to other replica. This option defines the probability
     *                                      with which those checksums are checked during read. By default they are always checked. Set to 0 to disable
     *                                      checksum checking and to 0.5 for instance to check them every other read |
     *caching属性说明
     * Option	            Default	    Description
     * keys	                ALL	        Whether to cache keys (“key cache”) for this table. Valid values are: ALL and NONE.
     * rows_per_partition	NONE	    The amount of rows to cache per partition (“row cache”). If an integer n is specified, the first n queried rows of a partition
     *                                  will be cached. Other possible options are ALL, to cache all rows of a queried partition, or NONE to disable row caching.
     */
    public TableCQL addOptions(boolean isStorage, List<KeyMap> orderList, List<KeyMap> optionList){
        StringBuilder cql = new StringBuilder();
        if(isStorage || orderList.size()>0 || optionList.size()>0) {
            cql.append(" WITH");
            if (isStorage)
                cql.append(" COMPACT STORAGE\n AND");
            if (orderList.size() > 0) {
                cql.append(" CLUSTERING ORDER BY (");
                for (KeyMap order : orderList) {
                    cql.append(order.getName()).append(" ").append(order.getValue()).append(",");
                }
                cql = new StringBuilder(cql.substring(0, cql.length() - 1) + ")\n AND");
            }
            if (optionList.size() > 0) {
                cql.append("");
                for (KeyMap option : optionList) {
                    cql.append(" ").append(option.getName()).append("=").append(option.getValue()).append("\n AND");
                }
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 4));
        }
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addAlter(String databaseName, String tableName){
        String cql = "ALTER TABLE "+databaseName+"."+tableName;
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addAlterADD(List<KeyMap> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0) {
            cql.append(" ADD ");
            for (KeyMap km : list){
                cql.append(km.getName()).append(" ").append(km.getValue()).append(",");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1));
        }
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addAlterDROP(List<String> list){
        StringBuilder cql = new StringBuilder();
        if(list.size()>0) {
            cql.append(" DROP ");
            for (String s : list){
                cql.append(s).append(" ");
            }
            cql = new StringBuilder(cql.substring(0, cql.length() - 1));
        }
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addDrop(String databaseName, String tableName){
        String cql = "DROP TABLE IF EXISTS "+databaseName+"."+tableName;
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addTruncate(String databaseName, String tableName){
        String cql = "TRUNCATE TABLE "+databaseName+"."+tableName;
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addCreateIndex(boolean isCustom, String indexName, String databaseName, String tableName, String identifier, String columnName){
        String temp = identifier.toUpperCase().equals("KEYS")?"KEYS":
                identifier.toUpperCase().equals("VALUES")?"VALUES":
                        identifier.toUpperCase().equals("ENTRIES")?"ENTRIES":
                                identifier.toUpperCase().equals("FULL")?"FULL":"";
        String cql="CREATE "+(isCustom?"CUSTOM ":" ")+"INDEX IF NOT EXISTS "+indexName+
                "\n ON "+databaseName+"."+tableName+" ("+
                (isNullOrEmpty(temp)?columnName:(temp+"("+columnName+")"))+")";
        stringbuilder.append(cql);
        return this;
    }

    /**
     * eg:USING 'path.to.the.IndexClass' WITH OPTIONS = {'storage': '/mnt/ssd/indexes/'}
     */
    public TableCQL addIndexOptions(String className, String option){
        String cql = "";
        if(!isNullOrEmpty(className)){
            cql += "\n USING '"+className+"'";
            if(!isNullOrEmpty(option))
                cql += " WITH OPTIONS = "+option;
        }
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addDropIndex(String indexName){
        String cql = "DROP INDEX IF EXISTS "+indexName;
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL addTrigger(String triggerName, String databaseName, String tableName){
        String cql = "CREATE TRIGGER IF NOT EXISTS "+triggerName+"\n ON "+databaseName+"."+tableName;
        stringbuilder.append(cql);
        return this;
    }

    public TableCQL dropTrigger(String triggerName, String databaseName, String tableName){
        String cql = "DROP TRIGGER IF EXISTS "+triggerName+"\n ON "+databaseName+"."+tableName;
        stringbuilder.append(cql);
        return this;
    }

}
