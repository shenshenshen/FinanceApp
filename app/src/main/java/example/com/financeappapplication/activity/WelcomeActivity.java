package example.com.financeappapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.financeappapplication.R;
import example.com.financeappapplication.common.ActivityManager;

public class WelcomeActivity extends Activity {

    @BindView(R.id.iv_welcome_icon)
    ImageView ivWelcomeIcon;
    @BindView(R.id.rl_welcome)
    RelativeLayout rlwelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉窗口标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);
        //提供启动动画
        setAnimation();
    }

    private Handler handler = new Handler();
    //启动动画
    private void setAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);//0:完全透明 1:完全不透明
        alphaAnimation.setDuration(3000);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());//设置动画的变化率
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                ActivityManager.getInstance().remove(WelcomeActivity.this);
            }
        },3000);
        //启动动画
        rlwelcome.startAnimation(alphaAnimation);
    }
}


