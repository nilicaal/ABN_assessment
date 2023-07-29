/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mybank.abnamro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author niels
 */
public class RetrieveUser {
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        Database db = new Database();

        try (Connection connection = db.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString("first_name"));
                user.setSurname(resultSet.getString("surname"));
                user.setBsn(resultSet.getString("bsn"));
                user.setMobilePhoneNumber(resultSet.getString("mobile_phone_number"));
                user.setHouseNumber(resultSet.getString("house_number"));
                user.setPostcode(resultSet.getString("postcode"));
                user.setStreetName(resultSet.getString("street_name"));
                user.setCity(resultSet.getString("city"));
                user.setProvince(resultSet.getString("province"));
                user.setBankAccountNumber(resultSet.getString("bank_account_number"));
                user.setBankAccountBalance(resultSet.getDouble("bank_account_balance"));
                user.setBankName(resultSet.getString("bank_name"));

                users.add(user);
            }
        } catch (SQLException e) {
        }
        return users;
    }
}
