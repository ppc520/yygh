package com.ppc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ppc.yygh.hosp.repository.ScheduleRepository;
import com.ppc.yygh.hosp.service.ScheduleService;
import com.ppc.yygh.model.hosp.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void saveSchedule(Map<String, Object> map) {
        Schedule schedule = JSONObject.parseObject(JSONObject.toJSONString(map), Schedule.class);
        String hoscode = schedule.getHoscode();
        String depcode = schedule.getDepcode();
        String hosScheduleId = schedule.getHosScheduleId();

        Schedule platformSchedule=scheduleRepository.findByHoscodeAndDepcodeAndHosScheduleId(hoscode,depcode,hosScheduleId);
        if (platformSchedule==null){
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            scheduleRepository.save(schedule);
        }else {
            schedule.setCreateTime(platformSchedule.getCreateTime());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(platformSchedule.getIsDeleted());
            schedule.setId(platformSchedule.getId());
            scheduleRepository.save(schedule);
        }
    }

    @Override
    public Page<Schedule> getSchedulePage(Map map) {
        Schedule schedule=new Schedule();
        schedule.setHoscode((String) map.get("hoscode"));

        Example<Schedule> example=Example.of(schedule);

        Pageable pageable= PageRequest.of(Integer.parseInt((String)map.get("page"))-1
                ,Integer.parseInt((String)map.get("limit"))
                ,Sort.by("createTime").ascending());
        Page<Schedule> all = scheduleRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void remove(Map map) {
        String hoscode =(String) map.get("hoscode");
        String hosScheduleId =(String) map.get("hosScheduleId");
        Schedule schedule = scheduleRepository.findByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if (schedule!=null){
            scheduleRepository.delete(schedule);
        }
    }
}
