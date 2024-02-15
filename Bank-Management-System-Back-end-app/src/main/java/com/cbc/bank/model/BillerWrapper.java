package com.cbc.bank.model;

public class BillerWrapper {
    
    private String biller;
    private String theirRef;
    
    private String yourRef;

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getTheirRef() {
        return theirRef;
    }

    public void setTheirRef(String theirRef) {
        this.theirRef = theirRef;
    }

    public String getYourRef() {
        return yourRef;
    }

    public void setYourRef(String yourRef) {
        this.yourRef = yourRef;
    }

    @Override
    public String toString() {
        return "BillerWrapper{" +
                "biller='" + biller + '\'' +
                ", theirRef='" + theirRef + '\'' +
                ", yourRef='" + yourRef + '\'' +
                '}';
    }
}
