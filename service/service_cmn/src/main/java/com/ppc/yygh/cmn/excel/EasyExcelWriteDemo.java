package com.ppc.yygh.cmn.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelWriteDemo {
    public static void main(String[] args) {
//        //往单个sheet中写数据
//        List<Student> list=new ArrayList<>();
//        list.add(new Student("5jjj","ppc","1llll"));
//        EasyExcel.write("C:\\Users\\ppc\\Desktop\\hello.xlsx",Student.class).sheet("学生列表一").doWrite(list);
        //往多个sheet中写数据
        List<Student> list = new ArrayList<>();
        list.add(new Student("5jjj", "ppc", "1llll"));

        ExcelWriter excelWriter = EasyExcel.write("C:\\Users\\ppc\\Desktop\\hello.xlsx", Student.class).build();
        WriteSheet writeSheet0 = EasyExcel.writerSheet(0).build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet(1).build();
        WriteSheet writeSheet2 = EasyExcel.writerSheet(2).build();
        excelWriter.write(list,writeSheet0);
        excelWriter.write(list,writeSheet1);
        excelWriter.write(list,writeSheet2);
        excelWriter.finish();

    }
}
