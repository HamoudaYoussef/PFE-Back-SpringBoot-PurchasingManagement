package biz.picosoft.demo.client.config;

import biz.picosoft.demo.config.SecurityConstants;
import biz.picosoft.demo.config.slf4j.Slf4jMDCFilterConfiguration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes req = RequestContextHolder.getRequestAttributes();
        if (req != null) {
            HttpServletRequest request = ((ServletRequestAttributes) req).getRequest();
            if (request != null) {
                String token = request.getHeader(SecurityConstants.HEADER_STRING_AUTHORIZATION);
                template.header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token);
                String api = request.getHeader(SecurityConstants.HEADER_API_STRING);
                template.header(SecurityConstants.HEADER_API_STRING, api);
                String app = request.getHeader(SecurityConstants.HEADER_STRING_APPLICATION);
                String language = request.getHeader(SecurityConstants.HEADER_STRING_LANGUAGE);
                template.header(SecurityConstants.HEADER_STRING_AUTHORIZATION, token);
                template.header(SecurityConstants.HEADER_STRING_APPLICATION, app);
                template.header(SecurityConstants.HEADER_STRING_LANGUAGE, language);
            }
            HttpServletResponse response = ((ServletRequestAttributes) req).getResponse();
            if (response != null) {
                if (response.getHeaders(Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER) != null) {
                    String uuid = response.getHeaders(Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER).toString();
                    uuid = uuid.replace("[", "");
                    uuid = uuid.replace("]", "");
                    template.header(Slf4jMDCFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY, uuid);
                }
            }
        }
    }
}
