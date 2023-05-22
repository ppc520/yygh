package com.ppc.yygh.cmn.service.impl;


import com.alibaba.excel.EasyExcel;
import com.ppc.yygh.model.cmn.Dict;
import com.ppc.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ppc.yygh.cmn.listener.DictListener;
import com.ppc.yygh.cmn.mapper.DictMapper;
import com.ppc.yygh.cmn.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author ppc
 * @since 2023-05-19
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Cacheable(value = "abc",keyGenerator = "keyGenerator")
    @Override
    public List<Dict> getChildByPid(Long pid) {
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<Dict>();
        queryWrapper.eq("parent_id",pid);
        List<Dict> dictList = baseMapper.selectList(queryWrapper);
        for (Dict dict : dictList) {
            dict.setHasChildren(isHasChildren(dict.getId()));
        }
        return dictList;
    }

    @Override
    public void download(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("数据字典", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

        //List<Dict> list = this.list();
        List<Dict> list = baseMapper.selectList(null);
        List<DictEeVo> dictEeVoList=new ArrayList<>(list.size());
        for (Dict dict : list) {
            DictEeVo dictEeVo=new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVoList.add(dictEeVo);
        }
        EasyExcel.write(response.getOutputStream(),DictEeVo.class).sheet(0).doWrite(dictEeVoList);
    }

    @CacheEvict(value = "abc",allEntries = true)
    @Override
    public void upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(this)).sheet(0).doRead();
    }

    private boolean isHasChildren(Long pid) {
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<Dict>();
        queryWrapper.eq("parent_id",pid);
        Long count = baseMapper.selectCount(queryWrapper);
        return count>0;
    }
}
