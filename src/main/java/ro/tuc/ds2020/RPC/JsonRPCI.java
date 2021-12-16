package ro.tuc.ds2020.RPC;


import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import ro.tuc.ds2020.entities.Device;

import java.util.List;


@JsonRpcService("/rpc")
public interface JsonRPCI {

    public List<Device> getDevices();

    public List<List<Double>> getSensorData(@JsonRpcParam(value="id") String id, @JsonRpcParam(value="days") String days);

}