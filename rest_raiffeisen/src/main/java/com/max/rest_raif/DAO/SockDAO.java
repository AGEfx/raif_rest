package com.max.rest_raif.DAO;

import com.max.rest_raif.entity.Sock;

import java.util.List;

public interface SockDAO {
    long getQuantity(String color, String operation,byte cottonPart);
    void addSock(Sock sock);
    void takeSock(Sock sock);
    List<Sock> getAllSocks();
}
