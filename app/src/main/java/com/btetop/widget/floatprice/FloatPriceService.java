package com.btetop.widget.floatprice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.blankj.utilcode.util.SizeUtils;
import com.btetop.R;
import com.btetop.activity.HeyueActivity;
import com.btetop.activity.KLineActivity;
import com.btetop.application.BteTopApplication;
import com.btetop.bean.AirBoardBean;
import com.btetop.bean.CoinInfo;
import com.btetop.bean.UserInfo;
import com.btetop.service.ArtBoardService;
import com.btetop.service.UserService;
import com.btetop.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class FloatPriceService extends Service implements ArtBoardService.DataListener {
    private static final String TAG = "FloatPriceService";
    private static final int interval_time = 5 * 1000;
    public static boolean isStarted = false;
    public static FloatPriceService instance = null;

    private final IBinder iBinder = new ConnectFloatPriceServiceBinder();

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    //    private InterceptLinearLayout rootView;
    private PriceAdapter adapter;

    private List<AirBoardBean> mdata = new ArrayList<>();
    private Context applicationContext;

    private android.os.Handler handler;
    private MyListView listView;


    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = SizeUtils.dp2px(67);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 300;
        layoutParams.y = 300;
        mdata = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow(Intent intent) {
        ArrayList<AirBoardBean> data = null;
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                data = (ArrayList<AirBoardBean>) extras.getSerializable(AirBoardBean.SerializableTag);
            }
        }

        instance = this;
        applicationContext = getApplicationContext();
        if (data != null && data.size() > 0) {
            mdata.addAll(data);
        }

        Drawable drawable = ContextCompat.getDrawable(applicationContext, R.drawable.bg_adpater_price);
        adapter = new PriceAdapter(mdata, applicationContext);


        listView = new MyListView(applicationContext);
        listView.setCacheColorHint(0);
        listView.setBackground(drawable);

        Drawable listViewDivider = ContextCompat.getDrawable(applicationContext, R.drawable.bg_adpater_price_divider);

        listView.setDivider(listViewDivider);
        listView.setDividerHeight(SizeUtils.dp2px(0.5f));
        listView.setAdapter(adapter);

        windowManager.addView(listView, layoutParams);
        listView.setOnTouchListener(new View.OnTouchListener() {

            //刚按下是起始位置的坐标
            float startDownX, startDownY;
            float downX, downY;
            float moveX, moveY;

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {


                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "ACTION_DOWN");
                        startDownX = downX = motionEvent.getRawX();
                        startDownY = downY = motionEvent.getRawY();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "ACTION_MOVE");
                        moveX = motionEvent.getRawX();
                        moveY = motionEvent.getRawY();
//                        if (mOnFlingListener != null)
//                            mOnFlingListener.onMove(moveX - downX, moveY - downY);
                        move(moveX - downX, moveY - downY);
                        downX = moveX;
                        downY = moveY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP");
                        float upX = motionEvent.getRawX();
                        float upY = motionEvent.getRawY();
                        if (upX == startDownX && upY == startDownY)
                            return false;
                        else
                            return true;
                }
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AirBoardBean airBoardBean = mdata.get(position);

                UserInfo userInfo = UserService.getCurrentUserInfo();
                if (airBoardBean.getQuoteAsset().equalsIgnoreCase("QUARTER") && userInfo.getIsFutureDogUser()==1) {//合约
                    Intent intent = new Intent(BteTopApplication.getInstance(), HeyueActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("base",airBoardBean.getBaseAsset());
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    BteTopApplication.getInstance().startActivity(intent);
                } else {

                    CoinInfo coinInfo = new CoinInfo();
                    coinInfo.setSymbol(airBoardBean.getBaseAsset());
                    coinInfo.setExchange(airBoardBean.getExchange());
                    coinInfo.setQuote(airBoardBean.getQuoteAsset());
                    Intent intent = new Intent(BteTopApplication.getInstance(), KLineActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("coinInfo", coinInfo);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    BteTopApplication.getInstance().startActivity(intent);
                }
            }
        });
        updateView();


    }

    private void updateView() {
        if (handler == null) {
            handler = new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    removeMessages(0);
                    getData();
                }
            };
        }

        getData();
    }

    private void getData() {
        ArtBoardService instance = ArtBoardService.getInstance();
        instance.getOnLineData();
        instance.addListener(this);
        handler.sendEmptyMessageDelayed(0, interval_time);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        isStarted = false;
        instance = null;
        windowManager.removeView(listView);

        ArtBoardService.getInstance().unSetListener(this);
    }

    @Override
    public void onData(List<AirBoardBean> data) {

        if (data.size() == 0) {
            listView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
            mdata.clear();
            mdata.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    public void delItem(int item) {
        mdata.remove(item);
        adapter.notifyDataSetChanged();
        if (mdata.size() == 0) {
            listView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
        }


    }

    private void move(float movedX, float movedY) {
        layoutParams.x = (int) (layoutParams.x + movedX);
        layoutParams.y = (int) (layoutParams.y + movedY);
        windowManager.updateViewLayout(listView, layoutParams);

    }


    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        boolean isfirstTouch = true;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    isfirstTouch = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isfirstTouch) {
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                    }
                    isfirstTouch = false;
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    public class ConnectFloatPriceServiceBinder extends Binder {
        public FloatPriceService getService() {
            return FloatPriceService.this;
        }
    }


}
