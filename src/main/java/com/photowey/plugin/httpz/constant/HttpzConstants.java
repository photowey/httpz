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
package com.photowey.plugin.httpz.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * {@code HttpzConstants}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class HttpzConstants {

    public static String EMPTY_STRING = "";

    public static String RequestMapping = "org.springframework.web.bind.annotation.RequestMapping";

    public static String GetMapping = "org.springframework.web.bind.annotation.GetMapping";

    public static String PostMapping = "org.springframework.web.bind.annotation.PostMapping";

    public static String PutMapping = "org.springframework.web.bind.annotation.PutMapping";

    public static String DeleteMapping = "org.springframework.web.bind.annotation.DeleteMapping";

    public static String PatchMapping = "org.springframework.web.bind.annotation.PatchMapping";

    public static String RequestBody = "org.springframework.web.bind.annotation.RequestBody";

    public static String RequestParam = "org.springframework.web.bind.annotation.RequestParam";

    public static String RequestHeader = "org.springframework.web.bind.annotation.RequestHeader";

    public static String RequestAttribute = "org.springframework.web.bind.annotation.RequestAttribute";

    public static String PathVariable = "org.springframework.web.bind.annotation.PathVariable";

    public static Set<String> BasicType = new HashSet<>();
    public static Set<String> RequestMappings = new HashSet<>();
    public static Set<String> RequestMethods = new HashSet<>();

    static {
        BasicType.add("int");
        BasicType.add("boolean");
        BasicType.add("byte");
        BasicType.add("short");
        BasicType.add("long");
        BasicType.add("float");
        BasicType.add("double");
        BasicType.add("char");
        BasicType.add("Boolean");
        BasicType.add("Byte");
        BasicType.add("Short");
        BasicType.add("Integer");
        BasicType.add("Long");
        BasicType.add("Float");
        BasicType.add("Double");
        BasicType.add("String");
        BasicType.add("Date");
        BasicType.add("BigDecimal");
        BasicType.add("Map");
        BasicType.add("HashMap");

        RequestMappings.add(RequestMapping);
        RequestMappings.add(GetMapping);
        RequestMappings.add(PostMapping);
        RequestMappings.add(PutMapping);
        RequestMappings.add(DeleteMapping);
        RequestMappings.add(PatchMapping);

        RequestMethods.add("GET");
        RequestMethods.add("POST");
        RequestMethods.add("PUT");
        RequestMethods.add("PATCH");
        RequestMethods.add("DELETE");
    }

    public boolean validateMethod(String method) {
        return RequestMethods.stream().map((v) -> v.equalsIgnoreCase(method)).limit(1).count() > 0;
    }

}
