/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 * WIP: here I started rethinking the database model, as this was a lot of code that seems very redundant. 
*/
package com.mybank.abnamro;

/**
 *
 * @author niels
 */

public class User {
    private String firstName;
    private String surname;
    private String bsn;
    private String mobilePhoneNumber;
    private String houseNumber;
    private String postcode;
    private String streetName;
    private String city;
    private String province;
    private String bankAccountNumber;
    private double bankAccountBalance;
    private String bankName;

    public User() {
    }

    // Constructor with all properties
    public User(String firstName, String surname, String bsn, String mobilePhoneNumber,
                String houseNumber, String postcode, String streetName, String city, String province,
                String bankAccountNumber, double bankAccountBalance, String bankName) {
        this.firstName = firstName;
        this.surname = surname;
        this.bsn = bsn;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountBalance = bankAccountBalance;
        this.bankName = bankName;
    }

    // Getters and setters for all properties

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getBsn() {
        return bsn;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setBankAccountBalance(double bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
