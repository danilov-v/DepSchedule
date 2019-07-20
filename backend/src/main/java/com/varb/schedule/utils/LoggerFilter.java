//package com.varb.schedule.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.Collections;
//import java.util.stream.Stream;
//
///**
// * Spring Web filter for logging request and response.
// *
// * @author Hidetake Iwata
// * @see org.springframework.web.filter.AbstractRequestLoggingFilter
// * @see ContentCachingRequestWrapper
// * @see ContentCachingResponseWrapper
// */
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE + 10)
//@Slf4j
//public class LoggerFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (isAsyncDispatch(request)) {
//            filterChain.doFilter(request, response);
//        } else {
//            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
//        }
//    }
//
//    protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            if (log.isInfoEnabled())
//                logRequestHeader(request);
//
//            filterChain.doFilter(request, response);
//        } finally {
//            if (log.isInfoEnabled()) {
//                logRequestBody(request);
//                logResponse(response);
//            }
//            response.copyBodyToResponse();
//        }
//    }
//
//    private static void logRequestHeader(ContentCachingRequestWrapper request) {
//
//        String queryString = request.getQueryString();
//        if (queryString == null) {
//            log.info(">> Request: ({}){}", request.getMethod(), request.getRequestURI());
//        } else {
//            log.info(">> Request: ({}){}?{}", request.getMethod(), request.getRequestURI(), queryString);
//        }
//        log.info(">> Headers: ");
//        Collections.list(request.getHeaderNames()).forEach(headerName ->
//                Collections.list(request.getHeaders(headerName)).forEach(headerValue ->
//                        log.info(">> {} : {}", headerName, headerValue)));
//    }
//
//    private static void logRequestBody(ContentCachingRequestWrapper request) {
//        val content = request.getContentAsByteArray();
//        if (content.length > 0) {
//            logContent(">>", content, request.getContentType(), request.getCharacterEncoding());
//        }
//    }
//
//    private static void logResponse(ContentCachingResponseWrapper response) {
//        log.info("<< Response: ");
//        val status = response.getStatus();
//        log.info("<< {} {}", status, HttpStatus.valueOf(status).getReasonPhrase());
//        log.info("<< Headers: ");
//        response.getHeaderNames().forEach(headerName ->
//                response.getHeaders(headerName).forEach(headerValue ->
//                        log.info("<< {}: {}", headerName, headerValue)));
//
//        val content = response.getContentAsByteArray();
//        if (content.length > 0) {
//            logContent("<<", content, response.getContentType(), response.getCharacterEncoding());
//        }
//    }
//
//    private static void logContent(String prefix, byte[] content, String contentType, String contentEncoding) {
//        log.info(prefix + " Body: ");
//        try {
//            val contentString = new String(content, contentEncoding);
//            Stream.of(contentString.split("\r\n|\r|\n")).forEach(line -> log.info(prefix + " {}", line));
//        } catch (UnsupportedEncodingException e) {
//            log.info(prefix + "[{} bytes content]", content.length);
//        }
//    }
//
//    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
//        if (request instanceof ContentCachingRequestWrapper) {
//            return (ContentCachingRequestWrapper) request;
//        } else {
//            return new ContentCachingRequestWrapper(request);
//        }
//    }
//
//    private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
//        if (response instanceof ContentCachingResponseWrapper) {
//            return (ContentCachingResponseWrapper) response;
//        } else {
//            return new ContentCachingResponseWrapper(response);
//        }
//    }
//}