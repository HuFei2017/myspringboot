package com.learning.publics;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.learning.schema.HDFSOperation;
import com.learning.schema.KeyMap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HDFSAPIHelper {

    public String appendUrl(String path, String operation, List<KeyMap> list){
        return new ConnectionHelper().appendUrl(path,operation,list);
    }

    private String getUrl(List<KeyMap> list){
        StringBuilder result = new StringBuilder();
        for(KeyMap k:list){
            result.append("&").append(k.getName()).append("=").append(k.getValue());
        }
        return result.toString();
    }

    public HDFSOperation httpRequest(String requestUrl, String requestMethod, byte[] outputStr){
        StringBuffer buffer;
        try{
            URL url=new URL(requestUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //往服务器端写内容 也就是发起http请求需要带的参数
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr);
                os.close();
            }
            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line;
            while((line=br.readLine())!=null){
                buffer.append(line).append("\n");
            }
            try {
                return new HDFSOperation(200,"","", JSON.parseObject(buffer.toString()));
            }catch (JSONException je){
                return new HDFSOperation(200,buffer.toString(),"",new JSONObject());
            }
        }catch(Exception e){
            return new HDFSOperation(500,"",e.toString(), new JSONObject());
        }
    }

}
