package com.learning.publics;

import java.io.UnsupportedEncodingException;

public class Publication {

    public String beforeTransfer(String s) {
        if (!s.contains("'"))
            return s;
        String[] m = s.split("");
        StringBuilder sBuilder = new StringBuilder();
        for (String k : m) {
            if (k.equals("'"))
                sBuilder.append("\\").append(k);
            else
                sBuilder.append(k);
        }
        s = sBuilder.toString();
        return s;
    }

    public String toPermission(String type, String str) {
        String head;
        StringBuilder body = new StringBuilder();
        StringBuilder per = new StringBuilder();
        for(char tmp : str.toCharArray()){
            per.append(Integer.toBinaryString(Integer.parseInt(String.valueOf(tmp))));
        }
        switch (type) {
            case "DIRECTORY":
                head = "d";
                break;
            case "FILE":
                head = "-";
                break;
            default:
                head = "s";
        }
        int i = 0;
        for (char s : per.toString().toCharArray()) {
            if (s == '1' && i % 3 == 0)
                body.append("r");
            else if (s == '1' && i % 3 == 1)
                body.append("w");
            else if (s == '1' && i % 3 == 2)
                body.append("x");
            else
                body.append("-");
            i++;
        }
        return head + body.toString();
    }

    public String stringToUnicode(String s){
        try {
            StringBuilder out = new StringBuilder("");
            //直接获取字符串的unicode二进制
            byte[] bytes = s.getBytes("unicode");
            //然后将其byte转换成对应的16进制表示即可
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertStringToUTF8(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c <= 255) {
                    sb.append(c);
                } else {
                    byte[] b;
                    b = Character.toString(c).getBytes("utf-8");
                    for (byte aB : b) {
                        int k = aB;
                        //转换为unsigned integer  无符号integer
                    /*if (k < 0)
                        k += 256;*/
                        k = k < 0 ? k + 256 : k;
                        //返回整数参数的字符串表示形式 作为十六进制（base16）中的无符号整数
                        //该值以十六进制（base16）转换为ASCII数字的字符串
                        sb.append(Integer.toHexString(k).toUpperCase());

                        // url转置形式
                        // sb.append("%" +Integer.toHexString(k).toUpperCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
