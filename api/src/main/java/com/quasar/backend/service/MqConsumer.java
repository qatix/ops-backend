package com.quasar.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class MqConsumer {
    private DefaultMQPushConsumer consumer;

    public static final String TOPIC = "test_topic";
    public static final String TAGS = "TagA || TagC || TagD";

    public MqConsumer() {
        consumer = new DefaultMQPushConsumer("group-name");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    }

    @PostConstruct
    private void startConsume() throws MQClientException {
        log.info("start to consume");

        consumer.subscribe(TOPIC, TAGS);

        //设置一个Listener，主要进行消息的逻辑处理
        //注意这里使用的是MessageListenerOrderly这个接口
        consumer.registerMessageListener(new MessageListenerOrderly() {
            AtomicLong consumeTimes = new AtomicLong(0);

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(false);
                this.consumeTimes.incrementAndGet();

                log.info(Thread.currentThread().getName() + " Receive New Messages:" + msgs + "%n");
                log.info("message count:{}", msgs.size());
                for (MessageExt msg : msgs) {
                    log.info("msg item:{}", msg.toString());
                    String msgBody = new String(msg.getBody());
                    log.info("msg body:{}", msgBody);
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        log.info("Consumer Started");
    }


}
