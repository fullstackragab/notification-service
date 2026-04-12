package com.payments.notification_service.consumer;

import com.payments.notification_service.event.PaymentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {

    @KafkaListener(
            topics = "payment.events",
            groupId = "notification-service",
            containerFactory = "paymentKafkaListenerContainerFactory"
    )
    public void onPaymentEvent(PaymentEvent event) {
        switch (event.eventType()) {
            case "PAYMENT_INITIATED" -> System.out.println("[NOTIFICATION] SMS -> Sender "
                    + event.senderAccountId()
                    + ": Your payment of " + event.amount() + " " + event.currency()
                    + " has been initiated. Ref: " + event.paymentId());

            case "PAYMENT_SETTLED" -> System.out.println("[NOTIFICATION] SMS -> Sender "
                    + event.senderAccountId()
                    + ": Your payment of " + event.amount() + " " + event.currency()
                    + " has been settled. Ref: " + event.paymentId());

            case "PAYMENT_FAILED" -> System.out.println("[NOTIFICATION] SMS -> Sender "
                    + event.senderAccountId()
                    + ": Your payment of " + event.amount() + " " + event.currency()
                    + " has FAILED. Ref: " + event.paymentId()
                    + " - please contact support.");

            default ->
                System.out.println("[NOTIFICATION] Unhandled event type: "
                    + event.eventType() + " for payment " + event.paymentId());
        }
    }
}
