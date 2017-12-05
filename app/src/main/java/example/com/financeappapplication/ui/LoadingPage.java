package example.com.financeappapplication.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.internal.Utils;
import example.com.financeappapplication.R;
import example.com.financeappapplication.util.LogUtil;
import example.com.financeappapplication.util.UIUtils;

/**
 * Created by Administrator on 2017/12/4.
 */

public abstract class LoadingPage extends FrameLayout {

    private static final int STATE_EMPTY = 0;
    private static final int STATE_ERROR = 1;
    private static final int STATE_SUCCESS = 2;
    private static final int STATE_LOADING = 3;

    private int state_current = STATE_LOADING;

    private View view_empty;
    private View view_error;
    private View view_success;
    private View view_loading;
    private LayoutParams params;

    public LoadingPage(@NonNull Context context) {
        super(context,null);
        init();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //对布局初始化
    private void init() {
         params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (view_loading == null){
            //加载布局
            view_loading = UIUtils.getView(R.layout.page_loading);
            //添加到当前布局
            addView(view_loading,params);
        }
        if (view_error == null){
            //加载布局
            view_error = UIUtils.getView(R.layout.page_error);
            //添加到当前布局
            addView(view_error,params);
        }
        if (view_empty == null){
            //加载布局
            view_empty = UIUtils.getView(R.layout.page_empty);
            //添加到当前布局
            addView(view_empty,params);
        }
        showSafePager();
        show();

    }

    //保证在主线程中运行
    private void showSafePager() {
        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    //根据当前state_current的值 显示哪一个
    private void showPage() {
        view_loading.setVisibility(state_current == STATE_LOADING ? VISIBLE : GONE);
        view_error.setVisibility(state_current == STATE_ERROR ? VISIBLE : GONE);
        view_empty.setVisibility(state_current == STATE_EMPTY ? VISIBLE : GONE);

        if (view_success == null){
            view_success = UIUtils.getView(layoutId());
            addView(view_success,params);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS ? VISIBLE : GONE);
    }

    //联网请求
    ResultState resultState;
    private void show() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url(),params(),new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                if (TextUtils.isEmpty(content)){
                    resultState = ResultState.EMPTY;
                    resultState.setContent("");
                }else{
                    resultState = ResultState.SUCCESS;
                    resultState.setContent(content);
                }
                LoadImage();
            }
            @Override
            public void onFailure(Throwable error, String content) {
                resultState = ResultState.ERROR;
                resultState.setContent("");
            }
        });
    }

    //根据枚举选择状态
    private void LoadImage() {
        switch (resultState){
            case EMPTY:
                state_current = STATE_EMPTY;
                break;
            case ERROR:
                state_current = STATE_ERROR;
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;
        }
        showSafePager();
        if (state_current == STATE_SUCCESS){
            onSucess(resultState,view_success);
        }
    }

    //联网成功把参数传出去
    protected abstract void onSucess(ResultState resultState, View view_success);

    protected abstract RequestParams params();

    protected abstract String url();


    protected abstract int layoutId();

    //把状态封装成枚举类型
     protected enum ResultState{
        ERROR(2),
        EMPTY(3),
        SUCCESS(4);

        int state;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        String content;
        ResultState(int state){
            this.state = state;
        }
    }

}
