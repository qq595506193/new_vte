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

public class AuditDocSuggestAdapter extends RecyclerView.Adapter<AuditDocSuggestAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.DOCTORADVICEBean> doctoradviceBeans;

    private List<String> docAdviceStr;

    public AuditDocSuggestAdapter(Context context) {
        this.context = context;
        docAdviceStr = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setDoctoradviceBeans(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.DOCTORADVICEBean> doctoradviceBeans) {
        if (doctoradviceBeans != null) {
            this.doctoradviceBeans = doctoradviceBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AuditDocSuggestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AuditDocSuggestAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.DOCTORADVICEBean doctoradviceBean = doctoradviceBeans.get(position);
        holder.tv_content.setText(doctoradviceBean.getADVICE_CONTENT());
        holder.cb_suggest.setVisibility(View.VISIBLE);
        holder.cb_suggest.setChecked(true);

        if (holder.cb_suggest.isChecked()) {
            docAdviceStr.add( doctoradviceBean.getADVICE_CONTENT());
            setCheckBoxChecked.onCheckBoxCheckedListener(docAdviceStr);
        }
        holder.cb_suggest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    for (int i = 0; i < docAdviceStr.size(); i++) {
                        if (docAdviceStr.get(i).equals(doctoradviceBean.getADVICE_CONTENT())) {
                            docAdviceStr.remove(i);
                        }
                    }
                    setCheckBoxChecked.onCheckBoxCheckedListener(docAdviceStr);
                } else {
                    docAdviceStr.add(doctoradviceBean.getADVICE_CONTENT());
                    setCheckBoxChecked.onCheckBoxCheckedListener(docAdviceStr);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (doctoradviceBeans == null) {
            doctoradviceBeans = new ArrayList<>();
        }
        return doctoradviceBeans.size();
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

    private SetCheckBoxChecked setCheckBoxChecked;

    public interface SetCheckBoxChecked {
        void onCheckBoxCheckedListener(List<String> docAdviceStr);
    }

    public void setSetCheckBoxChecked(SetCheckBoxChecked setCheckBoxChecked) {
        this.setCheckBoxChecked = setCheckBoxChecked;
    }
}
