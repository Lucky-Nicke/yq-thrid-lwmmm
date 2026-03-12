package com.lanxige.Req;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoRsp {
    private Long id;
    private String title;
    private String cover;
    private Date time;
}
