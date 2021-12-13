package com.learning.datasource.apply;

import com.learning.publics.FileUtil;
import com.learning.publics.HDFSAPIHelper;
import com.learning.publics.Publication;
import com.learning.schema.FileFormat;
import com.learning.schema.FileStatuses;
import com.learning.schema.HDFSOperation;
import com.learning.schema.KeyMap;

import java.util.ArrayList;
import java.util.List;

import static org.apache.parquet.Strings.isNullOrEmpty;

public class HDFSAPIOperation {

    /**
     * curl -i -L "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=OPEN
     [&offset=<LONG>][&length=<LONG>][&buffersize=<INT>][&noredirect=<true|false>]"
     */
    public HDFSOperation readFile(String path, List<KeyMap> list){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"OPEN",list),"GET",null);
    }

    /**
     * curl -i  "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=LISTSTATUS"
     */
    public List<FileFormat> listFiles(String path, String option){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        HDFSOperation hdfs = helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"LISTSTATUS",new ArrayList<>()),"GET",null);
        List<FileFormat> list = hdfs.getResult().toJavaObject(FileStatuses.class).getFileStatuses().getFileStatus();
        List<FileFormat> result = new ArrayList<>();
        if(isNullOrEmpty(option)) {
            for (FileFormat f : list) {
                f.setPermission(new Publication().toPermission(f.getType(),f.getPermission()));
            }
            result=list;
        }else {
            for (FileFormat f : list) {
                if (f.getPathSuffix().contains(option)) {
                    f.setPermission(new Publication().toPermission(f.getType(),f.getPermission()));
                    result.add(f);
                }
            }
        }
        return result;
    }

    /**
     * curl -i  "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=LISTSTATUS_BATCH&startAfter=<CHILD>"
     */
    public HDFSOperation iterListFiles(String path, List<KeyMap> list){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"LISTSTATUS_BATCH",list),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=GETCONTENTSUMMARY"
     */
    public HDFSOperation summarDir(String path){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"GETCONTENTSUMMARY",new ArrayList<>()),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=GETFILECHECKSUM"
     */
    public HDFSOperation fileCheck(String path){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"GETFILECHECKSUM",new ArrayList<>()),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/?op=GETHOMEDIRECTORY"
     */
    public HDFSOperation home(){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl("","GETHOMEDIRECTORY",new ArrayList<>()),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/?op=GETDELEGATIONTOKEN
     [&renewer=<USER>][&service=<SERVICE>][&kind=<KIND>]"
     */
    public HDFSOperation deleToken(List<KeyMap> list){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl("","GETDELEGATIONTOKEN",list),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=GETTRASHROOT"
     */
    public HDFSOperation trashRoot(String path){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"GETTRASHROOT",new ArrayList<>()),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=GETXATTRS
     &xattr.name=<XATTRNAME>&encoding=<ENCODING>"
     *
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=GETXATTRS
     &xattr.name=<XATTRNAME1>&xattr.name=<XATTRNAME2>&encoding=<ENCODING>"
     *
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=GETXATTRS
     &encoding=<ENCODING>"
     */
    public HDFSOperation xattr(String path, List<KeyMap> list){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"GETXATTRS",list),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=LISTXATTRS"
     */
    public HDFSOperation listXattr(String path){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"LISTXATTRS",new ArrayList<>()),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=CHECKACCESS
     &fsaction=<FSACTION>
     */
    public HDFSOperation checkAccess(String path, List<KeyMap> list){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"CHECKACCESS",list),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1?op=GETALLSTORAGEPOLICY"
     */
    public HDFSOperation storagePolicyAll(){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl("","GETALLSTORAGEPOLICY",new ArrayList<>()),"GET",null);
    }

    /**
     * curl -i "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=GETSTORAGEPOLICY"
     */
    public HDFSOperation storagePolicy(String path){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"GETSTORAGEPOLICY",new ArrayList<>()),"GET",null);
    }

    /**
     * "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=MKDIRS[&permission=<OCTAL>]"
    */
     public HDFSOperation mkdir(String path, List<KeyMap> list){
         HDFSAPIHelper helper = new HDFSAPIHelper();
         return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"MKDIRS",list),"PUT",null);
     }

    /**
     * curl -i -X PUT "<HOST>:<PORT>/webhdfs/v1/<PATH>?op=RENAME&destination=<PATH>"
     */
     public HDFSOperation rename(String old_path, String new_path){
         HDFSAPIHelper helper = new HDFSAPIHelper();
         List<KeyMap> list = new ArrayList<>();
         KeyMap k = new KeyMap();
         k.setName("destination");
         k.setValue(new_path);
         list.add(k);
         return helper.httpRequest(helper.appendUrl(old_path.substring(old_path.indexOf("/")+1),"RENAME",list),"PUT",null);
     }

    /**
     * curl -i -X PUT "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=SETPERMISSION
     [&permission=<OCTAL>]"
     */
    public HDFSOperation setPermission(String path, String permission){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        List<KeyMap> list = new ArrayList<>();
        KeyMap k = new KeyMap();
        k.setName("permission");
        k.setValue(permission);
        list.add(k);
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"SETPERMISSION",list),"PUT",null);
    }

    /**
     * curl -i -X DELETE "http://<host>:<port>/webhdfs/v1/<path>?op=DELETE
     [&recursive=<true |false>]"
     */
    public HDFSOperation delete(String path, String recursive){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        List<KeyMap> list = new ArrayList<>();
        KeyMap k = new KeyMap();
        k.setName("recursive");
        k.setValue(recursive);
        list.add(k);
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"DELETE",list),"DELETE",null);
    }

    /**
     * curl -i -X PUT "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=SETOWNER
     [&owner=<USER>][&group=<GROUP>]"
     */
    public HDFSOperation setOwner(String path, String owner){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        List<KeyMap> list = new ArrayList<>();
        String str[] = owner.split(";");
        if(!str[0].equals("")) {
            KeyMap k = new KeyMap();
            k.setName("owner");
            k.setValue(str[0]);
            list.add(k);
        }
        if(!str[1].equals("")) {
            KeyMap m = new KeyMap();
            m.setName("group");
            m.setValue(str[1]);
            list.add(m);
        }
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1),"SETOWNER",list),"PUT",null);
    }

    /**
     * curl -i -X POST "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=CONCAT&sources=<PATHS>"
     */
    public List<HDFSOperation> mergeFiles(String path, List<String> src){
        List<HDFSOperation> result = new ArrayList<>();
        HDFSAPIHelper helper = new HDFSAPIHelper();
        for(String s : src) {
            List<KeyMap> list = new ArrayList<>();
            KeyMap k = new KeyMap();
            k.setName("sources");
            k.setValue(s);
            list.add(k);
            result.add(helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1), "CONCAT", list), "POST", null));
        }
        return result;
    }

    /**
     * curl -i -X PUT -T <LOCAL_FILE> "http://<DATANODE>:<PORT>/webhdfs/v1/<PATH>?op=CREATE..."
     */
    public HDFSOperation uploadFiles(String fileName, String path, String files){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        return helper.httpRequest(helper.appendUrl(path.substring(path.indexOf("/")+1)+"/"+(isNullOrEmpty(fileName)||(fileName.equals("-1"))?files.substring(files.lastIndexOf("\\")+1):fileName),"CREATE",new ArrayList<>()),"PUT",new FileUtil().getBytes(files));
    }

    /**
    * downloadFile from HDFS using "open" file
    */
    public HDFSOperation downloadFile(String path, String localPath){
        HDFSOperation result = new HDFSOperation(200,"", "", null);
        try {
            String content = readFile(path, new ArrayList<>()).getContent();
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            if (!fileName.contains("."))
                fileName += ".txt";
            new FileUtil().getFile(content.getBytes(), localPath, fileName);
        }catch (Exception e){
            result.setStatus(500);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * copyFile from HDFS to HDFS using "open" file
     */
    public HDFSOperation copyFile(String src, String dst){
        HDFSAPIHelper helper = new HDFSAPIHelper();
        String content = readFile(src, new ArrayList<>()).getContent();
        String fileName = src.substring(src.lastIndexOf("/")+1);
        return helper.httpRequest(helper.appendUrl(dst.substring(dst.indexOf("/")+1)+"/"+fileName,"CREATE",new ArrayList<>()),"PUT",content.getBytes());
    }

}

