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
package com.photowey.plugin.httpz.ast.yml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photowey.plugin.httpz.ast.FileMap;
import com.photowey.plugin.httpz.ast.merge.MergeMap;
import com.photowey.plugin.httpz.ast.parser.YamlParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * {@code YmlParseTest}
 *
 * @author photowey
 * @date 2022/11/19
 * @since 1.0.0
 */
class YmlParseTest {

    @Test
    public void testBindBean() throws Exception {
        try (
                InputStream post = this.getClass().getClassLoader().getResourceAsStream("dev/httpz-post.yml");
                InputStream get = this.getClass().getClassLoader().getResourceAsStream("dev/httpz-get.yml");
        ) {

            FileMap fileMapPost = YamlParser.walk(post, FileMap.class);
            FileMap fileMapGet = YamlParser.walk(get, FileMap.class);

            Assertions.assertNotNull(fileMapPost);
            Assertions.assertNotNull(fileMapGet);

            Assertions.assertEquals("sharkchili", MergeMap.cascadeGet(fileMapPost.getHttpz().getContext(), "PASSWORD"));
            Assertions.assertNull(MergeMap.cascadeGet(fileMapPost.getHttpz().getContext(), "USERNAME"));

            ObjectMapper objectMapper = YamlParser.getObjectMapperInstance();

            // System.out.println(objectMapper.writeValueAsString(fileMapPost));
            // System.out.println(objectMapper.writeValueAsString(fileMapGet));

            // merge map

            MergeMap.walk(fileMapPost.getHttpz().getContext(), fileMapGet.getHttpz().getContext());
            fileMapPost.getHttpz().getRequests().forEach(r1 -> {
                FileMap.Request request = fileMapPost.getHttpz().getRequests()
                        .stream()
                        .filter((r) -> r.getProfile().equals(r1.getProfile()))
                        .findFirst().orElse(null);

                if (null != request) {
                    MergeMap.walk(r1.getHeaders(), request.getHeaders());
                    MergeMap.walk(r1.getParameters(), request.getParameters());
                    MergeMap.walk(r1.getBody(), request.getBody());
                }
            });

            // System.out.println(objectMapper.writeValueAsString(fileMapPost));

            Assertions.assertEquals("photowey", MergeMap.cascadeGet(fileMapPost.getHttpz().getContext(), "USERNAME"));
        }
    }

    @Test
    public void testBindMap() throws Exception {
        try (
                InputStream post = this.getClass().getClassLoader().getResourceAsStream("dev/httpz-post.yml");
        ) {

            Map<String, Object> ctx = YamlParser.walk(post);

            Assertions.assertNotNull(ctx);
            Assertions.assertEquals("photowey", MergeMap.cascadeGet(ctx, "httpz.context.USERNAME"));
            Assertions.assertNull(MergeMap.cascadeGet(ctx, "httpz.request.USERNAME"));
        }
    }
}
