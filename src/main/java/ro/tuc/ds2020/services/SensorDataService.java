package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.entities.SensorData;
import ro.tuc.ds2020.repositories.SensorDataRepository;

import java.util.*;

@Service
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public List<SensorData> findAll() {
        return sensorDataRepository.findAll();
    }

    public SensorData findSensorData(UUID sensorDataID) {
        List<SensorData> sensorDatas = sensorDataRepository.findAll();
        for(SensorData i: sensorDatas){
            if(i.getId().equals(sensorDataID))
                return i;
        }
        return null;
    }

    public UUID insert(SensorData sensorData) {
        sensorData.setTimestamp(new Date());
        SensorData insertedSensorData = sensorDataRepository.save(sensorData);
        return insertedSensorData.getId();
    }

    public void delete(UUID sensorDataID) {
        sensorDataRepository.deleteById(sensorDataID);
    }
}
