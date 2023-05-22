package com.ppc.yygh.hosp.controller;


import com.ppc.yygh.model.hosp.HospitalSet;
import com.ppc.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ppc.yygh.common.exception.YyghException;
import com.ppc.yygh.common.result.R;
import com.ppc.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author ppc
 * @since 2023-05-13
 */
@CrossOrigin
@RestController
@Api(tags = "医院设置信息")
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation("获取全部信息")
    @GetMapping("/getAll")
    public R getAll() {
        List<HospitalSet> list = null;
        try {
            //int i = 1 / 0;
            list = hospitalSetService.list();
        } catch (Exception e) {
            throw new YyghException(200013, "xxxx异常");
        }
        return R.ok().data("item", list);

    }

    @PostMapping("/page/{pageNum}/{size}")
    @ApiOperation("查询带条件和分页信息")
    public R getPageInfo(@ApiParam(name = "pageNum", value = "当前页") @PathVariable Integer pageNum,
                         @ApiParam(name = "size", value = "每页显示条数") @PathVariable Integer size,
                         @ApiParam("查询条件") @RequestBody HospitalSetQueryVo hospitalSetQueryVo) {

        Page<HospitalSet> page = new Page<HospitalSet>(pageNum, size);

        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<HospitalSet>();
        queryWrapper.like(hospitalSetQueryVo.getHosname() != null && hospitalSetQueryVo.getHosname() !="", "hosname", hospitalSetQueryVo.getHosname())
                .eq(hospitalSetQueryVo.getHoscode() != null && hospitalSetQueryVo.getHoscode() !="", "hoscode", hospitalSetQueryVo.getHoscode());

        hospitalSetService.page(page, queryWrapper);

        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增接口")
    public R save(@RequestBody HospitalSet hospitalSet) {
        hospitalSet.setStatus(1);
        //当前时间戳+随机数+MD5加密
        Random random = new Random();
        String temp = System.currentTimeMillis() + "" + random.nextInt(1000);
        hospitalSet.setSignKey(DigestUtils.md5DigestAsHex(temp.getBytes()));

        hospitalSetService.save(hospitalSet);
        return R.ok();
    }

    @ApiOperation("修改前先根据id回显数据")
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable Integer id) {
        return R.ok().data("item", hospitalSetService.getById(id));
    }

    @ApiOperation("修改数据")
    @PutMapping("/update")
    public R update(@RequestBody HospitalSet hospitalSet) {
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/batchDelete")
    public R batchDelete(@RequestBody List<Integer> ids) {
        hospitalSetService.removeBatchByIds(ids);
        return R.ok();
    }

    @ApiOperation("根据id删除")
    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable Long id){
        hospitalSetService.removeById(id);
        return R.ok();
    }

    @ApiOperation("锁定与解锁")
    @PutMapping("/status/{id}/{status}")
    public R updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setId(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

}

