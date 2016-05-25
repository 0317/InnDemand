package demand.inn.com.inndemand.parser;

import demand.inn.com.inndemand.constants.JsonKey;
import demand.inn.com.inndemand.model.User;
import demand.inn.com.inndemand.model.response.ServiceResponse;
import demand.inn.com.inndemand.model.response.common.JabongBaseModel;
import demand.inn.com.inndemand.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import demand.inn.com.inndemand.constants.ApiType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;


/**
 * Created  on Akash
 */
public class BaseParser implements IParser {

    public ServiceResponse parseData(int eventType, String data) {
        ServiceResponse response = null;
        try {
            response = handleJsonResponse(eventType, data);
        } catch (Exception e) {
            response = new ServiceResponse();
            response.setErrorCode(ServiceResponse.EXCEPTION);
        }
        return response;
    }

    protected JabongBaseModel parseBaseData(JSONObject jsonObject)
            throws JSONException {

        JabongBaseModel baseModel = new JabongBaseModel();
        baseModel.setSuccess(jsonObject.getBoolean(JsonKey.SUCCESS));
        return baseModel;

    }

    protected ServiceResponse handleJsonResponse(int eventType, JSONObject jsonObject) {
        ServiceResponse response = new ServiceResponse();
        try {
            JabongBaseModel responseModel = parseBaseData(jsonObject);
            response.setJabongBaseModel(responseModel);
            response.setEventType(eventType);
            response.setJsonResponse(jsonObject);
            //       Log.d("response is ", jsonObject.toString());



        } catch (Exception e) {
            response.setErrorCode(ServiceResponse.EXCEPTION);
            e.printStackTrace();
        }
        return response;
    }

    protected ServiceResponse handleJsonResponse(int eventType, String data) {
        ServiceResponse response = new ServiceResponse();


        try {
            JabongBaseModel responseModel =  new JabongBaseModel();

                try {

                    responseModel.setSuccess(new JSONObject(data).getBoolean(JsonKey.SUCCESS));
                }catch (Exception e){
                    responseModel.setSuccess(false);
                    response.setErrorCode(ServiceResponse.EXCEPTION);
                    data = "{\"success\":false}";

                }


            response.setJabongBaseModel(responseModel);
            response.setEventType(eventType);
            //response.setJsonResponse(new JSONObject(data));
            response.setJsonString(data);
            //       Log.d("response is ", jsonObject.toString());
            if (responseModel.isSuccess()) {
                response.setErrorCode(ServiceResponse.SUCCESS);
                parseJsonData(response);
            }  else {
                handleError(response);
            }



        } catch (Exception e) {
            response.setErrorCode(ServiceResponse.EXCEPTION);
            e.printStackTrace();
        }
        return response;
    }

    private boolean checkWhetherAutoLoginFailed(ServiceResponse response) {
        try {
            if (response.getJsonResponse().getJSONObject("session").has("redirectLogin")) {
                return response.getJsonResponse().getJSONObject("session").getBoolean("redirectLogin");
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }

    protected void handleError(ServiceResponse response) throws JSONException {
//TODO Error hanlding
        JSONObject jsonObject = response.getJsonResponse();

        if (Utils.isJsonObject(jsonObject, JsonKey.MESSAGE)) {


            if (jsonObject.getJSONObject(JsonKey.MESSAGE).has(JsonKey.VALIDATION)) {
                response.setErrorCode(ServiceResponse.VALIDATION_ERROR);

            } else if (jsonObject.getJSONObject(JsonKey.MESSAGE).has(JsonKey.ERROR)) {
                response.setErrorCode(ServiceResponse.MESSAGE_ERROR);

                ArrayList<String> errorMessages = new ArrayList<String>();

                if (Utils.isJsonObject(jsonObject.getJSONObject(JsonKey.MESSAGE), "error")) {
                    Iterator<String> keys = jsonObject.getJSONObject(JsonKey.MESSAGE).getJSONObject("error").keys();
                    for (int i = 0; keys.hasNext(); i++) {
                        Object json = jsonObject.getJSONObject(JsonKey.MESSAGE).getJSONObject("error").get(keys.next());
                        if (json instanceof JSONArray)
                            errorMessages.add(i, ((JSONArray) json).get(0).toString());
                        else
                            errorMessages.add(i, json.toString());

                    }
                } else {
                    for (int i = 0; i < jsonObject.getJSONObject(JsonKey.MESSAGE).getJSONArray("error").length(); i++) {
                        errorMessages.add(i, jsonObject.getJSONObject(JsonKey.MESSAGE).getJSONArray("error").getString(i));
                    }
                }
                response.setErrorMessages(errorMessages);
            }
        }
        if (response.getErrorCode() == ServiceResponse.VALIDATION_ERROR) {


        } else if (response.getErrorCode() == ServiceResponse.MESSAGE_ERROR) {


                    String error = response.getJsonResponse().getJSONObject(JsonKey.MESSAGE).getJSONArray(JsonKey.ERROR).getString(0);
                    response.setErrorText(error);



        }
    }

    protected void parseJsonData(ServiceResponse response) throws JSONException {
        Gson gson = new Gson();
        switch (response.getEventType()) {

            case ApiType.API_USER:

                User user = gson.fromJson(response.getJsonString(), User.class);

                response.setResponseObject(user);
                break;



        }

    }


}
