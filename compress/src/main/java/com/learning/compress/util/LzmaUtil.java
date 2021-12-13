package com.learning.compress.util;

import lzma.sdk.lzma.Decoder;
import lzma.streams.LzmaInputStream;
import lzma.streams.LzmaOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @ClassName LzmaUtil
 * @Description TODO
 * @Author hufei
 * @Date 2021/6/7 21:45
 * @Version 1.0
 */
public class LzmaUtil {

    public static byte[] compress(byte[] data) throws IOException {
        try (
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                LzmaOutputStream out = new LzmaOutputStream.Builder(
                        new BufferedOutputStream(stream))
                        .useMaximalDictionarySize()
                        .useMaximalFastBytes()
                        .build();
                InputStream in = new BufferedInputStream(new ByteArrayInputStream(data))) {
            IOUtils.copy(in, out);
            return stream.toByteArray();
        }
    }

    public static byte[] decompress(byte[] data) throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             LzmaInputStream in = new LzmaInputStream(
                     new BufferedInputStream(new ByteArrayInputStream(data)),
                     new Decoder());
             OutputStream out = new BufferedOutputStream(stream)) {
            IOUtils.copy(in, out);
            return stream.toByteArray();
        }
    }

}
