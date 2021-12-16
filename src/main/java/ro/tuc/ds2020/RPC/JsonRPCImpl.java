package ro.tuc.ds2020.RPC;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.entities.SensorData;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.SensorDataRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AutoJsonRpcServiceImpl
public class JsonRPCImpl implements JsonRPCI{

    private final DeviceRepository deviceRepository;
    private final SensorDataRepository sensorDataRepository;

    public JsonRPCImpl(DeviceRepository deviceRepository, SensorDataRepository sensorDataRepository) {
        this.deviceRepository = deviceRepository;
        this.sensorDataRepository = sensorDataRepository;
    }

    @Override
    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public List<List<Double>> getSensorData(String id, String days) {
        Device device = deviceRepository.findById(UUID.fromString(id)).get();
        Sensor sensor = device.getSensor();
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getDayOfMonth());
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        System.out.println(sensorDataList);
        List<List<Double>> returnedValue = new ArrayList<>();
        for(int i=now.getDayOfMonth()-Integer.valueOf(days)+1;i<=now.getDayOfMonth();i++){
            if(i>0){
                List<Double> doubles = new ArrayList<>(24);
                for(int h=0;h<24;h++){
                    doubles.add(0.0);
                }
                for(SensorData j: sensorDataList){
                    if(j.getSensor().getId().equals(sensor.getId())){
                        //sensor data-ul bun pt acest device, mai trb sa punem in lista
                        if(Integer.valueOf(j.getTimestamp().toString().substring(8,10)) == i){
                            doubles.set(Integer.valueOf(j.getTimestamp().toString().substring(11,13)),doubles.get(Integer.valueOf(j.getTimestamp().toString().substring(11,13)))+j.getEnergyConsumption());
                        }
                    }
                }
                returnedValue.add(doubles);
            }
        }
        return returnedValue;
    }


}
