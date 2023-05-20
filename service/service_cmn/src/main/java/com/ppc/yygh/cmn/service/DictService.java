package com.ppc.yygh.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织架构表 服务类
 * </p>
 *
 * @author ppc
 * @since 2023-05-19
 */
public interface DictService extends IService<Dict> {

    List<Dict> getChildByPid(Long pid);
}
