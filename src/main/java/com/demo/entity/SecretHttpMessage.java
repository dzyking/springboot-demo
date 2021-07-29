package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.InputStream;

/**
 * @author dzy
 * @date 2021/7/23
 * @desc
 */
@AllArgsConstructor
@NoArgsConstructor
public class SecretHttpMessage implements HttpInputMessage {
    private InputStream body;
    private HttpHeaders httpHeaders;

    @Override
    public InputStream getBody() {
        return this.body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }
}
