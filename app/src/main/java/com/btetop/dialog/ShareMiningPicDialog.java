package com.btetop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.bean.BaseBean;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.BitmapUtil;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ShareUtils;
import com.guoziwei.klinelib.util.BitmapResize;
import com.umeng.socialize.bean.SHARE_MEDIA;

import rx.functions.Action1;

public class ShareMiningPicDialog extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "ShareGetPicActivity";
    public static final String HOME_SHARE_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/btetop/pics";
    private ImageView ivShareImage;

    private Bitmap bitmap = null;


    private TextView cancel;

    LinearLayout wechat, wxcircle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.share_mining_dialog_layout, null);
        initViews(view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 初始化views
     *
     * @param view
     */
    private void initViews(View view) {
        ivShareImage = view.findViewById(R.id.iv_share_home_big);
        wechat = view.findViewById(R.id.wechat);
        wxcircle = view.findViewById(R.id.wxcircle);
        cancel = view.findViewById(R.id.cancel);

        wechat.setOnClickListener(this);
        wxcircle.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private static Bitmap createShareBitmap(Context context) {

        Bitmap qrCodeBitmap = null;
        if (!TextUtils.isEmpty(UserService.getCurrentUserMiningbase64Url())) {
            qrCodeBitmap = BitmapUtil.stringtoBitmap(UserService.getCurrentUserMiningbase64Url());
        }

        int targetScaleWidth = 1125;
        Bitmap sizePortraitsBitmap = BitmapResize.resizeScaleBitmap(qrCodeBitmap, 300);
        BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
        bfoOptions.inScaled = false;
        Bitmap bgBitmap = BitmapFactory.decodeResource(BteTopApplication.getInstance().getResources(), R.mipmap.bg_mining_share, bfoOptions);

        float scale = (float) targetScaleWidth / bgBitmap.getWidth();
        int targetScaleBackgroundHeight = (int) (scale * bgBitmap.getHeight());

        Bitmap newBitmap = Bitmap.createBitmap(targetScaleWidth, targetScaleBackgroundHeight, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        Canvas canvas = new Canvas(newBitmap);

        //绘制背景
        Rect topSrcRect = new Rect(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        Rect topDstRect = new Rect(0, 0, targetScaleWidth, targetScaleBackgroundHeight);
        canvas.drawBitmap(bgBitmap, topSrcRect, topDstRect, paint);

        int width = sizePortraitsBitmap.getWidth();
        int endLeft = (targetScaleWidth - width) / 2;

        canvas.drawBitmap(qrCodeBitmap, endLeft, 1250, null);

        if (!TextUtils.isEmpty(UserService.getCurrentUserInviteCode())) {
            String text = "" + UserService.getCurrentUserInviteCode();

            //在画布上写文字
            Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint2.setColor(Color.rgb(114, 89, 208));
            paint2.setTextSize((dp2px(context, 37)));
            paint2.setTypeface(Typeface.DEFAULT_BOLD);
            Rect bounds = new Rect();
            paint2.getTextBounds(text, 0, text.length(), bounds);

            canvas.drawText(text, 350, 900, paint2);
            // 保存
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newBitmap;
    }


    /**
     * dip转pix
     *
     * @param context
     * @param dp
     * @return
     */
    private static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share_home_big:
                doShare(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.wechat:
                doShare(SHARE_MEDIA.WEIXIN);
                //點擊分享按鈕 就增加積分
                ShareUtils.submitShare("wechat", "Page", "SharePic");
                this.dismiss();
                break;
            case R.id.wxcircle:
                doShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                ShareUtils.submitShare("penyouquan", "Page", "SharePic");
                this.dismiss();
                break;
            case R.id.cancel:
                this.dismiss();
                break;
        }


    }

    public doShare mShare;

    public interface doShare {
        void doShare();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ivShareImage.setImageBitmap(bitmap);
    }

    private void doShare(SHARE_MEDIA media) {

        ShareUtils.sharePic(getActivity(), bitmap, media);
        mShare.doShare();
    }


    public static ShareMiningPicDialog newInstance(Context context, doShare doshare) {
        ShareMiningPicDialog sharePicDialog = new ShareMiningPicDialog();
        sharePicDialog.bitmap = createShareBitmap(context);
        sharePicDialog.mShare = doshare;
        return sharePicDialog;
    }


    public void doDigShare() {
        BteTopService.doDigShare()
                .compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

}
