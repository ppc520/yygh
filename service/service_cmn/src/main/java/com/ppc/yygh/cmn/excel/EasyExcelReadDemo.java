package com.ppc.yygh.cmn.excel;

import com.alibaba.excel.EasyExcel;

public class EasyExcelReadDemo {
    public static void main(String[] args) {
        EasyExcel.read("C:\\Users\\ppc\\Desktop\\hello.xlsx",Student.class,new StudentListener())
                .sheet(0)
                .doRead();

    }
}
