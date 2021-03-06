package com.btetop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

import com.blankj.utilcode.util.SizeUtils;
import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.service.UserService;
import com.btetop.utils.BitmapUtil;
import com.btetop.utils.ShareUtils;
import com.guoziwei.klinelib.util.BitmapResize;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class ShareShequDialog extends DialogFragment implements View.OnClickListener {
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.share_shequ_dialog_layout, null);
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

    private static Bitmap createShareBitmap(Bitmap portraitsBitmap,String title,String nickName,String content){
        int targetScaleWidth=1080;
        Bitmap sizePortraitsBitmap = BitmapResize.resizeScaleBitmap(portraitsBitmap, 105);
        BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
        bfoOptions.inScaled = false;
        Bitmap bgBitmap = BitmapFactory.decodeResource(BteTopApplication.getInstance().getResources(), R.mipmap.bg_share_shequ, bfoOptions);

        float scale = (float) targetScaleWidth / bgBitmap.getWidth();
        int targetScaleBackgroundHeight = (int) (scale * bgBitmap.getHeight());

        Bitmap newBitmap = Bitmap.createBitmap(targetScaleWidth, targetScaleBackgroundHeight, Bitmap.Config.ARGB_8888);

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        Canvas canvas=new Canvas(newBitmap);

        //绘制背景
        Rect topSrcRect=new Rect(0,0,bgBitmap.getWidth(),bgBitmap.getHeight());
        Rect topDstRect=new Rect(0,0,targetScaleWidth, targetScaleBackgroundHeight);
        canvas.drawBitmap(bgBitmap,topSrcRect,topDstRect,paint);
        //绘制标题
        Rect titleRect=new Rect(0,0,targetScaleWidth,218);
        Paint paintTitle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTitle.setColor(Color.rgb(255, 255, 255));
        paintTitle.setTextSize(56);
        paintTitle.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (titleRect.bottom + titleRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(title,titleRect.centerX(),145, paintTitle);

        //绘制头像
        Paint iconPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        iconPaint.setFilterBitmap(true);
        iconPaint.setDither(true);
        canvas.drawBitmap(sizePortraitsBitmap,62,263,iconPaint);
        //绘制nickName
        Paint nickNamePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        nickNamePaint.setColor(Color.rgb(48,140,221));
        nickNamePaint.setTextSize(44);
        Rect bounds = new Rect();
        nickNamePaint.getTextBounds(nickName, 0, nickName.length(), bounds);
        canvas.drawText(nickName,196,318,nickNamePaint);
        //绘制内容
        TextView tv = new TextView(BteTopApplication.getInstance());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(825, 1150);
        tv.setLayoutParams(layoutParams);
        tv.setMaxLines(21);
        tv.setTextSize(SizeUtils.px2sp(44));
        tv.setTextColor(Color.rgb(98,106,117));
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(content);
        tv.setBackgroundColor(Color.TRANSPARENT);
        Bitmap testB;
        testB = Bitmap.createBitmap(825, 1150, Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(testB);
        tv.layout(0,0,825,1150);
        tv.draw(c);

        canvas.drawBitmap(testB,196,359,iconPaint);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newBitmap;
    }

    private static Bitmap createShareBitmap(Bitmap oriBitmap, Bitmap qrCodeBitmap) {
        int targetScaleWidth=1080;

        if (oriBitmap == null || qrCodeBitmap == null) return null;

        //计算新画布的大小
        float scale = (float) targetScaleWidth / oriBitmap.getWidth();
        int targetScaleBackgroundHeight = (int) (scale * oriBitmap.getHeight());

        int targetScaleQRHeight =(int)(scale * qrCodeBitmap.getHeight());


        //创建一个空的Bitmap(内存区域),宽度等于第一张图片的宽度，高度等于两张图片高度总和
        Bitmap newBitmap = Bitmap.createBitmap(targetScaleWidth, targetScaleBackgroundHeight, Bitmap.Config.ARGB_8888);

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        Canvas canvas = new Canvas(newBitmap);
        Rect topSrcRect=new Rect(0,0,oriBitmap.getWidth(),oriBitmap.getHeight());
        Rect topDstRect=new Rect(0,0,targetScaleWidth, targetScaleBackgroundHeight);

        Rect bottomSrcRect=new Rect(0,0,qrCodeBitmap.getWidth(),qrCodeBitmap.getHeight());
        Rect bottomDstRect=new Rect(0, targetScaleBackgroundHeight - targetScaleQRHeight,targetScaleWidth, targetScaleBackgroundHeight);

        //绘制背景
        canvas.drawBitmap(oriBitmap,topSrcRect,topDstRect,paint);
        //绘制二维码
        canvas.drawBitmap(qrCodeBitmap,bottomSrcRect,bottomDstRect,paint);


        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newBitmap;

    }

    private static Bitmap createShareBitmap(Context context) {

        BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
        bfoOptions.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.share_template, bfoOptions);
        if (bitmap == null) {
            return null;
        }

        Bitmap newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        Bitmap qrCodeBitmap = null;

        if (!TextUtils.isEmpty(UserService.getCurrentUserbase64Url())) {
            qrCodeBitmap = BitmapUtil.stringtoBitmap(UserService.getCurrentUserbase64Url());
        } else {
            qrCodeBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.inviting_friends_no_data, bfoOptions);
        }

        Bitmap newQRBitmap = BitmapUtil.scaleBitmap(qrCodeBitmap, (int) (qrCodeBitmap.getWidth() * 1.25), (int) (qrCodeBitmap.getHeight() * 1.25));


        //创建一个bitmap
        Canvas canvas = new Canvas(newBitmap);
        //在画布 0，0坐标上开始绘制原始图片
        //canvas.drawBitmap(bitmap, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(newQRBitmap, 229, 1201, null);


        if (!TextUtils.isEmpty(UserService.getCurrentUserInviteCode())) {
            String text = "邀请码:" + UserService.getCurrentUserInviteCode();

            //在画布上写文字
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.rgb(48, 140, 221));
//            paint.setTextSize((dp2px(context, 10)));
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            canvas.drawText(text, 375, 1236, paint);
            // 保存
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newBitmap;
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

    /**
     * 初始化数据
     */
    private void initData() {
        ivShareImage.setImageBitmap(bitmap);
    }

    private void doShare(SHARE_MEDIA media) {
        ShareUtils.sharePic(getActivity(), bitmap, media);
    }


    public static ShareShequDialog newInstance(Bitmap portraitsBitmap,String title,String nickName,String content) {
        ShareShequDialog sharePicDialog = new ShareShequDialog();
        sharePicDialog.bitmap = createShareBitmap(portraitsBitmap,title,nickName,content);
        return sharePicDialog;

    }

}
