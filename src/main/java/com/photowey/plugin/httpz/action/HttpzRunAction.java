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
package com.photowey.plugin.httpz.action;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.intellij.execution.ui.ConsoleViewContentType;
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
import com.photowey.plugin.httpz.okhttp.context.RequestContext;
import com.photowey.plugin.httpz.okhttp.executor.RequestExecutor;
import com.photowey.plugin.httpz.tool.ConsoleOutputToolWindow;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Objects;

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
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        Document document = editor.getDocument();
        int offset = info.getTextOffset();
        int lineNumber = document.getLineNumber(offset);
        int startOffset = document.getLineStartOffset(lineNumber);
        int endOffset = document.getLineEndOffset(lineNumber);
        String cmd = document.getText(new TextRange(startOffset, endOffset));
        if (Objects.equals(cmd, "")) {
            return;
        }

        HttpzAst ast = this.populateAst(cmd);

        RequestContext context = this.populateRequestContext(ast);

        HttpzPluginConfigure httpzPluginConfigure = App.getInstance().getHttpzPluginConfigure();
        RequestExecutor requestExecutor = httpzPluginConfigure.getRequestExecutor();
        try (Response response = requestExecutor.executeResponse(context)) {
            String output = this.parseResponse(response);

            //this.doNotify("Print", "Run result", output, NotificationType.INFORMATION);
            ConsoleOutputToolWindow.getConsoleView(event.getProject()).print(output, ConsoleViewContentType.NORMAL_OUTPUT);
        }
    }

    private HttpzAst populateAst(String cmd) {
        return new HttpzAst();
    }

    private RequestContext populateRequestContext(HttpzAst ast) {
        // TODO
        return new RequestContext();
    }

    @NotNull
    private String parseResponse(Response response) {
        StringBuilder output = new StringBuilder();

        return this.tryFormatIfNecessary(output.toString());
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

    private String tryFormatIfNecessary(String data) {
        if (JSON.isValid(data)) {
            JSONObject body = JSON.parseObject(data, JSONObject.class);
            return JSON.toJSONString(body, JSONWriter.Feature.PrettyFormat);
        } else if (JSON.isValidArray(data)) {
            JSONArray body = JSON.parseObject(data, JSONArray.class);
            return JSON.toJSONString(body, JSONWriter.Feature.PrettyFormat);
        }

        return data;
    }

    private void doNotify(
            @NotNull String groupId,
            @NotNull @NlsContexts.NotificationTitle String title,
            @NotNull @NlsContexts.NotificationContent String content,
            @NotNull NotificationType type) {
        Notifications.Bus.notify(new Notification(groupId, title, content, type));
    }
}
