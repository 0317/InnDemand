/**
 *
 */
package demand.inn.com.inndemand.model.response;


import demand.inn.com.inndemand.model.response.common.JabongBaseModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 */
public class ServiceResponse {

    private long apiResponseTime;

    public long getParsingTime() {
        return parsingTime;
    }

    public void setParsingTime(long parsingTime) {
        this.parsingTime = parsingTime;
    }

    public long getApiResponseTime() {
        return apiResponseTime;
    }

    public void setApiResponseTime(long apiResponseTime) {
        this.apiResponseTime = apiResponseTime;
    }

    private long parsingTime;
    public static final int EXCEPTION = 1;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    public static final int VALIDATION_ERROR = 4;
    public static final int MESSAGE_ERROR = 5;
    public static final int SUCCESS = 6;
    public static final int AUTO_LOGIN_FAILED = 7;

    private JSONObject jsonResponse;
    private String errorText;
    private int errorCode = 3;

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    private String jsonString;
    private Exception exception;
    private Hashtable headers;
    private ArrayList<String> errorMessages;
    private JabongBaseModel jabongBaseModel;
    private Object responseObject;
    private int eventType;

    private Object mRequestData = null;

    public Object getRequestData() {
        return mRequestData;
    }

    public void setRequestData(Object requestData) {
        this.mRequestData = requestData;
    }

    public boolean isRetryLimitExceeded() {
        return isRetryLimitExceeded;
    }

    public void setRetryLimitExceeded(boolean isRetryLimitExceeded) {
        this.isRetryLimitExceeded = isRetryLimitExceeded;
    }

    private boolean isRetryLimitExceeded;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    private String addressId;


    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(JSONObject jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(ArrayList<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * @return the jabongBaseModel
     */
    public JabongBaseModel getJabongBaseModel() {
        return jabongBaseModel;
    }

    /**
     * @param jabongBaseModel the jabongBaseModel to set
     */
    public void setJabongBaseModel(JabongBaseModel jabongBaseModel) {
        this.jabongBaseModel = jabongBaseModel;
    }


    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }


    public Hashtable getHeaders() {
        return headers;
    }

    public void setHeaders(Hashtable headers) {
        this.headers = headers;
    }

    public boolean isError() {
        return getErrorCode() > 0;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
