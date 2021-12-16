package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.SensorDataDTO;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.entities.SensorData;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.SensorDataRepository;
import ro.tuc.ds2020.repositories.SensorRepository;

import java.util.*;

@Service
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final SensorRepository sensorRepository;
    private final DeviceRepository deviceRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public SensorDataService(SensorDataRepository sensorDataRepository, SensorRepository sensorRepository, DeviceRepository deviceRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.sensorRepository = sensorRepository;
        this.deviceRepository = deviceRepository;
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

        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        SensorData lastSensorData = new SensorData();
        //sensorData.value-sensorData.value / sensorData.time-sensorData.time >= sensor.maxValue
        if(sensorDataList.size()>0)
            for(int i=sensorDataList.size()-1;i>=0;i--){
                if(sensorDataList.get(i).getSensor().getId().equals(sensor.getId())){
                    lastSensorData = sensorDataList.get(i);
                    break;
                }
            }
        else
            lastSensorData = null;
        //get client bases on the sensor
        List<Device> devices = deviceRepository.findAll();
        Client client = new Client();
        for (Device i:
             devices) {
            if(i.getSensor().getId().equals(sensor.getId())){
                client = i.getClient();
                break;
            }
        }
        if(lastSensorData.getId()!=null)
        if(sensorDataList.size()>0){
            if(     (sensorData.getEnergyConsumption() - lastSensorData.getEnergyConsumption()) /
                    ((sensorData.getTimestamp().getTime() - lastSensorData.getTimestamp().getTime()) / (60.0*60.0*1000.0))
                    >= sensor.getMaximumValue()
            ){
                //to webSocket
                simpMessagingTemplate.convertAndSend("/topic/client/"+client.getId(), "s-a trimis valoarea mai mare la clientul " + client.getUsername());
            }
        }

        SensorData insertedSensorData = sensorDataRepository.save(sensorData);
        return insertedSensorData.getId();
    }

    public void delete(UUID sensorDataID) {
        sensorDataRepository.deleteById(sensorDataID);
    }
}
