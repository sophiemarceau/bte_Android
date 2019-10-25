package com.btetop.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.btetop.R;
import com.btetop.adapter.GridItemPopupAdapter;
import com.btetop.bean.GridItem;
import com.btetop.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;


public class CommonGridPopup extends PopupWindow {

    private View contentView;
    private MyGridView grid;
    private RelativeLayout view_grid_down;
    private GridItemPopupAdapter adapter;
    private List<GridItem> mData = new ArrayList<GridItem>();
    private GridItemClickListen mGridItemClickListen;
    private View mArch;

    //记住选中状态
    private boolean rememberStaus=true;

    public CommonGridPopup(View arch, final Activity context, int resId, final String[] data, final GridItemClickListen gridItemClickListen) {

        mArch=arch;

        if (mData != null) {
            mData.clear();
        }
        for (int i = 0; i < data.length; i++) {
            GridItem item = new GridItem();
            item.setTitle(data[i]);
            mData.add(item);
        }

        this.mGridItemClickListen = gridItemClickListen;
        adapter = new GridItemPopupAdapter(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resId, null);
        grid = contentView.findViewById(R.id.grid);
        arch.post(new Runnable() {
            @Override
            public void run() {
                grid.setLayoutParams(new RelativeLayout.LayoutParams(arch.getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT));
            }
        });
        view_grid_down = contentView.findViewById(R.id.view_grid_down);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (rememberStaus) {
                    mData.get(position).setChecked(!mData.get(position).isChecked());
                }

                for (int i = 0; i < mData.size(); i++) {
                    if (i == position) {
                        continue;
                    }
                    mData.get(i).setChecked(false);
                }
                adapter.notifyDataSetChanged(mData);
                mGridItemClickListen.getGridItemInfo(position, mData.get(position));
            }
        });
        view_grid_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridItemClickListen.downPopupDismiss();
            }
        });

        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(00000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                gridItemClickListen.popupDismiss();
            }
        });
        this.update();

    }
     public void setRememberStatus(boolean rememberStatus) {
        this.rememberStaus = rememberStatus;
    }

    public void showPricePopup() {
        if (!this.isShowing()) {
            this.showAsDropDown(mArch);
            adapter.notifyDataSetChanged(mData);
        } else {
            this.dismiss();
        }
    }

    public void updateSelectStatus(int position) {
        mData.get(position).setChecked(!mData.get(position).isChecked());
        for (int i = 0; i < mData.size(); i++) {
            if (i == position) {
                continue;
            }
            mData.get(i).setChecked(false);
        }
        adapter.notifyDataSetChanged(mData);
        mGridItemClickListen.getGridItemInfo(position, mData.get(position));
    }

    public void SetDefaultSelect(int position) {
        mData.get(position).setChecked(!mData.get(position).isChecked());
        for (int i = 0; i < mData.size(); i++) {
            if (i == position) {
                continue;
            }
            mData.get(i).setChecked(false);
        }
        mGridItemClickListen.getGridItemInfo(position, mData.get(position));
    }



    public interface GridItemClickListen {
        void getGridItemInfo(int position, GridItem gridItem);

        void downPopupDismiss();

        void popupDismiss();
    }

}
