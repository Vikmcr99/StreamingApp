package com.example.my_streaming.Application.Account.Notification;

import java.util.concurrent.ExecutionException;
import com.azure.messaging.servicebus.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class NotifyAzureServiceBus {

    private static final String connectionString = "Endpoint=sb://my-streaming.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=UCS0sOHcAcyB9FtP62mpuAvA2/NlJtiBt+ASbJC83+s=";
    private static final String queueName = "notify_queue";

    public NotifyAzureServiceBus() { }

    public void sendMessage(Notify notify) throws JsonProcessingException {
        ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .queueName(queueName)
                .buildClient();

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(notify);

        ServiceBusMessage message = new ServiceBusMessage(body);
        sender.sendMessage(message);
    }


}




