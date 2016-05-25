package demand.inn.com.inndemand.model.request;


import demand.inn.com.inndemand.model.request.listener.IRequestCompletionListener;
import demand.inn.com.inndemand.parser.IParser;

/**
 * created by VJ
 * Interface to be implemented by all Request Types
 */
public interface IRequest {
    /**
     * Method for registering IRequestCompletionListener.
     * Implementing Request class should use this listener object for notifying there completion state.
     *
     * @param listener Listener object for state completion callbacks
     */
    void setOnCompletedListener(IRequestCompletionListener listener) ;

    /**
     * Callback method for re-registering the Success,Error Listeners
     * @param newListener new Listener object for re-registering
     */
     boolean updateListeners(Object newListener);

    IParser getParser();
    void setParser(IParser parser);

}
