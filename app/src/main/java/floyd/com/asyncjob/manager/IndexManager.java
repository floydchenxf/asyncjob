package floyd.com.asyncjob.manager;

import java.util.HashMap;
import java.util.Map;

import floyd.com.asyncjob.vo.UnReadTimes;
import floyd.com.asyncjob.vo.UnReadTimesExt;
import floyd.com.aync.AsyncJob;
import floyd.com.aync.AsyncJobHelper;
import floyd.com.aync.Func2;
import floyd.com.gson.JsonHttpJobFactory;
import floyd.com.request.HttpMethod;

/**
 * Created by chenxiaofeng on 2017/3/27.
 */
public class IndexManager {

    /**
     * 获取uid用户的未读消息数
     *
     * @param uid      当前用户
     * @param appkey   app应用Id
     * @param targetId 发送消息者
     * @return
     */
    public static AsyncJob<UnReadTimes> fetchUnReadTime(String uid, String appkey, String targetId) {
        String url = "https://chat.im.taobao.com/fb/unread_num_fetch";
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("appkey", appkey);
        params.put("targetId", targetId);
        return JsonHttpJobFactory.getJsonAsyncJob(url, params, HttpMethod.POST, UnReadTimes.class);
    }

    /**
     * 获取toId用户
     * @param uid
     * @param appkey
     * @return
     */
    public static AsyncJob<String> getToId(String uid, String appkey) {
        String url = "https://chat.im.taobao.com/fb/to_id_fetch";
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("appkey", appkey);
        return JsonHttpJobFactory.getJsonAsyncJob(url, params, HttpMethod.POST, String.class);
    }

    /**
     * 获取扩展的未读消息数，该api需要多次请求才能获取
     * @param uid
     * @param appkey
     * @param targetId
     * @return
     */
    public static AsyncJob<UnReadTimesExt> getUnReadTimeExt(String uid, String appkey, String targetId) {
        AsyncJob<UnReadTimes> a = fetchUnReadTime(uid, appkey, targetId);
        AsyncJob<String> b = getToId(uid, appkey);
        AsyncJob<UnReadTimesExt> result = AsyncJobHelper.zip(a, b, new Func2<UnReadTimes, String, UnReadTimesExt>() {
            @Override
            public UnReadTimesExt call(UnReadTimes unReadTimes, String s) {
                UnReadTimesExt ext = new UnReadTimesExt();
                ext.toId = s;
                ext.contact = unReadTimes.contact;
                ext.lastMsgTime = unReadTimes.lastMsgTime;
                ext.msgCount = unReadTimes.msgCount;
                ext.msgId = unReadTimes.msgId;
                return ext;
            }
        });

        return result;
    }
}
