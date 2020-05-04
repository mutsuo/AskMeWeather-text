package neet.com.knowweather.tool;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SMSToll {
    public static final int GET_SUCCESS = 1;//获取验证码成功
    public static final int SUBMIT_SUCCESS = 2;//验证成功
    public static final int CHECK_FAILE = 3;//检查失败
    private Handler mHander;
    public SMSToll(Handler mHander){
        initSMSSDK();
        this.mHander=mHander;
    }
    private void initSMSSDK() {
        //注册EventHandler监听，每次短信SDK操作回调,
        // 在EventHandler的4个回调方法都可能不在UI线程下，需要使用到消息处理机制。
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                //判断返回的结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //服务器返回成功
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        mHander.sendEmptyMessage(GET_SUCCESS);
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //校验成功
                        mHander.sendEmptyMessage(SUBMIT_SUCCESS);
                    }
                } else {
                    //服务器返回错误码
                    Throwable throwable = (Throwable) data;
                    Message msg = Message.obtain();
                    msg.what = CHECK_FAILE;
                    msg.obj = throwable;
                    mHander.sendMessage(msg);
                }
            }
        });
    }
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case GET_SUCCESS:
//                    Log.e("获取验证码成功", "成功");
//                    //logUpView.showFailedError("获取验证码成功");
//                    break;
//                case SUBMIT_SUCCESS://验证成功处理
//                    Log.e("验证成功", "成功");
//                    //LogupAsyTask logupAsyTask = new LogupAsyTask();
//                    //logupAsyTask.execute();
//                    break;
//                case CHECK_FAILE://服务器返回错误处理
//                    Throwable data = (Throwable) msg.obj;
//                    //logUpView.showFailedError("发生了我不知道的问题,请再试一次吧");
//                    //Logger.d(data.getMessage());
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    public void sendSmsCode(String mPhone) {
//        String mPhone = logUpView.getUserPhone();
//        //判断手机号是否为空并且检查手机号的有效性
//        if (!TextUtils.isEmpty(mPhone) && checkPhoneValid(mPhone)) {
            SMSSDK.getVerificationCode("86", mPhone);//申请验证码，结果都在EventHandler监听返回
//            logUpView.showTimedown();//开始倒计时
//        } else {
//            logUpView.showFailedError("请输入正确的手机号");
//        }
    }

    public void commint(String mPhone,String mCode) {
       // String mCode = logUpView.getRequestCode();
        //判断手机号和验证码都不为空
//        if (!TextUtils.isEmpty(mCode) && !TextUtils.isEmpty(logUpView.getUserPhone())) {
            SMSSDK.submitVerificationCode("86", mPhone, mCode);//提交验证信息，结果都在EventHandler监听返回
//        } else {
//            logUpView.showFailedError("请输入手机号和验证码");
//        }
    }
}
