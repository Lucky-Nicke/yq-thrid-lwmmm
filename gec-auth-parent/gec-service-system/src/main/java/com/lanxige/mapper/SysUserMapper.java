package com.lanxige.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.vo.SysUserQueryVo;
import com.lanxige.model.vo.UserTrendVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Nicke
 * @since 2025-10-11
 */
@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(IPage<SysUser> iPage, @Param("vo") SysUserQueryVo sysUserQueryVo);

    /**
     * 查询用户趋势
     *
     * @return 用户数据
     */
    List<UserTrendVo> selectUserTrend();

    /**
     * 查询用户总数
     *
     * @return 用户总数
     */
    Integer selectTotalUserCount();
}
