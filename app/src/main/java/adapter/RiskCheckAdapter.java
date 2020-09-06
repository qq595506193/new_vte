package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.RiskAssessBean;

public class RiskCheckAdapter extends RecyclerView.Adapter<RiskCheckAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;


    private boolean onBind;


    private List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean.SublistBean> sublistBeans;

    public RiskCheckAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    public void setSublistBeans(List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean.SublistBean> sublistBeans) {
        if (sublistBeans != null) {
            this.sublistBeans = sublistBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RiskCheckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_check, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RiskCheckAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean.SublistBean sublistBean = sublistBeans.get(position);
        holder.cbCheck.setText(sublistBean.getRISK_FACTOR_NAME());
        holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sublistBean.setChecked(true);
                    sublistBean.setIndex(position);
                    setOnclickItem.onclickItemListener(sublistBean);
                } else {
                    sublistBean.setChecked(false);
                    sublistBean.setIndex(-1);
                    setOnclickItem.onclickItemListener(sublistBean);
                }
                if (!onBind) {
                    notifyDataSetChanged();
                }
            }
        });
        onBind = true;
//        if (sublistBean.getMUTEX_GROUP() == 1) {
////            if (map != null && map.containsKey(position)) {
////                holder.cbCheck.setChecked(true);
////            } else {
////                holder.cbCheck.setChecked(false);
////            }
////        } else {
////
////        }
        if (sublistBean.isChecked()) {
            holder.cbCheck.setChecked(true);
        } else {
            holder.cbCheck.setChecked(false);
        }
        onBind = false;
    }

    @Override
    public int getItemCount() {
        if (sublistBeans == null) {
            sublistBeans = new ArrayList<>();
        }
        return sublistBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbCheck;
        ImageView ivWenhao;
        EditText etOther;
        TextView tvWenhaoContent;

        public ViewHolder(View itemView) {
            super(itemView);
            cbCheck = itemView.findViewById(R.id.cb_check);
        }
    }

    private SetOnclickItem setOnclickItem;// 选中年龄互斥

    public interface SetOnclickItem {
        void onclickItemListener(RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean.SublistBean sublistBean);
    }

    public void setSetOnclickItem(SetOnclickItem setOnclickItem) {
        this.setOnclickItem = setOnclickItem;
    }
}
