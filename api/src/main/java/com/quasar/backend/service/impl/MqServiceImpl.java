package com.quasar.backend.service.impl;

import com.quasar.backend.service.MqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.UnsupportedEncodingException;

@Slf4j
@Service("mqService")
public class MqServiceImpl implements MqService {

    private DefaultMQProducer producer;

    public MqServiceImpl() {
        producer = new DefaultMQProducer("Async-producer");
        producer.setNamesrvAddr("localhost:9876");
        producer.setRetryTimesWhenSendAsyncFailed(0);
        log.info("mqservice construct");
    }

    @PostConstruct
    private void startUp() throws MQClientException {
        producer.start();
        log.info("mqservice startup");
    }


    @Override
    public boolean sendTask(String topic, String tags, String content) {
        try {
            Message message = new Message(topic, tags,
                    content.getBytes(RemotingHelper.DEFAULT_CHARSET));
//            log.info("before send:{}", message.toString());
            producer.sendOneway(message);
            log.info("MSG ID:{}", message.getProperty("UNIQ_KEY"));

            return true;
        } catch (UnsupportedEncodingException | InterruptedException | RemotingException | MQClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendTaskAsync(String topic, String tags, String content) {
        try {
            Message message = new Message(topic, tags,
                    content.getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("OK {}", sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    log.error("Exception:", e);
                    e.printStackTrace();
                }
            });
        } catch (UnsupportedEncodingException | InterruptedException | RemotingException | MQClientException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destory() {
        System.out.println("mqservice shutdown...");
        producer.shutdown();
    }

}
