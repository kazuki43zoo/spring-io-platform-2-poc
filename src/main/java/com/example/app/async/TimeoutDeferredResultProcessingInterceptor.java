package com.example.app.async;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptorAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by btshimizukza on 2016/05/11.
 */
public class TimeoutDeferredResultProcessingInterceptor extends DeferredResultProcessingInterceptorAdapter {

    @Override
    public <T> boolean handleTimeout(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        deferredResult.setResult((T)"common/error/timeoutError");
//        HttpServletResponse servletResponse = request.getNativeResponse(HttpServletResponse.class);
//        if (!servletResponse.isCommitted()) {
//            servletResponse.sendError(HttpStatus.SERVICE_UNAVAILABLE.value());
//        }
        return false;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public <T> void postProcess(NativeWebRequest request, DeferredResult<T> deferredResult,
                                Object concurrentResult) throws Exception {
        if(concurrentResult instanceof Throwable){
            deferredResult.setResult((T)"common/error/systemError");
        }

        System.out.println("★★★★★★postProcess"+concurrentResult);
    }

    /**
     * This implementation is empty.
     */
    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest request, DeferredResult<T> deferredResult)
            throws Exception {
        System.out.println("★★★★★★beforeConcurrentHandling");
    }

    /**
     * This implementation is empty.
     */
    @Override
    public <T> void preProcess(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        System.out.println("★★★★★★preProcess");
    }


    /**
     * This implementation is empty.
     */
    @Override
    public <T> void afterCompletion(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        System.out.println("★★★★★★afterCompletion");
    }

}