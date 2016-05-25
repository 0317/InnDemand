package demand.inn.com.inndemand.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
	/**
	 * get String from {@link JSONObject}.
	 * 
	 * @param jsonObject
	 * @param key
	 * @return value
	 */
	public static String getString(JSONObject jsonObject, String key) {
		try {
			if (jsonObject != null) {
				if (!jsonObject.isNull(key)) {
					return jsonObject.getString(key);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get {@link JSONObject}.
	 * 
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public static JSONObject getJsonObject(JSONObject jsonObject, String key) {

		try {
			if (jsonObject != null) {
				if (!jsonObject.isNull(key)) {
					return jsonObject.getJSONObject(key);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
