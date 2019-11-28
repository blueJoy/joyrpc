package io.joyrpc.transport.channel;

/*-
 * #%L
 * joyrpc
 * %%
 * Copyright (C) 2019 joyrpc.io
 * %%
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
 * #L%
 */

import io.joyrpc.transport.session.Session;
import io.joyrpc.util.Timer;

import java.util.concurrent.CompletableFuture;

/**
 * 增强的CompletableFuture
 *
 * @date: 2019/5/9
 */
public class EnhanceCompletableFuture<I, M> extends CompletableFuture<M> {
    /**
     * 消息ID
     */
    protected final I messageId;
    /**
     * 会话
     */
    protected final Session session;
    /**
     * 超时时间
     */
    protected final Timer.Timeout timeout;
    /**
     * 扩展属性
     */
    protected Object attr;

    /**
     * 构造函数
     *
     * @param messageId
     * @param session
     * @param timeout
     */
    public EnhanceCompletableFuture(final I messageId, final Session session, final Timer.Timeout timeout) {
        this.messageId = messageId;
        this.session = session;
        this.timeout = timeout;
    }

    public I getMessageId() {
        return messageId;
    }

    public Session getSession() {
        return session;
    }

    public Timer.Timeout getTimeout() {
        return timeout;
    }

    public Object getAttr() {
        return attr;
    }

    public void setAttr(Object attr) {
        this.attr = attr;
    }

    /**
     * 放弃过期检查任务
     */
    public void cancel() {
        if (timeout != null && !timeout.isExpired()) {
            timeout.cancel();
        }
    }

    /**
     * 放弃过期检查任务
     *
     * @param exception
     */
    public void cancel(Throwable exception) {
        cancel();
        completeExceptionally(exception);
    }

}
