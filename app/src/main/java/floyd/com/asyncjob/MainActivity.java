package floyd.com.asyncjob;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import floyd.com.asyncjob.manager.FileUploadManager;
import floyd.com.asyncjob.manager.IndexManager;
import floyd.com.asyncjob.vo.UnReadTimesExt;
import floyd.com.aync.ApiCallback;
import floyd.com.request.FileItem;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 10000;
    private Button fetchDataButton;
    private TextView showDataView;
    private Button choosePicButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchDataButton = (Button) findViewById(R.id.fetch_data_button);
        showDataView = (TextView) findViewById(R.id.show_data);
        choosePicButton = (Button) findViewById(R.id.choose_pic);
        fetchDataButton.setOnClickListener(this);
        choosePicButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fetch_data_button:
                IndexManager.getUnReadTimeExt("testpro44", "23018936", "testpro46").startUI(new ApiCallback<UnReadTimesExt>() {
                    @Override
                    public void onError(int code, String errorInfo) {
                        Toast.makeText(MainActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(UnReadTimesExt unReadTimesExt) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("contractId:").append(unReadTimesExt.contact).append("\r\n");
                        sb.append("msgCount:").append(unReadTimesExt.msgCount).append("\r\n");
                        sb.append("toId:").append(unReadTimesExt.toId).append("\r\n");
                        showDataView.setText(sb.toString());
                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                });
                break;
            case R.id.choose_pic:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                InputStream in = null;
                byte[] tmpData = null;
                ByteArrayOutputStream baos = null;
                try {
                    baos = new ByteArrayOutputStream();
                    in = this.getContentResolver().openInputStream(uri);
                    tmpData = new byte[in.available()];
                    in.read(tmpData);
                } catch (FileNotFoundException fe) {
                    Log.e(TAG, fe.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                        }
                    }
                }

                if (tmpData != null && tmpData.length > 0) {
                    FileItem fileItem = new FileItem("floyd" + System.currentTimeMillis() + ".jpg", tmpData);
                    FileUploadManager.uploadFile("floydchen", "xxxx", fileItem).startUI(new ApiCallback<String>() {
                        @Override
                        public void onError(int code, String errorInfo) {
                            Toast.makeText(MainActivity.this, errorInfo, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProgress(int progress) {

                        }
                    });
                }
            }

        }
    }
}
