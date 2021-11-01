package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<Device>> getDevices(){
        return new ResponseEntity<>(deviceService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> detDevice(@PathVariable("id") UUID id){
        List<Device> devices = deviceService.findAll();
        for(Device i: devices){
            if(i.getId().equals(id))
                return new ResponseEntity<>(i, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> createDevice(@RequestBody Device device){
        UUID savedDeviceID = deviceService.insert(device);
        return new ResponseEntity<>(savedDeviceID, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UUID> deleteDevice(@PathVariable("id") UUID deviceID){
        deviceService.delete(deviceID);
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UUID> updateDevice(@PathVariable("id") UUID deviceID, @RequestBody Device device){
        deviceService.update(deviceID,device);
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }
}
