package com.demo.graphql.meterdataql.service;

import com.demo.graphql.meterdataql.model.MeterData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeterDataService {

    private List<MeterData> meterData;

    public MeterDataService(List<MeterData> meterData) {
        this.meterData = meterData;
    }

    public List<MeterData> findAll(){
        return meterData;
    }

    public List<MeterData> findByReadUnit(String dregisterRead){

        List<MeterData> onlyUnits=new ArrayList<>();
        for(MeterData md:this.meterData){
            if(md.getdRegisterRead().equals(dregisterRead)) onlyUnits.add(md);
        }
        return onlyUnits;
    }
}
