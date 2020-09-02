package adapter;

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
import java.util.List;

import app.App;
import bean.RiskAssessBean;

public class RiskAdater extends RecyclerView.Adapter<RiskAdater.ViewHolder> {


    private Context context;

    private LayoutInflater mLayoutInflater;

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

    @NonNull
    @Override
    public RiskAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_risk_group, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RiskAdater.ViewHolder holder, int position) {
        RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean wenjuanBean = wenjuanBeans.get(position);
        holder.tvScore.setText(wenjuanBean.getFACTOR_GROUP_NAME());
        RiskCheckAdapter riskCheckAdapter = new RiskCheckAdapter(context);
        holder.rvRiskGroup.setLayoutManager(new GridLayoutManager(context, 3));
        holder.rvRiskGroup.setAdapter(riskCheckAdapter);
        riskCheckAdapter.setSublistBeans(wenjuanBean.getSublist());
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
