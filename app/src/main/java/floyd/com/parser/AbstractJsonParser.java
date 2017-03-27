package floyd.com.parser;

import org.json.JSONException;
import org.json.JSONObject;

import floyd.com.aync.APIError;
import floyd.com.aync.ApiCallback;

/**
 * Created by floyd on 15-12-5.
 */
public abstract class AbstractJsonParser<T> implements Parser<T> {


    @Override
    public void doParse(ApiCallback<T> apiCallback, String s) {
        JSONObject j = null;
        try {
            j = new JSONObject(s);
            boolean success = j.getBoolean("success");
            if (success) {
                String data = j.getString("data");
                T r = convert2Obj(data);
                apiCallback.onSuccess(r);
            } else {
                String msg = j.getString("errorMsg");
                apiCallback.onError(APIError.API_BIZ_ERROR, msg);
            }
        } catch (JSONException e) {
            apiCallback.onError(APIError.API_JSON_PARSE_ERROR, e.getMessage());
        }
    }

    protected abstract T convert2Obj(String data);

}
