package com.zhuanglide.micrboot.mvc.resolver.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuanglide.micrboot.http.HttpContextRequest;
import com.zhuanglide.micrboot.http.HttpContextResponse;
import com.zhuanglide.micrboot.http.MediaType;
import com.zhuanglide.micrboot.mvc.ModelAndView;
import com.zhuanglide.micrboot.mvc.resolver.ViewResolver;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by wwj on 17/3/9.
 */
public class JsonViewResolver extends ViewResolver implements InitializingBean {
    public static final String DEFAULT_JSON_VIEW_NAME = "JSON_VIEW";
    private String viewName = DEFAULT_JSON_VIEW_NAME;
    private ObjectMapper objectMapper;

    @Override
    public ModelAndView resolve(Object result) {
        if(result instanceof ModelAndView){
            ModelAndView mv = (ModelAndView) result;
            if(null != mv.getViewName()){
                if(mv.getViewName().equals(viewName)){
                    return mv;
                }else if(MediaType.APPLICATION_JSON.isCompatibleWith(mv.getMediaType())){
                    return mv;
                }
            }else{
                return null;
            }
        }
        return null;
    }

    @Override
    public void render(ModelAndView mv, HttpContextRequest request, HttpContextResponse response) throws Exception {
        if(null != mv.getResult()) {
            response.setContent(objectMapper.writeValueAsString(mv.getResult()));
        }
        if (null == getContentType()) {
            setContentType(MediaType.TEXT_PLAIN_VALUE);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(null == objectMapper){
            objectMapper = new ObjectMapper();
        }
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
