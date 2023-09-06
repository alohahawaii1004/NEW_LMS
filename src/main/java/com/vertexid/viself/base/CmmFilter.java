/*
 * @(#)CmmFilter.java     2020-10-30(030) 오전 8:07
 *
 * Copyright 2020 JAYU.space
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vertexid.viself.base;

import java.io.IOException;
import java.util.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

/**
 * <b>Description</b>
 * <pre>
 *     공통 필터
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
public class CmmFilter implements Filter {

    /**
     * logger
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Set<String> excludedUrls;

    private String tmpPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePattern = filterConfig.getInitParameter("excludedUrls");
        excludedUrls = new HashSet<>();
        excludedUrls.addAll(Arrays.asList(excludePattern.split(",")));
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try{
            String sysTimeMillis = String.valueOf(System.currentTimeMillis());
            request.setAttribute("sysTimeMillis", sysTimeMillis);

            String path = ((HttpServletRequest) request).getServletPath();
            tmpPath = path;
            Iterator<String> iterator = excludedUrls.iterator();
            boolean isWrappFlag = true;
            while (iterator.hasNext()) {
                String tmpPattern = iterator.next();
                if (antPathMatcher.match(tmpPattern, path)) {
                    isWrappFlag = false;
                    break;
                }
            }// end of while
            if(isWrappFlag){
                // Request Wrapper
                HttpServletRequestWrapper requestWrapper =
                        new CmmRequestWrapper(((HttpServletRequest) request));

                chain.doFilter(requestWrapper, response);
            }else{
                log.info("no wrapping path......."+path);
                chain.doFilter(request, response);
            }

        }catch(Exception e){
            log.error(e + ":"+tmpPath);
//            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void destroy() {

    }
}
