package adapter.suggestAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.HistoryAssessBean;

public class NurseSuggestAdapter extends RecyclerView.Adapter<NurseSuggestAdapter.ViewHolder> {
    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.NURSEADVICEBean> nurseadviceBeans;

    public NurseSuggestAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setNurseadviceBeans(List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.NURSEADVICEBean> nurseadviceBeans) {
        if (nurseadviceBeans != null) {
            this.nurseadviceBeans = nurseadviceBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NurseSuggestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NurseSuggestAdapter.ViewHolder holder, int position) {
        holder.tv_content.setText(nurseadviceBeans.get(position).getADVICE_CONTENT());
    }

    @Override
    public int getItemCount() {
        if (nurseadviceBeans == null) {
            nurseadviceBeans = new ArrayList<>();
        }
        return nurseadviceBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
