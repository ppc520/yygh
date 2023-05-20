package com.ppc.yygh.cmn.controller;


import com.atguigu.yygh.model.cmn.Dict;
import com.ppc.yygh.cmn.service.DictService;
import com.ppc.yygh.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author ppc
 * @since 2023-05-19
 */
@CrossOrigin
@RestController
@Api(tags = "数据字典")
@RequestMapping("/admin/cmn")
public class DictController {
    @Autowired
    private DictService dictService;
    @ApiOperation("获取初始字典")
    @GetMapping("/childList/{pid}")
    public R getChildListByParentId(@PathVariable Long pid){
        List<Dict> list= dictService.getChildByPid(pid);
        return R.ok().data("items",list);
    }
}

