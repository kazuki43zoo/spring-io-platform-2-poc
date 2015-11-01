package com.example.app.exception;

import org.terasoluna.gfw.common.exception.SystemException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class ExceptionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String type = request.getParameter("type");
        if ("sysError".equalsIgnoreCase(type)) {
            throw new SystemException(type, "invalid messageId.");
        }
        if ("notFound".equalsIgnoreCase(type)) {
            throw new FileNotFoundException();
        }
        if ("nestedNotFound".equalsIgnoreCase(type)) {
            throw new RuntimeException(new CustomNotFoundException());
        }
        if ("error".equalsIgnoreCase(type)) {
            throw new Error("error!!");
        }
        if ("memoryError".equalsIgnoreCase(type)) {
            throw new OutOfMemoryError("error!!");
        }
        String status = request.getParameter("status");
        if (status != null){
            ((HttpServletResponse)response).sendError(Integer.valueOf(status));
            return;
        }
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
