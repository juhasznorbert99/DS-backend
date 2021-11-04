package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.SensorData;
import ro.tuc.ds2020.services.SensorDataService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/sensorData")
public class SensorDataController {
    private final SensorDataService sensorDataService;

    public SensorDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @GetMapping()
    public ResponseEntity<List<SensorData>> getSensorDatas(){
        return new ResponseEntity<>(sensorDataService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorData> getSensorData(@PathVariable("id") UUID sensorDataID){
        return new ResponseEntity<>(sensorDataService.findSensorData(sensorDataID), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> createSensorData(@RequestBody SensorData sensorData){
        UUID sensorDataID = sensorDataService.insert(sensorData);
        return new ResponseEntity<>(sensorDataID, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UUID> deleteSensorData(@PathVariable("id") UUID sensorDataID){
        sensorDataService.delete(sensorDataID);
        return new ResponseEntity<>(sensorDataID, HttpStatus.OK);
    }

}
