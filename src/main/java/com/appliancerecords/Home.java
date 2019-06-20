package com.appliancerecords;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public class Home {
    private String applianceName;
    private Date dateOfPurchase;
    private boolean serviceRequired;
    private int serviceTime;
    private java.util.Date firstService;
    private java.util.Date subsequentService;
    private Date nextService;
    private String serviceRemarks;
    private boolean AMC;
    private int AmcDuration;
    private Date expiry;
    private String Id;
    private MultipartFile multipartFile;

    public String getApplianceName() {
        return applianceName;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public boolean isServiceRequired() {
        return serviceRequired;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public java.util.Date getFirstService() {
        return firstService;
    }

    public java.util.Date getSubsequentService() {
        return subsequentService;
    }

    public String getServiceRemarks() {
        return serviceRemarks;
    }

    public boolean isAMC() {
        return AMC;
    }

    public int getAmcDuration() {
        return AmcDuration;
    }


    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public void setServiceRequired(boolean serviceRequired) {
        this.serviceRequired = serviceRequired;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setFirstService(Date firstService) {
        this.firstService = firstService;
    }

    public void setSubsequentService(Date subsequentService) {
        this.subsequentService = subsequentService;
    }

    public void setServiceRemarks(String serviceRemarks) {
        this.serviceRemarks = serviceRemarks;
    }

    public void setAMC(boolean amc) {
        AMC = amc;
    }

    public void setAmcDuration(int amcDuration) {
        AmcDuration = amcDuration;
    }

    public java.util.Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public Date getNextService() {
        return nextService;
    }

    public void setNextService(Date nextService) {
        this.nextService = nextService;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
