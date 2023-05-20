package com.ppc.yygh.cmn.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @ExcelProperty("学号")
    private String stuNo;
    @ExcelProperty("姓名")
    private String stuName;
    @ExcelProperty("性别")
    private String sex;
}
