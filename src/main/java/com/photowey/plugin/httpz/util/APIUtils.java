/**
 * Copyright © 2022 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.plugin.httpz.util;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.util.PsiTreeUtil;
import com.photowey.plugin.httpz.constant.HttpzConstants;

/**
 * {@code APIUtils}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public final class APIUtils {

    private static final String EMPTY_STRING = "";
    private static final String ATTR_VALUE = "value";

    private APIUtils() {
        // utils class; can't create
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static String determinePath(PsiMethod psiMethod) {
        StringBuilder path = new StringBuilder();
        PsiClass psiClass = PsiTreeUtil.getParentOfType(psiMethod, PsiClass.class);
        if (psiClass == null) {
            return EMPTY_STRING;
        }

        // @RequestMapping
        PsiAnnotation classAnnotation = psiClass.getAnnotation(HttpzConstants.RequestMapping);
        if (classAnnotation != null) {
            path.append(parseAnnotationAttributeValue(classAnnotation, ATTR_VALUE));
        }

        // @RequestMapping
        // @GetMapping
        // @PostMapping
        // @PutMapping
        // @PatchMapping
        // @DeleteMapping
        for (PsiAnnotation annotation : psiMethod.getAnnotations()) {
            if (isMappingAnnotation(annotation)) {
                path.append(parseAnnotationAttributeValue(annotation, ATTR_VALUE));
                break;
            }
        }

        return path.toString();
    }

    public static String parseAnnotationAttributeValue(PsiAnnotation annotation, String attr) {
        PsiNameValuePair[] pairs = annotation.getParameterList().getAttributes();
        for (PsiNameValuePair pair : pairs) {
            if (pair.getAttributeName().equals(attr)) {
                return pair.getLiteralValue();
            }
        }

        return EMPTY_STRING;
    }

    public static boolean isMappingAnnotation(PsiAnnotation psiAnnotation) {
        return HttpzConstants.RequestMappings.contains(psiAnnotation.getQualifiedName());
    }
}
