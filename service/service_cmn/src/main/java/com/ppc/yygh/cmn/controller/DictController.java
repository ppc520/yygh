package com.ppc.yygh.cmn.controller;


import com.ppc.yygh.model.cmn.Dict;
import com.ppc.yygh.cmn.service.DictService;
import com.ppc.yygh.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
            dictService.download(response);
    }
    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {//变量名file必须和前端upload组件的name属性一致
        dictService.upload(file);
        return R.ok();
    }
}

