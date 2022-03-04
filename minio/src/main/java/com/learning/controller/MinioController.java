package com.learning.controller;

import com.learning.service.MinioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MinioController
 * @Description TODO
 * @Author hufei
 * @Date 2022/3/4 14:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/phm/analysis-api")
public class MinioController {

    private final MinioService service;

    public MinioController(MinioService service) {
        this.service = service;
    }

    @PostMapping("/devlevel/data/add")
    public String addDevlevelData(@RequestBody byte[] data) {
        return service.addDevlevelData(data);
    }

}
