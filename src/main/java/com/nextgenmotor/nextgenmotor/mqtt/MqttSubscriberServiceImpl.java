package com.nextgenmotor.nextgenmotor.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nextgenmotor.nextgenmotor.model.MotorStatus;
import com.nextgenmotor.nextgenmotor.repository.MotorStatusRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MqttSubscriberServiceImpl implements MqttSubscriberService {
	
    @Autowired
    private MotorStatusRepository repository;

    @Value("${mqtt.broker-url}")
    private String broker;

    @Value("${mqtt.topic-control}")   // FIXED: listen on control topic
    private String controlTopic;

    private static final String DEFAULT_CONTROL_TOPIC = "nextgen/motor";

    @PostConstruct
    @Override
    public void subscribeToMotorData() {

        String topic = (controlTopic == null || controlTopic.isEmpty())
                ? DEFAULT_CONTROL_TOPIC
                : controlTopic;

        try {
            String clientId = MqttClient.generateClientId();
            MqttClient client = new MqttClient(broker, clientId, null);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            client.connect(options);
            System.out.println("Subscriber connected to broker: " + broker);

            client.subscribe(topic, (t, message) -> {
                String payload = new String(message.getPayload()).trim();
                System.out.println("Received control: " + payload);

                MotorStatus existing = repository.findTopByOrderByLastUpdatedTimeDesc();
                if (existing == null) {
                    existing = new MotorStatus();
                }

                if (payload.equalsIgnoreCase("ON")) {
                    existing.setMotorOn(true);
                } else if (payload.equalsIgnoreCase("OFF")) {
                    existing.setMotorOn(false);
                }

                existing.setLastUpdatedTime(System.currentTimeMillis());
                repository.save(existing);

                System.out.println("DB Updated: motorOn=" + existing.isMotorOn());
            });

            System.out.println("Subscribed to topic: " + topic);

        } catch (Exception e) {
            System.err.println("Subscriber Error: " + e.getMessage());
        }
    }
}
