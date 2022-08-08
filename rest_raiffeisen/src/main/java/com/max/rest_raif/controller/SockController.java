package com.max.rest_raif.controller;

import com.max.rest_raif.DAO.SockDAO;
import com.max.rest_raif.entity.Sock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SockController {

    private final SockDAO sockDAO;

    @Autowired
    public SockController(SockDAO sockDAO) {
        this.sockDAO = sockDAO;
    }

    //метод для вывода всех носков(в задании не требуется)
    @GetMapping("/test")
    public List<Sock> getQuantityOfSocks(){
        return sockDAO.getAllSocks();
    }
    @GetMapping("/socks")
    public long getQuantity(@RequestParam("color") String color,
                           @RequestParam("operation") String operation,
                           @RequestParam("cottonPart") byte cottonPart){
        System.out.println(color);
        System.out.println(operation);
        System.out.println(cottonPart);
        return sockDAO.getQuantity(color,operation, cottonPart);
    }

    @PostMapping("/socks/income")
    public void addSocks(@RequestBody Sock sock){
        sockDAO.addSock(sock);
    }

    @PostMapping("/socks/outcome")
    public void takeSocks(@RequestBody Sock sock){
        sockDAO.takeSock(sock);
    }


}
