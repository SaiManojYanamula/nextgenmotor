package com.nextgenmotor.nextgenmotor.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherServiceImpl implements MqttPublisherService {

    @Value("${mqtt.broker-url}")
    private String broker;

    @Value("${mqtt.topic-control}")
    private String controlTopic; // we set property below; fallback will be constant

    // fallback constants (in case you didn't set in yml)
    private static final String DEFAULT_CONTROL_TOPIC = "nextgen/motor";

    @Override
    public void publishMotorControl(boolean motorOn) {
        String topic = (controlTopic == null || controlTopic.isEmpty()) ? DEFAULT_CONTROL_TOPIC : controlTopic;
        String clientId = MqttClient.generateClientId();

        try (MqttClient client = new MqttClient(broker, clientId, null)) {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            // public broker: no username/password
            client.connect(options);
            System.out.println("Publisher connected to broker: " + broker + " id=" + clientId);

            String msg = motorOn ? "ON" : "OFF";
            MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
            mqttMessage.setQos(1);

            client.publish(topic, mqttMessage);
            System.out.println("Published to topic " + topic + " : " + msg);

            client.disconnect();
        } catch (Exception e) {
            System.err.println("Publish Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
