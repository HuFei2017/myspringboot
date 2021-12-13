package com.learning.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName ConsulServer1Controller
 * @Description TODO
 * @Author hufei
 * @Date 2021/6/9 15:50
 * @Version 1.0
 */
@RestController
public class ConsulConsumeController {

    private LoadBalancerClient balancer;
    private DiscoveryClient discovery;

    public ConsulConsumeController(LoadBalancerClient balancer,
                                   DiscoveryClient discovery) {
        this.balancer = balancer;
        this.discovery = discovery;
    }

    @GetMapping("/services")
    public Object getServices() {
        return discovery.getServices();
    }

    @GetMapping("/discover")
    public Object discover() {
        return balancer.choose("ConsulProducer").getUri().toString();
    }

    @GetMapping("/call")
    public String call(){
        ServiceInstance serviceInstance = balancer.choose("ConsulProducer");
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());

        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/hello", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }

}
