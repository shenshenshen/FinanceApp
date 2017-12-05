package example.com.financeappapplication.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.financeappapplication.R;
import example.com.financeappapplication.bean.Image;
import example.com.financeappapplication.common.AppNetConfig;
import example.com.financeappapplication.common.BaseFragment;
import example.com.financeappapplication.util.LogUtil;

/**
 * Created by Administrator on 2017/11/28.
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @BindView(R.id.tv_yhome_yearrate)
    TextView tvYhomeYearrate;

    private Image.ProInfoBean product;
    private List<Image.ImageArrBean> images;

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected void iniTitle() {
        LogUtil.e("HomeFragment被初始化了");
        tvTitle.setText("首页");
        ivTitleBack.setVisibility(View.GONE);
        ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    protected void iniData(String content) {
        LogUtil.e("HomeFragment联网请求成功");
        JSONObject jsonObject = JSON.parseObject(content);
        
        //解析json对象数据
        String proinifor = jsonObject.getString("proInfo");
        product = JSON.parseObject(proinifor,Image.ProInfoBean.class);

        String imageArr = jsonObject.getString("imageArr");
        images = JSON.parseArray(imageArr,Image.ImageArrBean.class);

        tvHomeProduct.setText(product.getName());
        tvYhomeYearrate.setText(product.getYearRate());

        vpHome.setAdapter(new MyPagerAdapter());

    }

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_home;
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Picasso.with(getActivity()).load(AppNetConfig.BASE_URL + images.get(position).getIMAURL()).into(imageView);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            List<Image.ImageArrBean> imagess = images;
            return imagess == null ? 0 : imagess.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}


