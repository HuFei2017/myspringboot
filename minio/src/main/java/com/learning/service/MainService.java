package com.learning.service;

import com.learning.dto.Devlevel;
import com.learning.dto.DevlevelAddRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MainService
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/4 19:50
 * @Version 1.0
 */
@Service
public class MainService {

    private final MinioService minioService;
    private final ElasticService elasticService;

    public MainService(MinioService minioService,
                       ElasticService elasticService) {
        this.minioService = minioService;
        this.elasticService = elasticService;
    }

    public String addDevlevelData(DevlevelAddRequest devlevelAddRequest, byte[] data) {

        String filePath = devlevelAddRequest.getOrdernumber() + "/devlevel/" + devlevelAddRequest.getFilename();

        //minio上传文件
        minioService.uploadFileToMinio(data, filePath);

        String fullFilePath = "/" + filePath;

        Devlevel devlevel = new Devlevel();
        BeanUtils.copyProperties(devlevelAddRequest, devlevel);
        devlevel.setFilePath(fullFilePath);

        //ES录入信息
        elasticService.addDeviceInfoToElastic(devlevel);

        return fullFilePath;
    }

    public byte[] getDevlevelData(String ordernumber, String filename) {
        List<Devlevel> devlevelList = elasticService.getDevlevels(ordernumber, filename);
        return minioService.getByteContent(devlevelList.get(0).getFilePath().substring(1));
    }
}
