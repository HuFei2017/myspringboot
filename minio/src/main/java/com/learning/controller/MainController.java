package com.learning.controller;

import com.learning.dto.DevlevelAddRequest;
import com.learning.service.MainService;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName MinioController
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/4 14:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/analysis-api")
public class MainController {

    private final MainService service;

    public MainController(MainService service) {
        this.service = service;
    }

    @PostMapping("/test")
    public String test(@RequestBody byte[] data) {
        return "file length：" + data.length;
    }

    /**
     * @Description 设备级数据上传
     * @Return java.lang.String
     * @Author jiashudong
     * @Date 2021/02/14 16:06
     */
    @PostMapping("/devlevel/data/add")
    public String addDevlevelData(@RequestParam(value = "filename", required = false) String filename,
                                  @RequestParam(value = "ordernumber", required = false) String ordernumber,
                                  @RequestParam(value = "creator", required = false) String creator,
                                  @RequestBody byte[] data) {
        DevlevelAddRequest devlevelAddRequest = new DevlevelAddRequest();
        devlevelAddRequest.setOrdernumber(ordernumber);
        devlevelAddRequest.setFilename(filename);
        devlevelAddRequest.setCreator(creator);
        return service.addDevlevelData(devlevelAddRequest, data);
    }

    /**
     * @Description 设备级数据查询
     * @Return byte[]
     * @Author jiashudong
     * @Date 2021/02/14 16:06
     */
    @PostMapping("/devlevel/data/get")
    public byte[] getDevlevelData(@RequestParam(value = "ordernumber", required = false) String ordernumber,
                                  @RequestParam(value = "filename", required = false) String filename) {
        return service.getDevlevelData(ordernumber, filename);
    }

}
