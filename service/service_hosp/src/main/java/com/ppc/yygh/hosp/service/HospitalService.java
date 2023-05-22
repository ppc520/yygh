package com.ppc.yygh.hosp.service;

import com.ppc.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    void saveHospital(Map<String, Object> map);

    String getSignKeyByHoscode(String hoscode);

    Hospital getHospitalByHosCode(String houseCode);
}
