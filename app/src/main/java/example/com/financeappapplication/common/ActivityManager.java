package example.com.financeappapplication.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator on 2017/11/29.
 */

public class ActivityManager {

    //单例模式:饿汉式
    private ActivityManager(){

    }

    private static ActivityManager activityManager = new ActivityManager();

    public static ActivityManager getInstance(){
        return  activityManager;
    }

    private Stack<Activity> activityStack = new Stack<>();

    //添加activity
    public void add(Activity activity){
        if (activity != null){
            activityStack.add(activity);
        }
    }

    //删除指定的activity
    public void remove(Activity activity){
        if (activity != null){
            for (int i = activityStack.size()-1; i>= 0;i--){
                Activity currentActivity = activityStack.get(i);
                if (currentActivity.getClass().equals(activity.getClass())){
                    currentActivity.finish();
                    activityStack.remove(i);
                }
            }
        }
    }

    //删除当前的activity
    public void removeCurrent() {

        Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }

    public void removeAll(){
        for (int i = activityStack.size()-1; i >= 0 ;i--){
            Activity activity = activityStack.get(i);
            activity.finish();
            activityStack.remove(activity);
        }
    }

    public int size(){
        return  activityStack.size();
    }

}
