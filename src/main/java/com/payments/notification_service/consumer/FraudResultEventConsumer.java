package com.payments.notification_service.consumer;

import com.payments.notification_service.event.FraudResultEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FraudResultEventConsumer {

    @KafkaListener(topics = "fraud.results", groupId = "notification-service-fraud",
            containerFactory = "fraudKafkaListenerContainerFactory")
    public void onFraudResult(FraudResultEvent event) {
        if(event.eventType().equals("FRAUD_FLAGGED")) {
            System.out.println("[NOTIFICATION] ALERT -> Risk Team: Payment"
                + event.paymentId()
                + " flagged as " + event.riskLevel() + " risk"
                + " (score=" + event.riskScore()
                + " Reason: " + event.reason());

            System.out.println("[NOTIFICATION] SMS -> Sender: Your payment of "
                + event.amount() + " " + event.currency()
                + " is under review. Ref: " + event.paymentId());
        }
    }
}
