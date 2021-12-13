package com.learning.compress.test;

import com.learning.compress.proto.MGDataProto;
import com.learning.compress.util.GZIPUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName MGDataTest
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/8 14:46
 * @Version 1.0
 */
public class MGDataTest {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Project\\IDEA Projects\\myspringboot\\compress\\src\\main\\java\\com\\learning\\compress\\file\\L_F1LP_ANGFB\\2021-02-10\\7a222416-ae22-4494-9bf1-d83bc4f80efd_1612968985737_0");
        byte[] fileBytes = FileUtils.readFileToByteArray(file);
        byte[] uncompressed = GZIPUtil.uncompress(fileBytes);
        MGDataProto.MGDataProtoDefine data = MGDataProto.MGDataProtoDefine.parseFrom(uncompressed);
        int size = data.getData().getDataList().size();
        System.out.println("Id："+data.getId());
        System.out.println("Addr："+data.getAddr());
        System.out.println("ChannelId："+data.getChannelId());
        System.out.println("SignalName："+data.getSignalName());
        System.out.println("parameter："+data.getParameter());
        System.out.println("StartTime："+data.getStartTime().getUnknownFields().getField(1).getVarintList().get(0)+"."+data.getStartTime().getUnknownFields().getField(2).getVarintList().get(0));
        System.out.println("EndTime："+data.getEndTime().getUnknownFields().getField(1).getVarintList().get(0)+"."+data.getEndTime().getUnknownFields().getField(2).getVarintList().get(0));
        System.out.println("Data：");
        for(int i = 0;i<size;i++){
            System.out.println("Time："+data.getData().getTimeList().get(i)+"；Value："+data.getData().getDataList().get(i));
        }
    }
}