package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.*;
import ro.tuc.ds2020.repositories.SensorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SensorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

    public UUID insert(Sensor sensor) {
        Sensor savedSensor = sensorRepository.save(sensor);
        return savedSensor.getId();
    }

    public Sensor returnSensor(List<Sensor> sensors, UUID id){
        for(Sensor i: sensors){
            if(i.getId().equals(id))
                return i;
        }
        return null;
    }

    public void delete(UUID sensorID) {
        sensorRepository.deleteById(sensorID);
    }

    public void update(UUID sensorID, Sensor sensor) {
        sensor.setId(sensorID);
        sensorRepository.save(sensor);
    }
}
