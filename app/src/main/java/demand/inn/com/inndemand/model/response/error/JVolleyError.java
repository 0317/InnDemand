package demand.inn.com.inndemand.model.response.error;

import com.android.volley.VolleyError;

/**
 * Created by VJ
 */
public class JVolleyError extends VolleyError {
    private int mEventType;
    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int mEventType) {
        this.mEventType = mEventType;
    }


    public JVolleyError(VolleyError error)
    {
        super(error.networkResponse);
    }
    public JVolleyError(String messg) {
        super(messg);
    }

    public JVolleyError() {
        super();
    }
}
