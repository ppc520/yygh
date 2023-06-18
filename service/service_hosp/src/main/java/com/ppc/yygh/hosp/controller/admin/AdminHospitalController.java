package com.ppc.yygh.hosp.controller.admin;

import com.ppc.yygh.common.result.R;
import com.ppc.yygh.hosp.service.HospitalService;
import com.ppc.yygh.model.hosp.Hospital;
import com.ppc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hospital")
public class AdminHospitalController {
    @Autowired
    private HospitalService hospitalService;
    @GetMapping("/{pageNum}/{pageSize}")
    public R getHospitalList(@PathVariable Integer pageNum, @PathVariable Integer pageSize,HospitalQueryVo hospitalQueryVo){
        Page<Hospital> hospitalPage=hospitalService.getHospitalPage(pageNum,pageSize,hospitalQueryVo);
        return R.ok().data("total",hospitalPage.getTotalElements()).data("list",hospitalPage.getContent());
    }
    @PutMapping("/{id}/{status}")
    public R updateStatus(@PathVariable String id,@PathVariable Integer status){
        hospitalService.updateStatus(id,status);
        return R.ok();
    }
    @GetMapping("/detail/{id}")
    public R detailById(@PathVariable String id){
        Hospital hospital=hospitalService.detail(id);
        return R.ok().data("hospital",hospital);
    }

}
