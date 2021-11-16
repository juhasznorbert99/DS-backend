package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SensorDataDTO {
    private UUID id;
    private Date timestamp;
    private Double energyConsumption;
    private UUID sensor_id;
}
