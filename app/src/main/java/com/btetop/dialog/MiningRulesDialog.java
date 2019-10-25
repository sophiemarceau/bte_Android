package com.btetop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.MiningRuleBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.List;

import rx.functions.Action1;

public class MiningRulesDialog {


    private Dialog dialog;

    private ImageView ivconfirm, iv_cancel;
    private TextView iv_mining_rules_bg;

    private Context mContext;

    public void show(Context context) {
        mContext = context;
        View view = View.inflate(context, R.layout.dialog_mining_rules, null);

        dialog = new Dialog(context, R.style.MyDialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        initViews(view);
        initData();
        dialog.show();

    }

    String DefaultInfo = "1.挖矿时间为2018年10月15日至2018年11月15日\n" +
            "2.比特易将每天放入3.3BTC放入奖励池\n" +
            "3.每天8点和20:00分别释放一次挖矿奖励，用户可在此时间后手动领取挖矿奖励，领取后进入下一时段挖矿\n" +
            "4.用户每时段获得的奖励由参与该时段挖矿的时间及算力共同决定\n" +
            "5.奖励发放形式：挖矿结束后，用户获得的挖矿奖励将放入比特易区块链策略投资基金中，三个月后用户可提现收益部分(往期最高三个月最高收益60%)\n" +
            "6.比特易拥有对挖矿活动的最终解释权";

    private void initData() {
        BteTopService.getMiningRule()
                .compose(RxUtil.<MiningRuleBean>mainAsync())
                .subscribe(new Action1<MiningRuleBean>() {
                    @Override
                    public void call(MiningRuleBean miningRuleBean) {
                        if (miningRuleBean != null) {
                            String info = "";
                            int line = 0;
                            List<String> data = miningRuleBean.getData();

                            for (int i = data.size() - 1; i >= 0; i--) {
                                String s = info + (line + 1) + "." + data.get(line) + "\n";
                                line++;
                                info = s;
                            }

                            iv_mining_rules_bg.setText("\n"+info);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        iv_mining_rules_bg.setText(DefaultInfo);
                    }
                });
    }


    private void initViews(View view) {

        iv_cancel = view.findViewById(R.id.iv_mining_rules_close);
        iv_mining_rules_bg = view.findViewById(R.id.iv_mining_rules_bg);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
