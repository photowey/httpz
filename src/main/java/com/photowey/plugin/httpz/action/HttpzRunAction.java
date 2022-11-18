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
package com.photowey.plugin.httpz.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.photowey.plugin.httpz.App;
import com.photowey.plugin.httpz.ast.HttpzAst;
import com.photowey.plugin.httpz.config.HttpzPluginConfigure;
import com.photowey.plugin.httpz.formatter.Formatter;
import com.photowey.plugin.httpz.lang.HttpzAnnotator;
import com.photowey.plugin.httpz.okhttp.context.RequestContext;
import com.photowey.plugin.httpz.okhttp.executor.RequestExecutor;
import com.photowey.plugin.httpz.tool.ConsoleOutputToolWindow;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * {@code HttpzRunAction}
 *
 * @author photowey
 * @date 2022/11/15
 * @since 1.0.0
 */
public class HttpzRunAction extends AnAction {

    private final PsiElement info;

    public HttpzRunAction(PsiElement info) {
        this.info = info;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        this.trycURL(event);
        //this.tryOkhttp(event);
    }

    public void trycURL(@NotNull AnActionEvent event) {
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        Document document = editor.getDocument();
        String cmd = this.tryBlockParse(document);

        this.debugger(cmd);

        try {
            cmd = cmd.replaceAll("HTTPZ", "CURL -X");

            Process process = Runtime.getRuntime().exec(cmd);
            String output = this.readOutput(process.getInputStream());

            this.debugger(cmd, output);

            ConsoleOutputToolWindow.show(event.getProject(), cmd, output);
        } catch (IOException ex) {
            this.doNotify("Print", "Execution failed", ex.getMessage(), NotificationType.ERROR);
        }
    }

    public void tryOkhttp(@NotNull AnActionEvent event) {
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        Document document = editor.getDocument();
        String cmd = this.tryBlockParse(document);

        HttpzAst ast = this.populateAst(cmd);

        RequestContext context = this.populateRequestContext(ast);

        HttpzPluginConfigure httpzPluginConfigure = App.getInstance().getHttpzPluginConfigure();
        RequestExecutor requestExecutor = httpzPluginConfigure.getRequestExecutor();
        try (Response response = requestExecutor.executeResponse(context)) {
            String output = this.parseResponse(response);

            ConsoleOutputToolWindow.show(event.getProject(), cmd, output);
        }
    }

    private void debugger(String cmd) {

    }

    private void debugger(String cmd, String output) {

    }

    private HttpzAst populateAst(String cmd) {
        return new HttpzAst();
    }

    private RequestContext populateRequestContext(HttpzAst ast) {
        // TODO
        return new RequestContext();
    }

    @NotNull
    private String readOutput(InputStream input) throws IOException {
        InputStreamReader isr = new InputStreamReader(input);
        LineNumberReader reader = new LineNumberReader(isr);
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        return this.tryFormatIfNecessary(output.toString());
    }

    @NotNull
    private String parseResponse(Response response) {
        StringBuilder output = new StringBuilder();

        return this.tryFormatIfNecessary(output.toString());
    }

    private String tryFormatIfNecessary(String data) {
        return Formatter.tryFormat(data);
    }

    // @formatter:off
    private void doNotify(
            @NotNull String groupId,
            @NotNull @NlsContexts.NotificationTitle String title,
            @NotNull @NlsContexts.NotificationContent String content,
            @NotNull NotificationType type) {
        Notifications.Bus.notify(new Notification(groupId, title, content, type));
    }
    // @formatter:on

    private String tryBlockParse(Document document) {
        int offset = info.getTextOffset();
        int lineNumber = document.getLineNumber(offset);
        int startOffset = document.getLineStartOffset(lineNumber);
        int endOffset = document.getLineEndOffset(lineNumber);

        String cmd = document.getText(new TextRange(startOffset, endOffset));
        String copy = this.replaceNewLine(cmd);
        StringBuilder ctx = new StringBuilder(copy);
        int counter = 0;

        try {
            while (StringUtils.isNotBlank(cmd) && (!cmd.startsWith(HttpzAnnotator.HTTPZ_PREFIX_STR) || counter == 0)) {
                lineNumber++;
                counter++;
                startOffset = document.getLineStartOffset(lineNumber);
                endOffset = document.getLineEndOffset(lineNumber);

                cmd = document.getText(new TextRange(startOffset, endOffset));
                cmd = this.replaceNewLine(cmd).replaceAll("^ *", "");

                if (StringUtils.isNotBlank(cmd)) {
                    ctx.append(HttpzAnnotator.HTTPZ_SEPARATOR_STR).append(cmd);
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

        cmd = ctx.toString();

        if (StringUtils.isBlank(cmd) || cmd.trim().equals(HttpzAnnotator.HTTPZ_PREFIX_STR)) {
            cmd = HttpzAnnotator.HTTPZ_HELP_STR;
        }

        return cmd;
    }

    @NotNull
    private String replaceNewLine(String cmd) {
        return cmd.replaceAll(" *\\\\$", "");
    }
}
