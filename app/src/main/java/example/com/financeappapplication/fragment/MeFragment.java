package example.com.financeappapplication.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import example.com.financeappapplication.R;
import example.com.financeappapplication.common.AppNetConfig;
import example.com.financeappapplication.common.BaseFragment;
import example.com.financeappapplication.util.LogUtil;

/**
 * Created by Administrator on 2017/11/28.
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_setting)
    ImageView ivTitleSetting;


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    public int getLayoutId() {

        return R.layout.fragment_me;
    }

    @Override
    protected void iniData(String content) {

    }

    protected void iniTitle() {
        LogUtil.e("MeFragment初始化了");
        ivTitleBack.setVisibility(View.GONE);
        ivTitleSetting.setVisibility(View.GONE);
        tvTitle.setText("我的");
    }


}


