package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.App;
import bean.RiskAssessBean;

public class RiskAdater extends RecyclerView.Adapter<RiskAdater.ViewHolder> {


    private Context context;

    private LayoutInflater mLayoutInflater;

    private Map<Integer, Boolean> map = new HashMap<>();

    private int checkedPosition = -1;

    private boolean onBind;

    private List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean> wenjuanBeans;

    public RiskAdater(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setWenjuanBeans(List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean> wenjuanBeans) {
        if (wenjuanBeans != null) {
            this.wenjuanBeans = wenjuanBeans;
        }
        notifyDataSetChanged();
    }

    //得到当前选中的位置
    public int getCheckedPosition() {
        return checkedPosition;
    }

    @NonNull
    @Override
    public RiskAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_risk_group, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RiskAdater.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean wenjuanBean = wenjuanBeans.get(position);
        holder.tvScore.setText(wenjuanBean.getFACTOR_GROUP_NAME());
        final RiskCheckAdapter riskCheckAdapter = new RiskCheckAdapter(context);
        holder.rvRiskGroup.setLayoutManager(new GridLayoutManager(context, 3));
        holder.rvRiskGroup.setAdapter(riskCheckAdapter);
        riskCheckAdapter.setSublistBeans(wenjuanBean.getSublist());

        riskCheckAdapter.setSetOnclickItem(new RiskCheckAdapter.SetOnclickItem() {
            @Override
            public void onclickItemListener(RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean.SublistBean sublistBean) {
                checkedPosition = position;
                for (int i = 0; i < wenjuanBeans.size(); i++) {
                    if (getCheckedPosition() == i) {
                        wenjuanBeans.get(i).getSublist().get(sublistBean.getIndex()).setChecked(true);
                    } else {
                        wenjuanBeans.get(i).getSublist().get(sublistBean.getIndex()).setChecked(false);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (wenjuanBeans == null) {
            wenjuanBeans = new ArrayList<>();
        }
        return wenjuanBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvScore;
        RecyclerView rvRiskGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tv_score);
            rvRiskGroup = itemView.findViewById(R.id.rv_risk_group);
        }
    }
}
