package com.example.webservice2;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class RestFrontService {

        @Autowired
        private LoadBalancerClient loadBalancer;

        @Autowired
        DiscoveryClient discoveryClient;
        @HystrixCommand(fallbackMethod = "defaultMessage")
        @GetMapping("/")
        public String hello() {
            ServiceInstance serviceInstance = loadBalancer.choose("WebRestService");
            System.out.println(serviceInstance.getUri());
//            List<ServiceInstance> instances = discoveryClient.getInstances("WebRestService");
//            ServiceInstance test = instances.get(0);
//            String hostname = test.getHost();
//            int port = test.getPort();
            String hostname = serviceInstance.getHost();
            int port = serviceInstance.getPort();
            RestTemplate restTemplate = new RestTemplate();
            String microservice1Address = "http://" + hostname + ":" + port;
            ResponseEntity<String> response =
                    restTemplate.getForEntity(microservice1Address, String.class);
            String s = response.getBody();
            return s;
        }

    public String defaultMessage() {
        return "Salut Veuillez réessayez dans un moment le serveur a eu un petit problème de connexion a votre service !";
    };
};
