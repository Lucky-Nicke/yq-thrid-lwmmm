package com.lanxige.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.Rsp.UserTrendRsp;
import com.lanxige.mapper.SysUserMapper;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.vo.UserTrendVo;
import com.lanxige.service.SysUserDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysUserDateServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserDateService {
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户id查询角色id
     *
     * @return 用户数据
     */
    @Override
    public UserTrendRsp getRoleIdsByUserId() {
        List<UserTrendVo> list = sysUserMapper.selectUserTrend();

        // 转成 Map<日期, 数量>
        Map<LocalDate, Integer> map = list.stream()
                .collect(Collectors.toMap(
                        UserTrendVo::getDay,
                        UserTrendVo::getCount
                ));

        List<Integer> actualData = new ArrayList<>();

        // 最近7天
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            actualData.add(map.getOrDefault(date, 0));
        }

        // 查询总用户数
        Integer totalUserCount = sysUserMapper.selectTotalUserCount();

        // 封装返回
        UserTrendRsp rsp = new UserTrendRsp();
        rsp.setActualData(actualData);
        rsp.setTotalUserCount(totalUserCount);

        return rsp;
    }
}
