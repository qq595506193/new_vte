package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.HistoryAssessBean;

public class HistoryTitleAdapter extends RecyclerView.Adapter<HistoryTitleAdapter.ViewHolder> {

    private Context context;

    private List<HistoryAssessBean.ServerParamsBean.ReportListBean> listBeans;

    private LayoutInflater mLayoutInflater;

    public HistoryTitleAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    public void setListBeans(List<HistoryAssessBean.ServerParamsBean.ReportListBean> listBeans) {
        if (listBeans != null) {
            this.listBeans = listBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryTitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_risk_table, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTitleAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final HistoryAssessBean.ServerParamsBean.ReportListBean reportListBean = listBeans.get(position);
        holder.tv_risk_table.setText(reportListBean.getFORM_NAME());
        if (reportListBean.getFORM_ID() == 2) {
            reportListBean.form_id = 2;
        } else if (reportListBean.getFORM_ID() == 1) {
            reportListBean.form_id = 1;
        }

        if (reportListBean.che_color) {
            holder.tv_risk_table.setBackgroundColor(Color.parseColor("#28c48f"));
            holder.tv_risk_table.setTextColor(Color.WHITE);
        } else {
            holder.tv_risk_table.setBackgroundColor(Color.WHITE);
            holder.tv_risk_table.setTextColor(Color.parseColor("#d2d0d0"));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTableItem.setOnClickTableItem(reportListBean.form_id, reportListBean);


                for (int i = 0; i < listBeans.size(); i++) {
                    HistoryAssessBean.ServerParamsBean.ReportListBean bean = listBeans.get(i);
                    if (i == position) {
                        bean.che_color = true;
                    } else {
                        bean.che_color = false;
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listBeans == null) {
            listBeans = new ArrayList<>();
        }
        return listBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_risk_table;
        ConstraintLayout cly;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_risk_table = itemView.findViewById(R.id.tv_risk_table);
            cly = itemView.findViewById(R.id.cly);
        }
    }

    private SetTableItem setTableItem;// 点击条目切换样式回调

    public interface SetTableItem {
        void setOnClickTableItem(int form_id, HistoryAssessBean.ServerParamsBean.ReportListBean reportListBean);
    }

    public void setSetTableItem(SetTableItem setTableItem) {
        this.setTableItem = setTableItem;
    }
}
