package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.RiskAssessBean;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean> xuanxiang;


    public RadioAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setXuanxiang(List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean> xuanxiang) {
        if (xuanxiang != null) {
            this.xuanxiang = xuanxiang;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RadioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_radio, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RadioAdapter.ViewHolder holder, int position) {
        RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean xuanxiangBean = xuanxiang.get(position);
        RadioButton tempButton = new RadioButton(context);
//        tempButton.setBackgroundResource(R.drawable.edit_bg);    // 设置RadioButton的背景图片
//        tempButton.setButtonDrawable(R.drawable.xxx);            // 设置按钮的样式

        tempButton.setPadding(10, 0, 0, 0);                // 设置文字距离按钮四周的距离
        tempButton.setText(xuanxiangBean.getGROUP_TAB());
        holder.rgGroup.addView(tempButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        tempButton.setTag(xuanxiangBean.getGROUP_TAB_ID());

        if (position == 0) {
            tempButton.setChecked(true);
        }

        holder.rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                final RadioButton tempButton = group.findViewById(checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton
                // 以下就可以对这个RadioButton进行处理了
                tempButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemRadioClick.setItemRadioClickListener((Integer) tempButton.getTag());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (xuanxiang == null) {
            xuanxiang = new ArrayList<>();
        }
        return xuanxiang.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RadioGroup rgGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            rgGroup = itemView.findViewById(R.id.rg_group);
        }
    }

    private ItemRadioClick itemRadioClick;//radio点击事件回调

    public interface ItemRadioClick {
        void setItemRadioClickListener(int tag);
    }

    public void setItemRadioClick(ItemRadioClick itemRadioClick) {
        this.itemRadioClick = itemRadioClick;
    }
}
