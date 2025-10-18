package com.lanxige.service;

public interface SysLoginService {
    public void recordLoginLog(String username, Integer status, String ipaddr, String message);
}
