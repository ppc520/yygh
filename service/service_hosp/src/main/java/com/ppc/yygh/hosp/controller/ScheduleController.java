package com.ppc.yygh.hosp.controller;

import com.ppc.yygh.hosp.result.Result;
import com.ppc.yygh.hosp.service.ScheduleService;
import com.ppc.yygh.hosp.utils.HttpRequestHelper;
import com.ppc.yygh.model.hosp.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        scheduleService.saveSchedule(map);
        return Result.ok();
    }
    @PostMapping("/schedule/list")
    public Result<Page> getSchedule(@RequestParam Map map){
        Page<Schedule> page= scheduleService.getSchedulePage(map);
        return Result.ok(page);
    }
    @PostMapping("/schedule/remove")
    public Result remove(@RequestParam Map map){
        scheduleService.remove(map);
        return Result.ok();
    }
}
