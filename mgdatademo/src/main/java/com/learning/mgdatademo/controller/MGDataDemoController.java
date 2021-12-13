package com.learning.mgdatademo.controller;

import com.learning.mgdatademo.entity.*;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName MGDataDemoController
 * @Description TODO
 * @Author hufei
 * @Date 2021/11/10 10:19
 * @Version 1.0
 */
@RestController
public class MGDataDemoController {

    @GetMapping("/api/app/ai/tagPublicList")
    public List<TagInfo> getTagList(String name) {
        TagInfo tagInfo = new TagInfo();
        tagInfo.setName("L_R1_RMGATM");
        return Collections.singletonList(tagInfo);
    }

    @PostMapping("/api/app/ai/getThirdPartyGzipDtp")
    public List<GzipDtpInfo> getGzipDtps(@RequestBody GzipDtpRequest request){
        return Collections.singletonList(new GzipDtpInfo());
    }

    @PostMapping("/api/app/timeSequence/queryOriginalData2")
    public List<DtpDataResponse> getDtpData(@RequestBody DtpDataRequest request) throws IOException {
        DtpDataResponse response = new DtpDataResponse();
        response.setId(request.getTagList().get(0));
        File file = new File("C:\\Project\\IDEA Projects\\myspringboot\\compress\\src\\main\\java\\com\\learning\\compress\\file\\L_F1LP_ANGFB\\2021-02-10\\7a222416-ae22-4494-9bf1-d83bc4f80efd_1612968985737_0");
        byte[] fileBytes = FileUtils.readFileToByteArray(file);
        response.setData(Collections.singletonList(fileBytes));
        return Collections.singletonList(response);
    }

}
