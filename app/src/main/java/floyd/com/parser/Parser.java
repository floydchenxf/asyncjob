package floyd.com.parser;


import floyd.com.aync.ApiCallback;

/**
 * Created by floyd on 15-12-5.
 */
public interface Parser<T> {

    public void doParse(ApiCallback<T> apiCallback, String s);
}
