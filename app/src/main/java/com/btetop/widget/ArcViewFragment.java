package com.btetop.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.btetop.R;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.config.Constant;
import com.btetop.message.RouteMessage;
import com.btetop.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 参考phonedialog
 */
public class ArcViewFragment extends DialogFragment {
    private  int radius1 = 350;
    private Activity activity;


    @BindView(R.id.luzhuanggou_item)
    ImageView imLuzhuang;
    @BindView(R.id.boduan_item)
    ImageView imBoDuan;
    @BindView(R.id.heyue_item)
    ImageView imHeyue;
    @BindView(R.id.yanjiu_item)
    ImageView imYanjiu;
    @BindView(R.id.dingpan_item)
    ImageView imDingpan;
    @BindView(R.id.xiaoxi_item)
    ImageView imXiaoxi;
    @BindView(R.id.jiqi_item)
    ImageView imJiqi;

    @BindView(R.id.bg_rectangle)
    LinearLayout bgRectangle;
    @BindView(R.id.bg_half_circle)
    LinearLayout bgHalfCircle;

    private List<ImageView> imageViews = new ArrayList<>();

    public static ArcViewFragment getInstance() {
        ArcViewFragment fragment = new ArcViewFragment();
        return fragment;
    }

    public ArcViewFragment() {

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity=getActivity();
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_bottom);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消


        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height=ScreenUtils.getScreenWidth()/2+SizeUtils.dp2px(41);
        window.setAttributes(lp);

        ButterKnife.bind(this, dialog); // Dialog即View


        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        assignViews();
        showArchMenu();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void assignViews() {
        radius1= (int) ((ScreenUtils.getScreenWidth()/2- SizeUtils.dp2px(23))*0.8);
        ViewGroup.LayoutParams params = bgHalfCircle.getLayoutParams();
        params.width= LinearLayout.LayoutParams.MATCH_PARENT;
        params.height=ScreenUtils.getScreenWidth()/2;
        bgHalfCircle.setLayoutParams(params);


        imageViews.add(imJiqi);
        imageViews.add(imXiaoxi);
        imageViews.add(imDingpan);
        imageViews.add(imHeyue);
        imageViews.add(imYanjiu);
        imageViews.add(imBoDuan);
        imageViews.add(imLuzhuang);
    }

    @OnClick({R.id.cancel, R.id.luzhuanggou_item, R.id.boduan_item, R.id.heyue_item, R.id.yanjiu_item,
            R.id.dingpan_item, R.id.xiaoxi_item, R.id.jiqi_item})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                hiddenArchMenu();
                break;
            case R.id.luzhuanggou_item:
                AppUtils.addEvent(Constant.EVENT_MODULE_MENU, "click", Constant.EVENT_LZ_DOG, "", "", "");
                EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_LU_DOG_PAGE));
                hiddenArchMenu();
                break;
            case R.id.boduan_item:
                AppUtils.addEvent(Constant.EVENT_MODULE_MENU, "click", Constant.EVENT_BAND_DOG, "", "", "");
                EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_BO_DOG_PAGE));
                hiddenArchMenu();
                break;
            case R.id.yanjiu_item:
                AppUtils.addEvent(Constant.EVENT_MODULE_MENU, "click", Constant.EVENT_RESEARCH_DOG, "", "", "");
                EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_RESEARCH_DOG_PAGE));
                hiddenArchMenu();
                break;
            case R.id.heyue_item:
                AppUtils.addEvent(Constant.EVENT_MODULE_MENU, "click", Constant.EVENT_FUTURE_DOG, "", "", "");
                EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_HEYUE_DOG_PAGE));
                hiddenArchMenu();
                break;
            case R.id.dingpan_item:
                AppUtils.addEvent(Constant.EVENT_MODULE_MENU, "click", Constant.EVENT_STRATEGY_DOG, "", "", "");
                EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_DINGPAN_DOG_PAGE));
                hiddenArchMenu();
                break;
            case R.id.xiaoxi_item:

                hiddenArchMenu();
                break;
            case R.id.jiqi_item:

                hiddenArchMenu();
                break;

        }

    }



    /**
     *平分到140度，所以开始就是0+20
     */
    private void showArchMenu(){
        /***第一步，遍历所要展示的菜单ImageView*/
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = imageViews.get(i);
            PointF point = new PointF();
            /***第二步，根据菜单个数计算每个菜单之间的间隔角度*/
            int avgAngle = (180 / (imageViews.size() - 1));
            /**第三步，根据间隔角度计算出每个菜单相对于水平线起始位置的真实角度**/
            int angle = avgAngle * i;
            /**
             * 圆点坐标：(x0,y0)
             * 半径：r
             * 角度：a0
             * 则圆上任一点为：（x1,y1）
             * x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
             * y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
             */
            /**第四步，根据每个菜单真实角度计算其坐标值**/
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius1;
            point.y = (float) -Math.sin(angle * (Math.PI / 180)) * radius1;

            /**第五步，根据坐标执行位移动画**/
            /**
             * 第一个参数代表要操作的对象
             * 第二个参数代表要操作的对象的属性
             * 第三个参数代表要操作的对象的属性的起始值
             * 第四个参数代表要操作的对象的属性的终止值
             */
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageView, "translationX", 0, point.x);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageView, "translationY", 0, point.y);
            /**动画集合，用来编排动画**/
            AnimatorSet animatorSet = new AnimatorSet();
            /**设置动画时长**/
            animatorSet.setDuration(200);
            /**设置同时播放x方向的位移动画和y方向的位移动画**/
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            /**开始播放动画**/
            animatorSet.start();
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private void hiddenArchMenu(){
        for (int i = 0; i < imageViews.size(); i++) {
            final ImageView imageView = imageViews.get(i);
            PointF point = new PointF();
            int avgAngle = (180 / (imageViews.size() - 1));
            int angle = avgAngle * i;
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius1;
            point.y = (float) -Math.sin(angle * (Math.PI / 180)) * radius1;

            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageView, "translationX", point.x, 0);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageView, "translationY", point.y, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(200);
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            animatorSet.start();
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    imageView.setVisibility(View.INVISIBLE);
                    ArcViewFragment.this.dismiss();
                    super.onAnimationEnd(animation);
                }
            });
        }
    }

}
