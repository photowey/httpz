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
package com.photowey.plugin.httpz.lang.psi;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.util.ProcessingContext;
import com.photowey.plugin.httpz.icon.HttpzIcons;
import com.photowey.plugin.httpz.lang.HttpzFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * {@code HttpzCommandCodeContributor}
 *
 * @author photowey
 * @date 2022/11/18
 * @since 1.0.0
 */
public class HttpzCommandCodeContributor extends CompletionContributor {

    // @formatter:off
    private final static List<String> completionItems = List.of(
            "httpz", "HTTPZ",
            "--header", "-H",
            "--data", "-d",
            "--query", "-q",
            "--user-agent", "-A",

            "--env", "-e",
            "--profile", "-p",
            "--config", "-c",
            "--file", "-f",

            // TODO support or not?
            "--output", "-o",

            "http://", "https://",

            "GET", "POST", "PUT", "PATCH ", "DELETE"
    );
    // @formatter:on

    public HttpzCommandCodeContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().inFile(StandardPatterns.instanceOf(HttpzFile.class)),
                new HttpzCommandCompletionProvider()
        );
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        int offset = parameters.getOffset();
        Document document = parameters.getEditor().getDocument();
        int lineStartOffset = document.getLineStartOffset(document.getLineNumber(offset));
        String prefix = document.getText(TextRange.create(lineStartOffset, offset));
        int lastSpace = prefix.lastIndexOf(' ') == -1 ? 0 : prefix.lastIndexOf(' ') + 1;
        super.fillCompletionVariants(parameters, result.withPrefixMatcher(prefix.substring(lastSpace)));
    }

    static class HttpzCommandCompletionProvider extends CompletionProvider<CompletionParameters> {
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context,
                                      @NotNull CompletionResultSet result) {
            for (String item : completionItems) {
                result.addElement(LookupElementBuilder
                        .create(item)
                        .withPresentableText(item)
                        .withTailText(item)
                        .bold()
                        .withTypeText(item)
                        .withIcon(HttpzIcons.ICON)
                );
            }
        }
    }
}
