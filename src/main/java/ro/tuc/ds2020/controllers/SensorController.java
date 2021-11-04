package ro.tuc.ds2020.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.repositories.SensorRepository;
import ro.tuc.ds2020.services.ClientService;
import ro.tuc.ds2020.services.SensorService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/sensor")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping()
    public ResponseEntity<List<Sensor>> getSensors(){
        return new ResponseEntity<>(sensorService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensor(@PathVariable("id") UUID sensorID){
        return new ResponseEntity<>(sensorService.findSensor(sensorID), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> createSensor(@RequestBody Sensor sensor){
        UUID sensorID = sensorService.insert(sensor);
        return new ResponseEntity<>(sensorID, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UUID> deleteSensor(@PathVariable("id") UUID sensorID){
        sensorService.delete(sensorID);
        return new ResponseEntity<>(sensorID, HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<UUID> updateSensor(@PathVariable("id") UUID sensorID, @RequestBody Sensor sensor){
        sensorService.update(sensorID, sensor);
        return new ResponseEntity<>(sensorID, HttpStatus.OK);
    }

}
