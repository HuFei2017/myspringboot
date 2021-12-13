package com.learning.compress.test;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

/**
 * @ClassName Base64tEST
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/8 16:31
 * @Version 1.0
 */
public class Base64Test {
    public static void main(String[] args) {

        double[] data = {1.0, 2.0, 3.0, 4.15, 7.81};
        String dataCompressed = "eJyLNtQz0FEwAhHGIMJEz9BUR8Fcz8IwFgBHpwUv";

        String compressed = compressData(Arrays.toString(data));

        System.out.println(compressed.equals(dataCompressed));
        System.out.println("the compressed content is shown as follow:\n" + compressed);

        Double[] decompressed = getFloatArrayFromString(decompressData(compressed));

        System.out.println(decompressed.length == data.length);
        System.out.println("the decompressed content is shown as follow:\n" + Arrays.toString(decompressed));
    }


    /**
     * zlib压缩+base64
     */
    private static String compressData(String data) {
        ByteArrayOutputStream bos;
        DeflaterOutputStream zos;
        try {
            bos = new ByteArrayOutputStream();
            zos = new DeflaterOutputStream(bos);
            zos.write(data.getBytes());
            zos.close();
            return new String(Base64.encodeBase64(bos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * zlib解压+base64
     */
    private static String decompressData(String encdata) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InflaterOutputStream zos = new InflaterOutputStream(bos);
            zos.write(Base64.decodeBase64(encdata.getBytes()));
            zos.close();
            return new String(bos.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private static Double[] getFloatArrayFromString(String str) {
        if (str.equals("[]"))
            return new Double[0];
        return getFloatArrayFromStringArray(str.replace("[", "").replace("]", "").split(","));
    }

    private static Double[] getFloatArrayFromStringArray(String[] array) {
        Double[] ret = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            if (array[i].toLowerCase().contains("null"))
                ret[i] = null;
            else {
                try {
                    ret[i] = Double.valueOf(array[i]);
                } catch (NumberFormatException e) {
                    ret[i] = null;
                }
            }
        }
        return ret;
    }
}
