package com.lanxige.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {
    public static void out(HttpServletResponse response, Result result) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        try {
            String jsonStr = JSON.toJSONString(result);
            response.getWriter().write(jsonStr);
            response.getWriter().flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
