package demand.inn.com.inndemand.model.request.listener;


import demand.inn.com.inndemand.model.request.IRequest;

/**
 * Created  on 30/1/14.
 */
public interface IRequestCompletionListener {
    void onRequestProcessed(boolean isSuccess,IRequest request);

}
