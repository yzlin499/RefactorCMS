package com.zhbit.cms.filter;

import com.alibaba.fastjson.JSONObject;
import com.zhbit.cms.frameclass.LoginUsers;
import com.zhbit.cms.frameclass.PermissionsParsing;
import com.zhbit.cms.frameclass.StatusCode;
import com.zhbit.cms.infobeans.UserInfo;
import com.zhbit.cms.tools.Tools;
import org.apache.catalina.connector.RequestFacade;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PermissionsFilter implements Filter {
    private PermissionsParsing parsing=PermissionsParsing.getInstance();
    private LoginUsers loginUsers=LoginUsers.getInstance();
    public void destroy() {

    }




    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        chain.doFilter(request,response);

        String pathInfo=((HttpServletRequest)request).getServletPath();

        HttpSession httpSession=((HttpServletRequest) request).getSession();
        if(loginUsers.isLogin(httpSession)){
            UserInfo userInfo=(UserInfo) httpSession.getAttribute("userInfo");
            if(userInfo!=null){
                int level=parsing.getLevel(userInfo.getPersonGroup(),pathInfo);
                if(level>userInfo.getPersonCtrlLevel()){
                    response.getWriter().print(Tools.quickJSON(StatusCode.FORBIDDEN,"forbidden"));
                }else{
                    chain.doFilter(request,response);
                }
            }else{
                int level=parsing.getLevel(0,pathInfo);
                if(level==0){
                    chain.doFilter(request,response);
                }
            }
        }else{
            int level=parsing.getLevel(0,pathInfo);
            if(level==0){
                chain.doFilter(request,response);
            }else{
                response.getWriter().print(Tools.quickJSON(StatusCode.FORBIDDEN,"login fail"));
            }
        }
    }
}
