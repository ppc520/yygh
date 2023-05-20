package com.ppc.yygh.cmn.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentListener extends AnalysisEventListener<Student> {

    List<Student> list = new ArrayList<>();
    private static final int BATCH_COUNT = 10;

    //每解析一个excel的表头时触发
    @Override
    public void invokeHeadMap(Map headMap, AnalysisContext context) {
        System.out.println("headMap = " + headMap);
    }

    //每解析一行数据，都会调用一次invoke方法
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        list.add(student);
        if (list.size() >= BATCH_COUNT) {
//            baseMapper.batchInsert(list);
            list.clear();
        }
    }

    //结束后执行,可以关闭链接等
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        baseMapper.batchInsert(list);
    }
}
