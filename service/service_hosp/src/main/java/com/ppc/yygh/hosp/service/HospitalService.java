package com.ppc.yygh.hosp.service;

import com.ppc.yygh.model.hosp.Hospital;
import com.ppc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface HospitalService {
    void saveHospital(Map<String, Object> map);

    String getSignKeyByHoscode(String hoscode);

    Hospital getHospitalByHosCode(String houseCode);

    Page<Hospital> getHospitalPage(Integer pageNum, Integer pageSize, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Hospital detail(String id);
}
