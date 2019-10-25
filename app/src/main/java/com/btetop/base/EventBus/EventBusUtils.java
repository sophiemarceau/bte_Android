package com.btetop.base.EventBus;

import com.btetop.message.RouteMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * 使用示例
 * Event<ABC> event=new Event<>(0,new ABC());
 * EventBusUtils.sendEvent(event);
 */
public class EventBusUtils {


    /**
     * 绑定 接受者
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 解绑定
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送消息(事件)
     *
     * @param event
     */
    public static void sendEvent(RouteMessage event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送 粘性 事件
     * <p>
     * 粘性事件，在注册之前便把事件发生出去，等到注册之后便会收到最近发送的粘性事件（必须匹配）
     * 注意：只会接收到最近发送的一次粘性事件，之前的会接受不到。
     *
     * @param event
     */
    public static void sendStickyEvent(EventMessage event) {
        EventBus.getDefault().postSticky(event);
    }
}
