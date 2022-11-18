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
package com.photowey.plugin.httpz.okhttp.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code RequestHeaders}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class RequestHeaders {

    private ConcurrentHashMap<String, Object> ctx = new ConcurrentHashMap<>();

    public <T> RequestHeaders add(String key, T value) {
        ctx.put(key, value);

        return this;
    }

    public <T> RequestHeaders add(Map<String, T> params) {
        ctx.putAll(params);

        return this;
    }

    public <T> T get(String key) {
        return (T) ctx.get(key);
    }

    public <T> Map<String, T> get() {
        return (Map<String, T>) ctx;
    }

    public <T> T getHeader(String key) {
        return (T) ctx.get(key);
    }
}
