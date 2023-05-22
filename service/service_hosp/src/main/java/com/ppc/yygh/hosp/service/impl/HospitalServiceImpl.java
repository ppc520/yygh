package com.ppc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ppc.yygh.model.hosp.Hospital;
import com.ppc.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ppc.yygh.common.exception.YyghException;
import com.ppc.yygh.hosp.mapper.HospitalSetMapper;
import com.ppc.yygh.hosp.repository.HospitalRepository;
import com.ppc.yygh.hosp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private HospitalSetMapper hospitalSetMapper;
    @Override
    public void saveHospital(Map<String, Object> map) {
        String s = JSONObject.toJSONString(map);
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);
        String hoscode = hospital.getHoscode();
        Hospital collection=hospitalRepository.findByHoscode(hoscode);
        if (collection==null){
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else {
            hospital.setStatus(collection.getStatus());
            hospital.setCreateTime(collection.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(collection.getIsDeleted());
            hospital.setId(collection.getId());
            hospitalRepository.save(hospital);
        }

    }

    @Override
    public String getSignKeyByHoscode(String hoscode) {
        QueryWrapper<HospitalSet> qw=new QueryWrapper<HospitalSet>();
        qw.eq("hoscode",hoscode);
        HospitalSet hospitalSet = hospitalSetMapper.selectOne(qw);
        if (hospitalSet==null){
            throw new YyghException(20001,"该医院信息不存在");
        }
        return hospitalSet.getSignKey();
    }

    @Override
    public Hospital getHospitalByHosCode(String houseCode) {
        Hospital hospital = hospitalRepository.findByHoscode(houseCode);
        return hospital;
    }
}
