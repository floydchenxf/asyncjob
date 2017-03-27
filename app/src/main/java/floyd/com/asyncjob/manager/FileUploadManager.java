package floyd.com.asyncjob.manager;

import java.util.HashMap;
import java.util.Map;

import floyd.com.aync.AsyncJob;
import floyd.com.aync.HttpJobFactory;
import floyd.com.aync.StringFunc;
import floyd.com.request.FileItem;
import floyd.com.request.HttpMethod;

/**
 * Created by chenxiaofeng on 2017/3/27.
 */
public class FileUploadManager {

    public static AsyncJob<String> uploadFile(String nick, String arg1, FileItem fileItem) {
        String url = "http://openlog.ww.taobao.com/upload/upload_service.do";
        Map<String, FileItem> fileParams = new HashMap<String, FileItem>();
        fileParams.put("upfile", fileItem);
        Map<String, String> params = new HashMap<String, String>();
        params.put("nick", nick);
        params.put("arg1", arg1);
        return HttpJobFactory.createFileJob(url, params, fileParams, HttpMethod.POST).map(new StringFunc());
    }
}
