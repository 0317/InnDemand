package demand.inn.com.inndemand.parser;

import demand.inn.com.inndemand.model.response.ServiceResponse;

/**
 * Created  by Akash
 */
public interface IParser {
    public ServiceResponse parseData(int eventType,String data);

}