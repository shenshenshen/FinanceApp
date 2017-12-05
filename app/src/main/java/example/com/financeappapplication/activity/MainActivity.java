package example.com.financeappapplication.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.financeappapplication.R;
import example.com.financeappapplication.fragment.HomeFragment;
import example.com.financeappapplication.fragment.InvestFragment;
import example.com.financeappapplication.fragment.MeFragment;
import example.com.financeappapplication.fragment.MoreFragment;
import example.com.financeappapplication.util.UIUtils;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.iv_main_home)
    ImageView ivMainHome;
    @BindView(R.id.tv_main_home)
    TextView tvMainHome;
    @BindView(R.id.ll_main_home)
    LinearLayout llMainHome;
    @BindView(R.id.iv_main_invest)
    ImageView ivMainInvest;
    @BindView(R.id.tv_main_invest)
    TextView tvMainInvest;
    @BindView(R.id.ll_main_invest)
    LinearLayout llMainInvest;
    @BindView(R.id.iv_main_me)
    ImageView ivMainMe;
    @BindView(R.id.tv_main_me)
    TextView tvMainMe;
    @BindView(R.id.ll_main_me)
    LinearLayout llMainMe;
    @BindView(R.id.iv_main_more)
    ImageView ivMainMore;
    @BindView(R.id.tv_main_more)
    TextView tvMainMore;
    @BindView(R.id.ll_main_more)
    LinearLayout llMainMore;

    FragmentTransaction transaction;
    private static final int WHAT_RESET_BACK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSelect(0);
    }

    @OnClick({R.id.ll_main_home,R.id.ll_main_invest,R.id.ll_main_me,R.id.ll_main_more})
    public void showTab(View view){
     //   Toast.makeText(MainActivity.this,"具体选择了",Toast.LENGTH_SHORT).show();
        switch (view.getId()){
            case R.id.ll_main_home:
                setSelect(0);
                break;
            case R.id.ll_main_invest:
                setSelect(1);
                break;
            case R.id.ll_main_me:
                setSelect(2);
                break;
            case R.id.ll_main_more:
                setSelect(3);
                break;
        }
    }

    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;

    //提供相应的fragment的显示
    private void setSelect(int i) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        //隐藏所有Fragment的显示
        hideFragments();

        //重置Tab
        resetTab();

        switch (i){
            case 0:
                if (homeFragment == null){
                    homeFragment = new HomeFragment();//创建对象以后，并不会马上调用生命周期方法，而是在commit()之后，方才调用
                    transaction.add(R.id.fl_main,homeFragment);
                }
                //显示当前的fragment
                transaction.show(homeFragment);

                //改变选中项的图片和文本颜色的变化
                ivMainHome.setImageResource(R.drawable.bottom02);
                tvMainHome.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                break;
            case 1:
                if (investFragment == null){
                    investFragment = new InvestFragment();
                    transaction.add(R.id.fl_main,investFragment);
                }
                transaction.show(investFragment);

                //改变选中项的图片和文本颜色的变化
                ivMainInvest.setImageResource(R.drawable.bottom04);
                tvMainInvest.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                break;
            case 2:
                if (meFragment == null){
                    meFragment = new MeFragment();
                    transaction.add(R.id.fl_main,meFragment);
                }
                transaction.show(meFragment);

                //改变选中项的图片和文本颜色的变化
                ivMainMe.setImageResource(R.drawable.bottom06);
                tvMainMe.setTextColor(UIUtils.getColor(R.color.home_back_selected01));
                break;
            case 3:
                if (moreFragment == null){
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.fl_main,moreFragment);
                }
                transaction.show(moreFragment);

                //改变选中项的图片和文本颜色的变化
                ivMainMore.setImageResource(R.drawable.bottom08);
                tvMainMore.setTextColor(UIUtils.getColor(R.color.home_back_selected));
                break;
        }
        transaction.commit();//提交事务
    }

    //重置选项
    private void resetTab() {
        ivMainHome.setImageResource(R.drawable.bottom01);
        ivMainInvest.setImageResource(R.drawable.bottom03);
        ivMainMe.setImageResource(R.drawable.bottom05);
        ivMainMore.setImageResource(R.drawable.bottom07);

        tvMainHome.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainInvest.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainMe.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainMore.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
    }

    //隐藏Fragments
    private void hideFragments() {
        if (homeFragment != null){
            transaction.hide(homeFragment);
        }
        if (meFragment!= null){
            transaction.hide(meFragment);
        }
        if (moreFragment != null){
            transaction.hide(moreFragment);
        }
        if (investFragment != null){
            transaction.hide(investFragment);
        }
    }

    //重写onKeyUp(),实现连续两次点击方法可推出当前应用
    private boolean flag = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_RESET_BACK :
                    flag = true;//复原
                    break;
            }
        }
    };
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && flag) {
            Toast.makeText(MainActivity.this,"如果再点击一次，退出当前应用",Toast.LENGTH_SHORT).show();
            flag = false;
            //发送延迟消息
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK,2000);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(WHAT_RESET_BACK);//指定id的所有的消息
    }
}
