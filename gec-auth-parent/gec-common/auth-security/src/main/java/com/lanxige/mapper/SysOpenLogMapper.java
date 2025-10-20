package com.lanxige.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lanxige.model.system.SysOpenLog;
import com.lanxige.model.vo.SysOperLogQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOpenLogMapper extends BaseMapper<SysOpenLog> {
    IPage<SysOpenLog> selectPage(IPage<SysOpenLog> iPage, @Param("vo") SysOperLogQueryVo sysOperLogQueryVo);
}
