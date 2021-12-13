package com.learning.datasource.controller;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.learning.datasource.configuration.CassandraPool;
import com.learning.datasource.ddl.DatabaseCassandra;
import com.learning.datasource.ddl.TableCassandra;
import com.learning.datasource.dml.CassManipulation;
import com.learning.schema.CassTableInfo;
import com.learning.schema.KeyMap;
import com.learning.schema.input.*;
import com.learning.schema.output.DDLOperation;
import com.learning.schema.output.DMLOperation;
import com.learning.schema.output.SoftRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class CassandraController {

    @Autowired
    HttpServletRequest request;
    CassandraPool pool = CassandraPool.getInstance();

    //数据库操作
    @GetMapping("/datahouse/cassandra/keyspace/create/{databaseName}")
    public DDLOperation createKeyspace(@PathVariable("databaseName") String databaseName){
        Session session = pool.getSession();
        try{
            List<KeyMap> list = new ArrayList<>();
            KeyMap keymap = new KeyMap();
            keymap.setName("replication");
            keymap.setValue("{'class': 'SimpleStrategy', 'replication_factor' : 1}");
            list.add(keymap);
            return new DatabaseCassandra().createDatabase(session,databaseName,list);
        }finally {
            if(session != null)
                session.close();
        }
    }

    @GetMapping("/datahouse/cassandra/keyspaces/describe")
    public List<CassTableInfo> descKeyspaces(){
        Session session = pool.getSession();
        try{
            return new DatabaseCassandra().descDatabase(session);
        }finally {
            if(session != null)
                session.close();
        }
    }

    @PostMapping("/datahouse/cassandra/keyspace/alter/{databaseName}")
    public DDLOperation alterKeyspace(@PathVariable("databaseName") String databaseName, @RequestBody List<KeyMap> input){
        Session session = pool.getSession();
        try{
            return new DatabaseCassandra().alterDatabase(session,databaseName,input);
        }finally {
            if(session != null)
                session.close();
        }
    }

    @GetMapping("/datahouse/cassandra/keyspace/drop/{databaseName}")
    public DDLOperation dropKeyspace(@PathVariable("databaseName") String databaseName){
        Session session = pool.getSession();
        try{
            return new DatabaseCassandra().dropDatabase(session,databaseName);
        }finally {
            if(session != null)
                session.close();
        }
    }

    //数据表操作
    @PostMapping("/datahouse/cassandra/table/create")
    public DDLOperation createTable(@RequestBody CassTableCreateInput input){
        Session session = pool.getSession();
        try{
            return new TableCassandra().createTable(session,input.getDatabaseName(), input.getTableName(), input.getColumnList(),
                    input.getPrimaryList(), input.isStorage(), input.getOrderList(), input.getOptionList());
        }finally {
            if(session != null)
                session.close();
        }
    }

    @GetMapping("/datahouse/cassandra/table/describe/{databaseName}")
    public List<CassTableInfo> descTables(@PathVariable("databaseName") String databaseName){
        Session session = pool.getSession();
        try{
            return new TableCassandra().descTables(session,databaseName);
        }finally {
            if(session != null)
                session.close();
        }
    }

    @PostMapping("/datahouse/cassandra/table/alter/addColumns")
    public DDLOperation alterTableADD(@RequestBody TableAltering input){
        Session session = pool.getSession();
        try{
            return new TableCassandra().alterTableADD(session,input.getDatabaseName(), input.getTableName(), input.getColumnList());
        }finally {
            if(session != null)
                session.close();
        }
    }

    @PostMapping("/datahouse/cassandra/table/alter/dropColumns")
    public DDLOperation alterTableDROP(@RequestBody TableAltering input){
        Session session = pool.getSession();
        try{
            return new TableCassandra().alterTableDROP(session,input.getDatabaseName(), input.getTableName(), input.getDropColumnList());
        }finally {
            if(session != null)
                session.close();
        }
    }

    @PostMapping("/datahouse/cassandra/table/alter/properties")
    public DDLOperation alterTablePro(@RequestBody TableAltering input){
        Session session = pool.getSession();
        try{
            return new TableCassandra().alterTablePro(session,input.getDatabaseName(), input.getTableName(), input.getColumnList());
        }finally {
            if(session != null)
                session.close();
        }
    }

    @GetMapping("/datahouse/cassandra/table/drop/{databaseName}/{tableName}")
    public DDLOperation dropTable(@PathVariable("databaseName") String databaseName, @PathVariable("tableName") String tableName){
        Session session = pool.getSession();
        try{
            return new TableCassandra().dropTable(session,databaseName, tableName);
        }finally {
            if(session != null)
                session.close();
        }
    }

    @GetMapping("/datahouse/cassandra/table/truncate/{databaseName}/{tableName}")
    public DDLOperation truncateTable(@PathVariable("databaseName") String databaseName, @PathVariable("tableName") String tableName){
        Session session = pool.getSession();
        try{
            return new TableCassandra().truncateTable(session,databaseName, tableName);
        }finally {
            if(session != null)
                session.close();
        }
    }

    @PostMapping("/datahouse/cassandra/getQueryResult")
    public DMLOperation getQueryResult(@RequestBody QueryResultInput input) {
        Session session = pool.getSession();
        CassManipulation operation = new CassManipulation();
        DMLOperation result = new DMLOperation();
        String databaseName = input.getDatabaseName().contains(".")?input.getDatabaseName().substring(0,input.getDatabaseName().indexOf(".")):input.getDatabaseName();
        String[] hqlstring = input.getHql().replaceAll("[\r\n]", "").split(";");
        try {
            long consuming = 0;
            for (int i=0;i<hqlstring.length;i++) {
                String sql = hqlstring[i];
                if(i== hqlstring.length-1){
                    result = operation.executeQuery(session,databaseName,sql,input.getPageIndex(),input.getPageSize(),true);
                    consuming += result.getResultSet().getTimeConsuming();
                    result.getResultSet().setTimeConsuming(consuming);
                }
                else {
                    result = operation.executeQuery(session, databaseName, sql, input.getPageIndex(), input.getPageSize(), false);
                    consuming += result.getResultSet().getTimeConsuming();
                }
            }
            return result;
        } finally {
            if(session != null)
                session.close();
        }
    }

    //业务操作
    //获取MES数据
    @PostMapping("/datahouse/cassandra/getvaluesbyid/{reserve}")
    public List<SoftRate> fetDataByids(@RequestBody IndexInput input, @PathVariable("reserve") int reserve) throws ParseException {
        Session session = pool.getSession();
        List<SoftRate> result = new ArrayList<>();
        try{
            List<String> schemaList = new ArrayList<>();
            schemaList.add("measdate");
            schemaList.add("measvalue");
            session.execute("USE rzdata");
            String sql = "select * from dat_tsdatas where assetid in (";
            for(String str : input.getIdList()) {
                sql += str+",";
            }
            sql = sql.substring(0,sql.length()-1)+")";
            if(input.getsTime()!="")
                sql += " and measdate>='" + input.getsTime() + "'";
            if(input.geteTime()!="")
                sql += " and measdate<='" + input.geteTime() + "'";
            sql += " ALLOW FILTERING";
            ResultSet rs = session.execute(sql);
            SoftRate sr = null;
            List<List<Object>> values = new ArrayList<>();
            String isStr = "";
            String afterStr = "";
            for(Row row:rs){
                afterStr = row.getObject("assetid").toString();
                if(!isStr.equals("") && !isStr.equals(afterStr)) {
                    sr.setValues(values);
                    sr.setCount(values.size());
                    result.add(sr);
                    values = new ArrayList<>();
                }
                if(!isStr.equals(afterStr)) {
                    sr = new SoftRate();
                    sr.setId(afterStr);
                    sr.setSchema(schemaList);
                }
                isStr = afterStr;
                List<Object> value = new ArrayList<>();
                value.add(DELETE8(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject("measdate").toString()))));
                value.add(decimalTo(row.getObject("measvalue"),reserve));
                values.add(value);
            }
            if(sr == null)
                sr = new SoftRate();
            sr.setValues(values);
            result.add(sr);
            return result;
        } finally {
            if(session != null)
                session.close();
        }
    }

    //获取lims数据
    @PostMapping("/datahouse/cassandra/getvalues/{reserve}")
    public List<SoftRate> getDataBySampleID(@RequestBody IndexInput input, @PathVariable("reserve") int reserve) throws ParseException {
        Session session = pool.getSession();
        List<SoftRate> result = new ArrayList<>();
        try{
            List<String> schemaList = new ArrayList<>();
            schemaList.add("measdate");
            schemaList.add("measvalue");
            schemaList.add("oiltype");
            schemaList.add("material");
            schemaList.add("descomment");
            session.execute("USE rzdata");
            String sql = "";
            for(AnalyseItem te : input.getItemList()){
                sql = "select * from dat_limsdatas_detail where assetid="+te.getPointName()+" and itemname='"+te.getItemName()+"' ";
                if(input.getOilType()!=null && input.getOilType()!="")
                    sql += "and sampletype='"+input.getOilType()+"' ";
                if(input.getMaterial()!=null && input.getMaterial()!="")
                    sql += "and material='"+input.getMaterial()+"' ";
                sql += "and measdate>='"+input.getsTime()+"' and measdate<='"+input.geteTime()+"' ALLOW FILTERING";
                ResultSet rs = session.execute(sql);
                SoftRate sr = new SoftRate();
                sr.setId(te.getPointName());
                sr.setItemname(te.getItemName());
                sr.setSchema(schemaList);
                List<List<Object>> values = new ArrayList<>();
                int count = 0;
                for(Row row:rs){
                    if(input.getPageSize()>0) {
                        if (count >= (input.getPageIndex() - 1) * input.getPageSize() && count < input.getPageIndex() * input.getPageSize()) {
                            List<Object> value = new ArrayList<>();
                            value.add(DELETE8(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject("measdate").toString()))));
                            value.add(decimalTo(row.getObject("measvalue"),reserve));
                            value.add(row.getObject("sampletype"));
                            value.add(row.getObject("material"));
                            value.add(row.getObject("descomment"));
                            values.add(value);
                        }
                        count++;
                    }else {
                        List<Object> value = new ArrayList<>();
                        value.add(DELETE8(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject("measdate").toString()))));
                        value.add(decimalTo(row.getObject("measvalue"),reserve));
                        value.add(row.getObject("sampletype"));
                        value.add(row.getObject("material"));
                        value.add(row.getObject("descomment"));
                        values.add(value);
                    }
                }
                sr.setValues(values);
                sr.setCount(count);
                result.add(sr);
            }
            return result;
        } finally {
            if(session != null)
                session.close();
        }
    }

    @PostMapping("/datahouse/cassandra/updatevalues")
    public void updateDescomment(@RequestBody UpdateApplyInput input) throws ParseException {
        Session session = pool.getSession();
        try {
            session.execute("USE rzdata");
            String sql = "update dat_limsdatas_detail set descomment='"+input.getDescomment()+"' where assetid="+input.getItem().getPointName()+" and measdate='"+input.getSampleTime()+"' and itemname='"+input.getItem().getItemName()+"'";
            session.execute(sql);
        }finally {
            if(session != null)
                session.close();
        }
    }

    @PostMapping("/datahouse/cassandra/getMaterials")
    public List<String> getMaterials(@RequestBody KeyMap input){
        Session session = pool.getSession();
        List<String> result = new ArrayList<>();
        try {
            session.execute("USE rzdata");
            String sql = "select * from dat_lims_product_material where point='"+(input.getName().contains("D")?"沥青取样口":input.getName())+"' and oiltype='"+input.getValue()+"' allow filtering";
            ResultSet rs = session.execute(sql);
            for(Row row:rs){
                if(!result.contains(row.getString("materialtype")))
                    result.add(row.getString("materialtype"));
            }
            return result;
        }finally {
            if(session != null)
                session.close();
        }
    }

    @PostMapping("/datahouse/cassandra/getPoints")
    public List<String> getPoints(@RequestBody KeyMap input){
        Session session = pool.getSession();
        List<String> result = new ArrayList<>();
        try {
            session.execute("USE rzdata");
            String sql = "select * from dat_lims_product_material where point='"+(input.getName().contains("D")?"沥青取样口":input.getName())+"' allow filtering";
            ResultSet rs = session.execute(sql);
            for(Row row:rs){
                if(!result.contains(row.getString("oiltype")))
                    result.add(row.getString("oiltype"));
            }
            return result;
        }finally {
            if(session != null)
                session.close();
        }
    }

    private static String DELETE8(String time) {
        try {
            Date d = null;
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sd.parse(time);
            long rightTime = (long) (d.getTime() - 8 * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
            return sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
        }catch (Exception ex){
            return time;
        }
    }

    private String decimalTo(Object num, int reserve){
        if(reserve<=0)
            return num.toString();
        else {
            try {
                float fl = Float.parseFloat(num.toString());
                return Float.toString((float)(Math.round(fl * 10 * reserve))/(10 * reserve));
            } catch (Exception ex) {
                Matcher matcher =  Pattern.compile("[0-9]+(.[0-9]+)?").matcher(num.toString());
                while(matcher.find()){
                    return matcher.group();
                }
            }
            return "0.0";
        }
    }
}
