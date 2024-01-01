package com.sen_system.utils;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReferenceGeneretor{
    public String generetedRef(String country,String fistName,String LastName){
        String ctry = country.length() >= 2 ? country.substring(0,2).toUpperCase() : country.toUpperCase();
        String pre = fistName.length() >= 2 ? fistName.substring(0,2).toUpperCase() : fistName.toUpperCase();
        String suf = LastName.length() >= 2 ? LastName.substring(0,2).toUpperCase() : LastName.toUpperCase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmssSSS");
        String datePart = dateFormat.format(new Date());
        String preDate = datePart.substring(0,6);
        String interDate = datePart.substring(6,10);
        String sufDate = datePart.substring(10,14);
        return ctry + preDate + pre + interDate + suf + sufDate;
    }
}
