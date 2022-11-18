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
package com.photowey.plugin.httpz.lang;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * {@code HttpzAnnotator}
 *
 * @author photowey
 * @date 2022/11/18
 * @since 1.0.0
 */
public class HttpzAnnotator implements Annotator {
    public static final String HTTPZ_HELP_STR = "HTTPZ -h";
    public static final String HTTPZ_PREFIX_STR = "HTTPZ";
    public static final String HTTPZ_SEPARATOR_STR = " ";

    public static final String HTTPZ_NEW_LINE_STR = "\\";

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        // TODO
    }
}
