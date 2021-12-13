package com.learning.datasource.controller;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.learning.datasource.configuration.CassandraPool;
import com.learning.publics.ConnectionHelper;
import com.learning.schema.input.AutoChartInput;
import com.learning.schema.input.AutoCtlInput;
import com.learning.schema.input.AutoCtlSimpInput;
import com.learning.schema.output.AutoCtlDetail;
import com.learning.schema.output.AutoCtrl;
import com.learning.schema.output.AutoItem;
import com.learning.schema.output.AutoTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.parquet.Strings.isNullOrEmpty;

@RestController
public class AutoCtlController {

    @Autowired
    HttpServletRequest request;
    CassandraPool pool = CassandraPool.getInstance();

    private Connection connectPostgresql(){
        return new ConnectionHelper().connectPostgresql();
    }

    @PostMapping("/datahouse/instruments/rate/home")
    public AutoCtrl getHome(@RequestBody AutoChartInput input) {
        Session session = pool.getSession();
        Connection conn = connectPostgresql();
        AutoCtrl result = new AutoCtrl();
        List<AutoCtlDetail> res = new ArrayList<>();
        int type = input.getType();
        String name = input.getName();
        try (Statement stmt = conn.createStatement()) {
            ArrayList<String> xList = new ArrayList<>();
            ArrayList<String> tmp = new ArrayList<>();
            ArrayList<Float> autoCtlList = new ArrayList<>();
            ArrayList<Float> autoCountList = new ArrayList<>();
            ArrayList<Float> uncountList = new ArrayList<>();
            ArrayList<Float> countList = new ArrayList<>();
            Map map = new HashMap<>();
            Map mp = new HashMap();
            String typeName = (type==0?"自控":"平稳");
            String sql = "";
            short level = 1;
            if(name.equals("host")) {
                sql = "select a.sourceid as sourceid,a.indextype as indextype,b.name as name from sta_index a join com_asset b on a.assetid=b.id where b.code='zikong' and (b.name like '%京博%' or b.type = '102')";
                level = 0;
            }else if(name.equals("京博石化"))
                sql = "select n.sourceid as sourceid,n.indextype as indextype,m.name as name from (" +
                        "select * from com_asset where id in (" +
                        "select k.cid from (" +
                        "select d.cid from (" +
                        "select cid from com_asset a join com_assetrela b on a.id = b.pid where code='zikong' and name='"+name+"' " +
                        ") c join com_assetrela d on c.cid=d.pid" +
                        ") t join com_assetrela k on t.cid = k.pid)" +
                        ") m join sta_index n on m.id=n.assetid";
            else
                sql = "select n.sourceid as sourceid,n.indextype as indextype,m.name as name from (" +
                        "select * from com_asset where id in (" +
                        "select k.cid from (" +
                        "select cid from com_asset a join com_assetrela b on a.id = b.pid where code='zikong' and name='"+name+"'" +
                        ") t join com_assetrela k on t.cid = k.pid)" +
                        ") m join sta_index n on m.id=n.assetid";
            ResultSet ret = stmt.executeQuery(sql);
            while (ret.next()) {
                tmp.add(ret.getString("sourceid"));
                map.put(ret.getString("sourceid"),ret.getString("name"));
                mp.put(ret.getString("sourceid"),ret.getString("indextype"));
            }
            sql = "select * from rzdata.dat_tsdatas where assetid ="+tmp.get(0)+" limit 1";
            com.datastax.driver.core.ResultSet rs = session.execute(sql);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(rs.one().getObject(1).toString()));
            sql = "select * from rzdata.dat_tsdatas where assetid in (";
            for(String assetid : tmp) {
                sql +=  assetid + ",";
            }
            sql = sql.substring(0,sql.length()-1)+") and measdate='"+time+"'";
            rs = session.execute(sql);
            for(Row row:rs){
                switch (mp.get(row.getObject(0).toString()).toString().replaceAll(typeName, "")) {
                    case ("率"): {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setType(typeName + "率");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        res.add(ac);
                        break;
                    }
                    case "数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setType(typeName + "数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        res.add(ac);
                        break;
                    }
                    case "回路总数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setType(typeName + "回路总数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        res.add(ac);
                        break;
                    }
                    case "不参与计算数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setType(typeName + "不参与计算数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        res.add(ac);
                        break;
                    }
                }
            }
            //调整输出顺序
            int count = 0;
            if(res.size() % 4 == 0)
                count = res.size()/4 ;
            else
                count = res.size()/4 + 3;
            AutoItem[] item = new AutoItem[count];
            for(int i=0;i<count;i++){
                item[i] = new AutoItem();
            }
            for(AutoCtlDetail detail : res){
                if(!xList.contains(detail.getName()))
                    xList.add(detail.getName());
                item[xList.indexOf(detail.getName())].setName(detail.getName());
                switch (detail.getType().replaceAll(typeName,"")){
                    case "率":{
                        item[xList.indexOf(detail.getName())].setRate(detail.getValue());
                        break;
                    }
                    case "数":{
                        item[xList.indexOf(detail.getName())].setNum(detail.getValue());
                        break;
                    }
                    case "回路总数":{
                        item[xList.indexOf(detail.getName())].setCount(detail.getValue());
                        break;
                    }
                    case "不参与计算数":{
                        item[xList.indexOf(detail.getName())].setUncount(detail.getValue());
                        break;
                    }
                }
            }
            Arrays.sort(item, Comparator.comparing(AutoItem::getRate).reversed());
            xList.clear();
            for(int i=0;i<item.length;i++){
                if(item[i].getName().equals("京博石化")) {
                    xList.add(item[i].getName());
                    autoCtlList.add(item[i].getRate());
                    autoCountList.add(item[i].getNum());
                    uncountList.add(item[i].getUncount());
                    countList.add(item[i].getCount() + item[i].getUncount());
                    break;
                }
            }
            for(int i=0;i<item.length;i++){
                if(!item[i].getName().equals("京博石化")) {
                    xList.add(item[i].getName());
                    autoCtlList.add(item[i].getRate());
                    autoCountList.add(item[i].getNum());
                    uncountList.add(item[i].getUncount());
                    countList.add(item[i].getCount() + item[i].getUncount());
                }
            }
            result.setLevel(level);
            result.setxList(xList);
            result.setAutoCtlList(autoCtlList);
            result.setAutoCountList(autoCountList);
            result.setCountList(countList);
            result.setUncountList(uncountList);
            stmt.close();
            return result;
        }catch (Exception ex){
            return new AutoCtrl();
        }finally {
            try {
                if(conn != null)
                    conn.close();
                if(session != null)
                    session.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

    @PostMapping("/datahouse/instruments/rateAll/detail/{type}")
    public AutoTable getAllDetails(@RequestBody AutoCtlSimpInput input, @PathVariable("type") int type){
        Session session = pool.getSession();
        Connection conn = connectPostgresql();
        AutoTable result = new AutoTable();
        try (Statement stmt = conn.createStatement()) {
            String typeName = (type==0?"自控":"平稳");
            List<String> idList = new ArrayList<>();
            List<String> idListTmp = new ArrayList<>();
            List<AutoCtlDetail> res = new ArrayList<>();
            List<AutoCtlDetail> res2 = new ArrayList<>();
            idList.add(input.getAssetid());
            idListTmp.add(input.getAssetid());
            Map map = new HashMap<>();
            Map mp = new HashMap();
            Map sp = new HashMap();
            Map tp = new HashMap();
            int num = 0;
            int seq = 0;
            String sql = "";
            sp.put(input.getAssetid(),seq++);
            while (true) {
                sql = "select b.* from com_assetrela a join com_asset b on a.cid=b.id where a.pid in (";
                for (String s : idListTmp) {
                    sql += "'" + s + "',";
                }
                sql = sql.substring(0, sql.length() - 1) + ")";
                ResultSet rs = stmt.executeQuery(sql);
                idListTmp.clear();
                while (rs.next()) {
                    num++;
                    idList.add(rs.getObject(1).toString());
                    idListTmp.add(rs.getObject(1).toString());
                    sp.put(rs.getObject(1).toString(),seq);
                }
                seq++;
                if(num>0)
                    num = 0;
                else
                    break;

            }
            sql = "select a.sourceid as sourceid,a.indextype as indextype,b.name as name,b.id as assetid from sta_index a join com_asset b on a.assetid=b.id where a.assetid in (";
            for (String s : idList) {
                sql += "'" + s + "',";
            }
            sql = sql.substring(0, sql.length() - 1) + ") and indextype like '"+typeName+"%'";
            ResultSet ret = stmt.executeQuery(sql);
            idListTmp.clear();
            while (ret.next()) {
                idListTmp.add(ret.getString("sourceid"));
                map.put(ret.getString("sourceid"),ret.getString("name"));
                mp.put(ret.getString("sourceid"),ret.getString("indextype"));
                tp.put(ret.getString("sourceid"),ret.getString("assetid"));
            }
            sql = "select * from rzdata.dat_tsdatas where assetid in (";
            for(String assetid : idListTmp) {
                sql +=  assetid + ",";
            }
            sql = sql.substring(0,sql.length()-1)+")  limit 1";
            com.datastax.driver.core.ResultSet rs = session.execute(sql);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(rs.one().getObject(1).toString()));
            String sqlTitle = "select count(*)";
            sql = " from rzdata.dat_tsdatas where assetid in (";
            for(String assetid : idListTmp) {
                sql +=  assetid + ",";
            }
            sql = sql.substring(0,sql.length()-1)+") and measdate='"+time+"' ALLOW FILTERING";
            rs = session.execute(sqlTitle+sql);
            int count = Integer.parseInt(rs.one().getObject(0).toString());
            sqlTitle = "select *";
            rs = session.execute(sqlTitle+sql);
            for(Row row:rs){
                switch (mp.get(row.getObject(0).toString()).toString().replaceAll(typeName, "")) {
                    case ("率"): {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setCalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject(1).toString())));
                        ac.setType(typeName + "率");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        ac.setSeq(Short.parseShort(sp.get(tp.get(row.getObject(0).toString())).toString()));
                        res.add(ac);
                        break;
                    }
                    case "数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setCalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject(1).toString())));
                        ac.setType(typeName + "数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        ac.setSeq(Short.parseShort(sp.get(tp.get(row.getObject(0).toString())).toString()));
                        res.add(ac);
                        break;
                    }
                    case "回路总数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setCalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject(1).toString())));
                        ac.setType(typeName + "回路总数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        ac.setSeq(Short.parseShort(sp.get(tp.get(row.getObject(0).toString())).toString()));
                        res.add(ac);
                        break;
                    }
                    case "不参与计算数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setCalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject(1).toString())));
                        ac.setType(typeName + "不参与计算数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        ac.setSeq(Short.parseShort(sp.get(tp.get(row.getObject(0).toString())).toString()));
                        res.add(ac);
                        break;
                    }
                }
            }
            res.sort(Comparator.comparing(AutoCtlDetail::getSeq).thenComparing(AutoCtlDetail::getName));
            num = 0;
            while(true) {
                if (num >= input.getPageIndex() * input.getPageSize()*4 || num >= res.size())
                    break;
                else if (num < input.getPageIndex() * input.getPageSize()*4 && num >= (input.getPageIndex() - 1) * input.getPageSize()*4) {
                    res2.add(res.get(num));
                }
                num++;
            }
            result.setAutoCtlDetail(res2);
            result.setCount(count/4);
            stmt.close();
            return result;
        }catch (Exception ex){
            return new AutoTable();
        }finally {
            try {
                if(conn != null)
                    conn.close();
                if(session != null)
                    session.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }

    @PostMapping("/datahouse/instruments/rate/detail/{type}")
    public AutoTable getDetails(@RequestBody AutoCtlInput input, @PathVariable("type") int type) {
        Session session = pool.getSession();
        Connection conn = connectPostgresql();
        AutoTable result = new AutoTable();
        try(Statement stmt = conn.createStatement()) {
            String typeName = (type==0?"自控":"平稳");
            List<AutoCtlDetail> res = new ArrayList<>();
            List<AutoCtlDetail> res2 = new ArrayList<>();
            ArrayList<String> tmp = new ArrayList<>();
            Map map = new HashMap<>();
            Map mp = new HashMap();
            String sql = "select a.sourceid as sourceid,a.indextype as indextype,b.name as name from sta_index a join com_asset b on a.assetid=b.id where a.assetid ='"+input.getAssetid()+"' and indextype like '"+typeName+"%'";
            ResultSet ret = stmt.executeQuery(sql);
            while (ret.next()) {
                tmp.add(ret.getString("sourceid"));
                map.put(ret.getString("sourceid"),ret.getString("name"));
                mp.put(ret.getString("sourceid"),ret.getString("indextype"));
            }
            if(tmp.size()<=0) {
                result.setAutoCtlDetail(res2);
                result.setCount(0);
                stmt.close();
                return result;
            }
            String sqlTitle = "select count(*)";
            sql  = " from rzdata.dat_tsdatas where assetid in (";
            for(String assetid : tmp) {
                sql +=  assetid + ",";
            }
            sql = sql.substring(0,sql.length()-1)+") and measdate>'"+input.getsTime()+"' and measdate <='"+input.geteTime()+"'";
            if(!isNullOrEmpty(input.getOption())&&!input.getOption().equals(""))
                sql += " and measvalue"+input.getOption();
            sql += " ALLOW FILTERING";
            com.datastax.driver.core.ResultSet rs = session.execute(sqlTitle+sql);
            int num = Integer.parseInt(rs.one().getObject(0).toString());
            sqlTitle = "select *";
            rs = session.execute(sqlTitle+sql);
            int i = 0;
            for(Row row:rs){
                switch (mp.get(row.getObject(0).toString()).toString().replaceAll(typeName, "")) {
                    case ("率"): {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setCalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject(1).toString())));
                        ac.setType(typeName + "率");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        res.add(ac);
                        break;
                    }
                    case "数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setCalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject(1).toString())));
                        ac.setType(typeName + "数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        res.add(ac);
                        break;
                    }
                    case "回路总数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setCalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject(1).toString())));
                        ac.setType(typeName + "回路总数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        res.add(ac);
                        break;
                    }
                    case "不参与计算数": {
                        AutoCtlDetail ac = new AutoCtlDetail();
                        ac.setName(map.get(row.getObject(0).toString()).toString());
                        ac.setCalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(row.getObject(1).toString())));
                        ac.setType(typeName + "不参与计算数");
                        ac.setValue(Float.parseFloat(row.getObject(2).toString()));
                        res.add(ac);
                        break;
                    }
                }
            }
            //倒序排一遍
            res.sort(Comparator.comparing(AutoCtlDetail::getCalTime).reversed());
            while(true) {
                if (i >= input.getPageIndex() * input.getPageSize()*4 || i>=res.size())
                    break;
                if (i < input.getPageIndex() * input.getPageSize()*4 && i >= (input.getPageIndex() - 1) * input.getPageSize()*4) {
                    res2.add(res.get(i));
                }
                i++;
            }
            result.setAutoCtlDetail(res2);
            result.setCount(num/4);
            stmt.close();
            return result;
        }catch (Exception ex){
            return new AutoTable();
        }finally{
            try {
                if(conn != null)
                    conn.close();
                if(session != null)
                    session.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }
}
