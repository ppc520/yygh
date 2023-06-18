package com.ppc.yygh.hosp.controller.api;

import com.ppc.yygh.model.hosp.Hospital;
import com.ppc.yygh.common.exception.YyghException;
import com.ppc.yygh.hosp.result.Result;
import com.ppc.yygh.hosp.service.HospitalService;
import com.ppc.yygh.hosp.utils.HttpRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiHospitalController {
    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/hospital/show")
    public Result<Hospital> getHospitalInfo(HttpServletRequest httpServletRequest){
        Map<String, Object> map = HttpRequestHelper.switchMap(httpServletRequest.getParameterMap());
        String houseCode=(String) map.get("hoscode");
        Hospital hospital= hospitalService.getHospitalByHosCode(houseCode);
        return Result.ok(hospital);

    }
    @PostMapping("/saveHospital")
    public Result saveHosp(HttpServletRequest request){
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        String reqSignKey = (String) map.get("sign");
        String hoscode = (String) map.get("hoscode");
        String platformSignKey=hospitalService.getSignKeyByHoscode(hoscode);
        if(reqSignKey!=null&&platformSignKey!=null&&reqSignKey.equals(DigestUtils.md5DigestAsHex(platformSignKey.getBytes()))){
            String logoData = (String) map.get("logoData");
            map.put("logoData",logoData.replaceAll(" ","+"));
            hospitalService.saveHospital(map);
            return Result.ok();
        }else {
            throw new YyghException(20001,"保存失败");
        }

    }
}
