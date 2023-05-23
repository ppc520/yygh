package com.ppc.yygh.hosp.controller;

import com.ppc.yygh.hosp.repository.DepartmentRepository;
import com.ppc.yygh.hosp.result.Result;
import com.ppc.yygh.hosp.service.DepartmentService;
import com.ppc.yygh.hosp.utils.HttpRequestHelper;
import com.ppc.yygh.model.hosp.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest httpServletRequest){
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(httpServletRequest.getParameterMap());
        departmentService.saveDepartment(stringObjectMap);

        return Result.ok();
    }
    @PostMapping("/department/list")
    public Result<Page> getDepartmentPage(@RequestParam Map map){
        Page<Department> page=departmentService.getDepartmentPage(map);
        return Result.ok(page);
    }
    @PostMapping("/department/remove")
    public Result remove(@RequestParam Map map){
        departmentService.remove(map);
        return Result.ok();
    }
}
