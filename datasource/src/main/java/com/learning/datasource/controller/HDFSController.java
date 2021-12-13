package com.learning.datasource.controller;

import com.alibaba.fastjson.JSONObject;
import com.learning.datasource.apply.HDFSAPIOperation;
import com.learning.schema.FileFormat;
import com.learning.schema.HDFSOperation;
import com.learning.schema.KeyMap;
import com.learning.schema.input.Changing;
import com.learning.schema.input.HDFSInput;
import com.learning.schema.input.MergeInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

@RestController
public class HDFSController {

    @Autowired
    HttpServletRequest request;

    //HDFS文件操作
    @PostMapping("/datahouse/hdfs/readFile")
    public HDFSOperation readFile(@RequestBody HDFSInput input){
        return new HDFSAPIOperation().readFile(input.getPath(),new ArrayList<>());
    }

    @PostMapping("/datahouse/hdfs/listDirectory/{option}")
    public List<FileFormat> listDirectory(@RequestBody HDFSInput input, @PathVariable("option") String option){
        return new HDFSAPIOperation().listFiles(input.getPath(),(option.equals("-1")||isNullOrEmpty(option))?"":option);
    }

    @PostMapping("/datahouse/hdfs/summdir")
    public HDFSOperation summdir(@RequestBody HDFSInput input){
        return new HDFSAPIOperation().summarDir(input.getPath());
    }

    @PostMapping("/datahouse/hdfs/fileCheck")
    public HDFSOperation fileCheck(@RequestBody HDFSInput input){
        return new HDFSAPIOperation().fileCheck(input.getPath());
    }

    @GetMapping("/datahouse/hdfs/homedir")
    public HDFSOperation homedir(){
        return new HDFSAPIOperation().home();
    }

    @PostMapping("/datahouse/hdfs/mkdir")
    public HDFSOperation mkdir(@RequestBody HDFSInput input){
        List<KeyMap> list = new ArrayList<>();
        KeyMap k = new KeyMap();
        k.setName("destination");
        k.setValue("777");
        list.add(k);
        return new HDFSAPIOperation().mkdir(input.getPath(),list);
    }

    @PostMapping("/datahouse/hdfs/renameOrmove")
    public HDFSOperation renameOrmove(@RequestBody Changing input){
        return new HDFSAPIOperation().rename(input.getPath(),input.getAfter());
    }

    @PostMapping("/datahouse/hdfs/setpermission")
    public HDFSOperation setpermission(@RequestBody Changing input){
        return new HDFSAPIOperation().setPermission(input.getPath(),input.getAfter());
    }

    @PostMapping("/datahouse/hdfs/delete")
    public HDFSOperation delete(@RequestBody Changing input){
        if(input.getAfter().equals("true"))
            return new HDFSAPIOperation().delete(input.getPath(),"true");
        else {
            String userDir = JSONObject.parseObject(homedir().getResult().toString()).toJavaObject(Changing.class).getPath();
            System.out.println(userDir+"/.Trash/" + input.getPath().substring(input.getPath().lastIndexOf("/") + 1));
            return new HDFSAPIOperation().rename(input.getPath(), userDir+"/.Trash/" + input.getPath().substring(input.getPath().lastIndexOf("/") + 1));
        }
    }

    @PostMapping("/datahouse/hdfs/merge")
    public List<HDFSOperation> merge(@RequestBody MergeInput input){
        return new HDFSAPIOperation().mergeFiles(input.getPath(),input.getList());
    }

    @PostMapping("/datahouse/hdfs/uploadFiles/{fileName}")
    public HDFSOperation uploadFiles(@PathVariable("fileName") String fileName, @RequestBody Changing input) throws Exception {
        return new HDFSAPIOperation().uploadFiles(fileName,input.getPath(),input.getAfter());
    }

    @PostMapping("/datahouse/hdfs/downloadFiles")
    public HDFSOperation downloadFiles(@RequestBody Changing input){
        return new HDFSAPIOperation().downloadFile(input.getPath(),input.getAfter());
    }

    @PostMapping("/datahouse/hdfs/copyFiles")
    public HDFSOperation copyFiles(@RequestBody Changing input){
        return new HDFSAPIOperation().copyFile(input.getPath(),input.getAfter());
    }

}
