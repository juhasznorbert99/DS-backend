package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.*;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public UUID insert(Device device) {
        Device savedDevice = deviceRepository.save(device);
        return savedDevice.getId();
    }

    public void delete(UUID deviceID) {
        deviceRepository.deleteById(deviceID);
    }

    public void update(UUID deviceID, Device device) {
        device.setId(deviceID);
        deviceRepository.save(device);
    }
}
