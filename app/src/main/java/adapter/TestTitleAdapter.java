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

import bean.RiskAssessBean;
import bean.TestTitleBean;

public class TestTitleAdapter extends RecyclerView.Adapter<TestTitleAdapter.ViewHolder> {

    private Context context;

    private List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean> mWenjuannameBeans;

    private LayoutInflater mLayoutInflater;


    public TestTitleAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setmWenjuannameBeans(List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean> wenjuannameBeans) {
        mWenjuannameBeans = wenjuannameBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TestTitleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_risk_table, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestTitleAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final RiskAssessBean.ServerParamsBean.WENJUANNAMEBean wenjuannameBean = mWenjuannameBeans.get(position);
        holder.tv_risk_table.setText(wenjuannameBean.getFORM_NAME());
        if (wenjuannameBean.isChe_color()) {
            holder.tv_risk_table.setBackgroundColor(Color.parseColor("#28c48f"));
            holder.tv_risk_table.setTextColor(Color.WHITE);
        } else {
            holder.tv_risk_table.setBackgroundColor(Color.WHITE);
            holder.tv_risk_table.setTextColor(Color.parseColor("#d2d0d0"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.onItemClickListener(wenjuannameBean.getFORM_ID());
                for (int i = 0; i < mWenjuannameBeans.size(); i++) {
                    RiskAssessBean.ServerParamsBean.WENJUANNAMEBean bean = mWenjuannameBeans.get(i);
                    if (i == position) {
                        bean.setChe_color(true);
                    } else {
                        bean.setChe_color(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mWenjuannameBeans == null) {
            mWenjuannameBeans = new ArrayList<>();
        }
        return mWenjuannameBeans.size();
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

    // 条目点击事件
    private SetOnItemClickListener setOnItemClickListener;

    public interface SetOnItemClickListener {
        void onItemClickListener(int form_id);
    }

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }
}
