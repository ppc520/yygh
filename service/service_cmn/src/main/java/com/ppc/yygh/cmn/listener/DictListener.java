package com.ppc.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ppc.yygh.cmn.mapper.DictMapper;
import com.ppc.yygh.cmn.service.DictService;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DictListener extends AnalysisEventListener<DictEeVo> {
    private DictService dictService;
    private List<Dict> dictList=new ArrayList<>();
    public DictListener(DictService dictService){
        this.dictService=dictService;
    }
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict=new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        QueryWrapper<Dict> wrapper=new QueryWrapper<Dict>();
        wrapper.eq("id",dict.getId());
        List<Dict> list = dictService.list(wrapper);
        if (list.size()==0){//判断是否已存在，如果未存在，就加入到list，如果已存在，就更新这条数据
            dictList.add(dict);
            if (dictList.size()>=20){
                dictService.saveBatch(dictList);
                dictList.clear();
            }
        }else {
            dictService.updateById(dict);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        dictService.saveBatch(dictList);
    }
}
