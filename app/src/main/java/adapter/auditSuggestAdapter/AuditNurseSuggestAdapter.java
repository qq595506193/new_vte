package adapter.auditSuggestAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.DocAuditBean;
import bean.HistoryAssessBean;

public class AuditNurseSuggestAdapter extends RecyclerView.Adapter<AuditNurseSuggestAdapter.ViewHolder> {
    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<String> nuresStr;

    private List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.NURSEADVICEBean> nurseadviceBeans;

    public AuditNurseSuggestAdapter(Context context) {
        this.context = context;
        nuresStr = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setNurseadviceBeans(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.NURSEADVICEBean> nurseadviceBeans) {
        if (nurseadviceBeans != null) {
            this.nurseadviceBeans = nurseadviceBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AuditNurseSuggestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AuditNurseSuggestAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.NURSEADVICEBean nurseadviceBean = nurseadviceBeans.get(position);
        holder.tv_content.setText(nurseadviceBean.getADVICE_CONTENT());
        holder.cb_suggest.setVisibility(View.VISIBLE);
        holder.cb_suggest.setChecked(true);
        if (holder.cb_suggest.isChecked()) {
            nuresStr.add(nurseadviceBean.getADVICE_CONTENT());
            setNurseChecked.onNurseCheckedListener(nuresStr);
        }
        holder.cb_suggest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    for (int i = 0; i < nuresStr.size(); i++) {
                        if (nuresStr.get(i).equals(nurseadviceBean.getADVICE_CONTENT())) {
                            nuresStr.remove(i);
                        }
                    }
                    setNurseChecked.onNurseCheckedListener(nuresStr);
                } else {
                    nuresStr.add(nurseadviceBean.getADVICE_CONTENT());
                    setNurseChecked.onNurseCheckedListener(nuresStr);
                }
            }
        });
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
        CheckBox cb_suggest;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            cb_suggest = itemView.findViewById(R.id.cb_suggest);
        }
    }

    private SetNurseChecked setNurseChecked;

    public interface SetNurseChecked {
        void onNurseCheckedListener(List<String> nuresStr);
    }

    public void setSetNurseChecked(SetNurseChecked setNurseChecked) {
        this.setNurseChecked = setNurseChecked;
    }
}
