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
package com.photowey.plugin.httpz.okhttp.executor;

import com.photowey.plugin.httpz.okhttp.context.RequestContext;
import com.photowey.plugin.httpz.okhttp.enums.HttpMethod;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * {@code OkhttpRequestExecutor}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public interface OkhttpRequestExecutor extends RequestExecutor {

    Request.Builder populateRequest(HttpMethod httpMethod, RequestContext context);

    Response executeRequest(OkHttpClient okHttpClient, Request request) throws IOException;

    void executeRequest(OkHttpClient okHttpClient, Request request, Consumer<Response> callback) throws IOException;

}
