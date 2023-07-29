/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mybank.abnamro;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author niels
 */
public class Register implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "User registered!";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response.getBytes());
        }
    }
}
