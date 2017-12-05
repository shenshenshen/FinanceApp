package example.com.financeappapplication.common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.financeappapplication.ui.LoadingPage;

/**
 * Created by Administrator on 2017/12/1.
 */

public abstract class BaseFragment extends Fragment{

    private Unbinder butterknife;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LoadingPage loadingPage = new LoadingPage(container.getContext()) {
            @Override
            protected void onSucess(ResultState resultState, View view_success) {
                  butterknife  = ButterKnife.bind(BaseFragment.this,view_success);
                  iniData(resultState.getContent());
                  iniTitle();
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }

            @Override
            protected int layoutId() {
                return getLayoutId();
            }
        };

        return loadingPage;
    }

    protected abstract String getUrl();

    protected abstract RequestParams getParams();

    protected abstract void iniTitle();

    protected abstract void iniData(String content);

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        butterknife.unbind();
    }
}



