package com.demo.graphql.meterdataql.model;

import java.io.Serializable;
import java.time.LocalDateTime;


public class MeterData implements Serializable {

    private String accessCode;

    private String zTsRead;

    private String dRegisterRead;

    private String mRead;

    private String dReadUnit;

    public MeterData() {
    }

    public MeterData(String accessCode, String zTsRead, String dRegisterRead, String mRead, String dReadUnit) {
        this.accessCode = accessCode;
        this.zTsRead = zTsRead;
        this.dRegisterRead = dRegisterRead;
        this.mRead = mRead;
        this.dReadUnit = dReadUnit;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getzTsRead() {
        return zTsRead;
    }

    public void setzTsRead(String zTsRead) {
        this.zTsRead = zTsRead;
    }

    public String getdRegisterRead() {
        return dRegisterRead;
    }

    public void setdRegisterRead(String dRegisterRead) {
        this.dRegisterRead = dRegisterRead;
    }

    public String getmRead() {
        return mRead;
    }

    public void setmRead(String mRead) {
        this.mRead = mRead;
    }

    public String getdReadUnit() {
        return dReadUnit;
    }

    public void setdReadUnit(String dReadUnit) {
        this.dReadUnit = dReadUnit;
    }
}
