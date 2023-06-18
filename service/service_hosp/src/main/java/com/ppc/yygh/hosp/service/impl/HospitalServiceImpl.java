package com.ppc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ppc.yygh.client.DictFeignClient;
import com.ppc.yygh.enums.DictEnum;
import com.ppc.yygh.model.hosp.Hospital;
import com.ppc.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ppc.yygh.common.exception.YyghException;
import com.ppc.yygh.hosp.mapper.HospitalSetMapper;
import com.ppc.yygh.hosp.repository.HospitalRepository;
import com.ppc.yygh.hosp.service.HospitalService;
import com.ppc.yygh.vo.hosp.HospitalQueryVo;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private HospitalSetMapper hospitalSetMapper;
    @Autowired
    private DictFeignClient dictFeignClient;
    @Override
    public void saveHospital(Map<String, Object> map) {
        String s = JSONObject.toJSONString(map);
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);
        String hoscode = hospital.getHoscode();
        Hospital collection = hospitalRepository.findByHoscode(hoscode);
        if (collection == null) {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else {
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
        QueryWrapper<HospitalSet> qw = new QueryWrapper<HospitalSet>();
        qw.eq("hoscode", hoscode);
        HospitalSet hospitalSet = hospitalSetMapper.selectOne(qw);
        if (hospitalSet == null) {
            throw new YyghException(20001, "该医院信息不存在");
        }
        return hospitalSet.getSignKey();
    }

    @Override
    public Hospital getHospitalByHosCode(String houseCode) {
        Hospital hospital = hospitalRepository.findByHoscode(houseCode);
        return hospital;
    }

    @Override
    public Page<Hospital> getHospitalPage(Integer pageNum, Integer pageSize, HospitalQueryVo hospitalQueryVo) {
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);

        Sort sort=Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable=PageRequest.of(pageNum-1,pageSize,sort);

        ExampleMatcher matcher=ExampleMatcher.matching()
                .withMatcher("hosname", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Hospital> example=Example.of(hospital,matcher);
        Page<Hospital> page = hospitalRepository.findAll(example,pageable);
        for (Hospital item : page.getContent()) {
           this.packageHospital(item);
        }
        return page;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        if (status==0||status==1){
            Hospital hospital = hospitalRepository.findById(id).get();
            hospital.setStatus(status);
            hospital.setUpdateTime(new Date());
            hospitalRepository.save(hospital);
        }

    }

    @Override
    public Hospital detail(String id) {
        Hospital hospital = hospitalRepository.findById(id).get();
        this.packageHospital(hospital);
        return hospital;
    }

    private void packageHospital(Hospital hospital){
        String provinceAddress = dictFeignClient.getNameByValue(Long.parseLong(hospital.getProvinceCode()));
        String cityAdress = dictFeignClient.getNameByValue(Long.parseLong(hospital.getCityCode()));
        String districtAddress=dictFeignClient.getNameByValue(Long.parseLong(hospital.getDistrictCode()));
        String level=dictFeignClient.getNameByDictCodeAndValue(Long.parseLong(hospital.getHostype()), DictEnum.HOSTYPE.getDictCode());
        hospital.getParam().put("hostypeString",level);
        hospital.getParam().put("fullAddress",provinceAddress+cityAdress+districtAddress+hospital.getAddress());
    }
}
