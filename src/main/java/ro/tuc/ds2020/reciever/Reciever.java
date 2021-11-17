package ro.tuc.ds2020.reciever;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.SensorDataDTO;
import ro.tuc.ds2020.repositories.SensorDataRepository;
import ro.tuc.ds2020.services.SensorDataService;

import java.util.UUID;

@Component
@Slf4j
public class Reciever {

    private final SensorDataService sensorDataService;

    public Reciever(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }


    @Bean
    public void receiveMessage() throws Exception{
        String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null) uri = "amqps://ffeeaqmk:oy0V2zCDctpBEQoWpM6WQqf516o2U0L9@roedeer.rmq.cloudamqp.com/ffeeaqmk";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);

        //Recommended settings
        factory.setConnectionTimeout(30000);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queue = "hello";     //queue name
        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

        channel.queueDeclare(queue, durable, exclusive, autoDelete, null);


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            SensorDataDTO sensorDataDTO = new SensorDataDTO();

            try {
                JSONObject jsonObject=new JSONObject(message);
                sensorDataDTO.setSensor_id(UUID.fromString(String.valueOf(jsonObject.get("sensor_id"))));
                sensorDataDTO.setEnergyConsumption(Double.valueOf(String.valueOf(jsonObject.get("energyConsumption"))));
                System.out.println(sensorDataDTO);
                sensorDataService.insert(sensorDataDTO);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            log.info(" [x] Received '" + message + "'");
            System.out.println(" [x] Received '" + message + "'");

        };
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> { });
    }
}
