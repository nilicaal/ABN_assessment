/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mybank.abnamro;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.google.gson.Gson;

/**
 *
 * @author niels
 */
public class Show implements HttpHandler {
@Override
public void handle(HttpExchange exchange) throws IOException {
        // Get the authenticated user (you should implement proper authentication and retrieval of the username)
        String authenticatedIBAN = ""; // Replace this with the authenticated username

        // Fetch user data from the database
        User user = findUserByUsername(authenticatedIBAN);

        if (user != null) {
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(user);

            // Send the response
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(responseBytes);
            }
        } else {
            // If the user is not found (should not happen if properly authenticated)
            try (exchange) {
                // If the user is not found (should not happen if properly authenticated)
                exchange.sendResponseHeaders(403, 0);
            }
        }
    }

    private User findUserByUsername(String username) {
        List<User> users = RetrieveUser.getAllUsers();
        for (User user : users) {
            if (user.getBankAccountNumber().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
