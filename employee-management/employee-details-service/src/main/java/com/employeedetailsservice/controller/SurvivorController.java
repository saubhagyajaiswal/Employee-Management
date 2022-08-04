package com.employeedetailsservice.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@EnableDiscoveryClient
@RestController
@RequestMapping("/survivor")
public class SurvivorController {
    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/survivors")
    @RolesAllowed("admin")
    private ResponseEntity<Object> getSurvivorData(){
//        InstanceInfo service = eurekaClient
//                .getApplication("SURVIVE-APPOCALYPSE")
//                .getInstances()
//                .get(0);
//
//        String hostName = service.getHostName();
//        int port = service.getPort();
        String hostName = "localhost";
        int port = 8090;

        URI url = URI.create("http://" + hostName + ":" + port + "/survivor");

        ResponseEntity<String> response = new RestTemplate().getForEntity(url, String.class);

        System.out.println(response.getBody());
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @Autowired
    private LoadBalancerClient loadBalancer;

    @GetMapping("/survivorsList")
    @RolesAllowed("admin")
    private ResponseEntity<Object> getSurvivor(){
//        URI url = URI.create("http://SURVIVE-APPOCALYPSE/survivor");
        ServiceInstance instance = loadBalancer.choose("SURVIVE-APPOCALYPSE");
        URI uri = instance.getUri();

        ResponseEntity<String> response = new RestTemplate().getForEntity(uri, String.class);

        System.out.println(response.getBody());
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}
