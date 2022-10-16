package com.project.digitalpaymentsbook;

public class RetailerDetails {
    private String companyName;
    private String email;
    private String password;
    private long phoneNumber;
    private String RetailerAddress;
    private String RetailerCity;
    private String RetailerRegion;
    private int postalCode;
    private String userType;


    public RetailerDetails(){

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRetailerAddress() {
        return RetailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        RetailerAddress = retailerAddress;
    }

    public String getRetailerCity() {
        return RetailerCity;
    }

    public void setRetailerCity(String retailerCity) {
        RetailerCity = retailerCity;
    }

    public String getRetailerRegion() {
        return RetailerRegion;
    }

    public void setRetailerRegion(String retailerRegion) {
        RetailerRegion = retailerRegion;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
