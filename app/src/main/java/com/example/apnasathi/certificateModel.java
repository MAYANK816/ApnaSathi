package com.example.apnasathi;

public class certificateModel {
    String farmerName,farmerNumber,farmerAddress,farmerArea,farmerCropType;

    public certificateModel() {

    }
    public certificateModel(String farmerName, String farmerNumber, String farmerAddress, String farmerArea, String farmerCropType) {
        this.farmerName = farmerName;
        this.farmerNumber = farmerNumber;
        this.farmerAddress = farmerAddress;
        this.farmerArea = farmerArea;
        this.farmerCropType = farmerCropType;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerNumber() {
        return farmerNumber;
    }

    public void setFarmerNumber(String farmerNumber) {
        this.farmerNumber = farmerNumber;
    }

    public String getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(String farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public String getFarmerArea() {
        return farmerArea;
    }

    public void setFarmerArea(String farmerArea) {
        this.farmerArea = farmerArea;
    }

    public String getFarmerCropType() {
        return farmerCropType;
    }

    public void setFarmerCropType(String farmerCropType) {
        this.farmerCropType = farmerCropType;
    }
}
