package com.learning.mgdatademo.controller;

import com.learning.mgdatademo.entity.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName MGDataDemoController
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/10 10:19
 * @Version 1.0
 */
@RestController
public class MGDataDemoController {

    private final SimpleDateFormat df;

    public MGDataDemoController() {
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    @GetMapping("/api/app/ai/tagPublicList")
    public List<TagInfo> getTagList(String name) {
        List<TagInfo> list = new ArrayList<>();
        list.add(createTagInfo("488edb94-b625-44c0-8358-db5ebbbbaae7", "L_KA21MSY02T12", "X/32000*300"));
        list.add(createTagInfo("4564e0e2-5c4d-4ca7-bf2d-8a5835e22a94", "L_FJ21YVHS1_SPF", "(x-12800)/51200*200-100"));
        list.add(createTagInfo("c96d6dac-d9ff-46ef-9d8f-ad443181d886", "L_JA21MKL01T22", "X/32000*300"));
        list.add(createTagInfo("ed30107b-3d67-4180-9cf4-77ae1ae7e19a", "L_JF21YVHS1_SPF", "(x-12800)/51200*200-100"));
        list.add(createTagInfo("47820511-5136-41b2-9d92-fb424c7c408b", "L_JF21YVHS2_SPF", "(x-12800)/51200*200-100"));
        return list;
    }

    @PostMapping("/api/app/ai/getThirdPartyGzipDtp")
    public List<GzipDtpInfo> getGzipDtps(@RequestBody GzipDtpRequest request) {
        List<GzipDtpInfo> list = new ArrayList<>();
        long start = 1612968985737L;
        int i = 0;
        while (i < 13) {
            long end = start + 300000L;
            list.add(createGzipDtpInfo(
                    df.format(new Date(start)),
                    df.format(new Date(end)),
                    "7a222416-ae22-4494-9bf1-d83bc4f80efd_" + start + "_0"
            ));
            start = end;
            i++;
        }
        return list;
    }

    @PostMapping("/api/app/timeSequence/queryOriginalData2")
    public List<DtpDataResponse> getDtpData(@RequestBody DtpDataRequest request) throws IOException, ParseException {
        DtpDataResponse response = new DtpDataResponse();
        response.setId(request.getTagList().get(0));
        ClassPathResource classPathResource = new ClassPathResource("file/7a222416-ae22-4494-9bf1-d83bc4f80efd_" + df.parse(request.getStartTime().replace("T", " ")).getTime() + "_0");
        response.setData(Collections.singletonList(FileCopyUtils.copyToByteArray(classPathResource.getInputStream())));
        return Collections.singletonList(response);
    }

    private TagInfo createTagInfo(String id, String name, String expression) {
        TagInfo info = new TagInfo();
        info.setId(id);
        info.setName(name);
        info.setFunction(expression);
        info.setProperty("1");
        info.setData("N/A");
        return info;
    }

    private GzipDtpInfo createGzipDtpInfo(String startTime, String endTime, String path) {
        GzipDtpInfo info = new GzipDtpInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        info.setPath(path);
        info.setDfsPath(path);
        return info;
    }

}