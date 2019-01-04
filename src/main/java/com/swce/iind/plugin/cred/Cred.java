package com.swce.iind.plugin.cred;

import java.io.Serializable;

/**
 * Created by sanjayda on 8/20/2018 at 3:59 PM
 */
public class Cred implements Serializable
{
    private static final long serialVersionUID = -299482035708790407L;

    private String USERNAME;
    private String PASSWORD;
    private String HOST;
    private int PORT;


    public Cred(String USERNAME, String PASSWORD, String HOST, int PORT) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.HOST = HOST;
        this.PORT = PORT;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }
}
