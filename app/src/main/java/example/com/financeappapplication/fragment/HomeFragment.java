package example.com.financeappapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.financeappapplication.R;
import example.com.financeappapplication.bean.Image;
import example.com.financeappapplication.common.AppNetConfig;
import example.com.financeappapplication.util.UIUtils;

/**
 * Created by Administrator on 2017/11/28.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);

        //初始化Title
        initTitle();
        
        //初始化数据
        initData();

        return view;
    }

    //初始化数据
    private void initData() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(AppNetConfig.INDEX,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {//响应成功
                //解析json数据 : Gson /Fast-json
                JSONObject jsonObject = JSON.parseObject(content);

                //解析json对象数据
                String proinfor = jsonObject.getString("proInfo");
                //解析json数组数据
                Image.ProInfoBean product = JSON.parseObject(proinfor, Image.ProInfoBean.class);

                //解析json数组
                String ImageArr = jsonObject.getString("imageArr");
                List<Image.ImageArrBean> images =  JSON.parseArray(ImageArr, Image.ImageArrBean.class);

            }

            @Override
            public void onFailure(Throwable error, String content) {//响应失败
                Toast.makeText(UIUtils.getContext(),"联网获取数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //初始化Title
    private void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        ivTitleSetting.setVisibility(View.GONE);
        tvTitle.setText("首页");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}


