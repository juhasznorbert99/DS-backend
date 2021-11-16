package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.SensorDataDTO;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.entities.SensorData;
import ro.tuc.ds2020.repositories.SensorDataRepository;
import ro.tuc.ds2020.repositories.SensorRepository;

import java.util.*;

@Service
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final SensorRepository sensorRepository;

    public SensorDataService(SensorDataRepository sensorDataRepository, SensorRepository sensorRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.sensorRepository = sensorRepository;
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

    public UUID insert(SensorDataDTO sensorDataDTO) {
        SensorData sensorData = new SensorData();
        sensorData.setTimestamp(new Date());
        sensorData.setEnergyConsumption(sensorDataDTO.getEnergyConsumption());

        Sensor sensor = sensorRepository.findById(sensorDataDTO.getSensor_id()).get();
        sensorData.setSensor(sensor);

        SensorData insertedSensorData = sensorDataRepository.save(sensorData);
        return insertedSensorData.getId();
    }

    public void delete(UUID sensorDataID) {
        sensorDataRepository.deleteById(sensorDataID);
    }
}
