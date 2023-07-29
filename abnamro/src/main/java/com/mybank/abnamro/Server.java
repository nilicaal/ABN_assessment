/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mybank.abnamro;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author niels
 */
public class Server {
    public static void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/login", new Login());
        server.createContext("/show", new Show());
        server.createContext("/register", new Register());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080.");
    }
}
