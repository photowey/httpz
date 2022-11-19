/**
 * Copyright © 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.plugin.httpz.ast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code HttpzAst}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class HttpzAst implements Serializable {

    private static final long serialVersionUID = -4186349990947775250L;

    /**
     * httpz
     */
    private String command;
    /**
     * GET
     * POST
     * PUT
     * PATCH
     * DELETE
     */
    private String method;
    /**
     * --user-agent, -A
     * -A "httpz/1.0.0"
     */
    private String ua;
    /**
     * -H "Content-Type: application/json"
     */
    private String contentType;
    /**
     * --data, -d
     */
    private String body;
    /**
     * --config, -c
     * -c httpz-conf.yml
     */
    private String config;
    /**
     * --file, -f
     * -f httpz.yml
     */
    private String file;
    /**
     * --output, -o
     * -o httpbinpost.json
     */
    private String output;

    // ----------------------------------------------------------------

    /**
     * --header, -H
     * -H "K:V"
     */
    private List<Header> headers = new ArrayList<>();
    /**
     * --query, -q
     * -q "name:${USERNAME}"
     */
    private List<Parameter> parameters = new ArrayList<>();
    /**
     * -c httpz-conf.yml
     */
    private Map<String, String> configMap = new HashMap<>();
    /**
     * -f httpz.yml
     */
    private Map<String, String> fileMap = new HashMap<>();

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    public Map<String, String> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, String> fileMap) {
        this.fileMap = fileMap;
    }
}
