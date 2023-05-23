package com.ppc.yygh.hosp.service;

import com.ppc.yygh.model.hosp.Schedule;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ScheduleService {
    void saveSchedule(Map<String,Object> map);

    Page<Schedule> getSchedulePage(Map map);

    void remove(Map map);
}
