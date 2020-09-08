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

public class AuditNoteSuggestAdapter extends RecyclerView.Adapter<AuditNoteSuggestAdapter.ViewHolder> {
    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.PATIENTADVICEBean> patientadviceBeans;

    private List<String> noteStr;

    public AuditNoteSuggestAdapter(Context context) {
        this.context = context;
        noteStr = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setPatientadviceBeans(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.PATIENTADVICEBean> patientadviceBeans) {
        if (patientadviceBeans != null) {
            this.patientadviceBeans = patientadviceBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AuditNoteSuggestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AuditNoteSuggestAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.PATIENTADVICEBean patientadviceBean = patientadviceBeans.get(position);
        holder.tv_content.setText(patientadviceBean.getADVICE_CONTENT());
        holder.cb_suggest.setVisibility(View.VISIBLE);
        holder.cb_suggest.setChecked(true);
        if (holder.cb_suggest.isChecked()) {
            noteStr.add(patientadviceBean.getADVICE_CONTENT());
            setNoteChecked.NoteCheckedListener(noteStr);
        }
        holder.cb_suggest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    for (int i = 0; i < noteStr.size(); i++) {
                        if (noteStr.get(i).equals(patientadviceBean.getADVICE_CONTENT())) {
                            noteStr.remove(i);
                        }
                    }
                    setNoteChecked.NoteCheckedListener(noteStr);
                } else {
                    noteStr.add(patientadviceBean.getADVICE_CONTENT());
                    setNoteChecked.NoteCheckedListener(noteStr);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (patientadviceBeans == null) {
            patientadviceBeans = new ArrayList<>();
        }
        return patientadviceBeans.size();
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

    private SetNoteChecked setNoteChecked;

    public interface SetNoteChecked {
        void NoteCheckedListener(List<String> noteStr);
    }

    public void setSetNoteChecked(SetNoteChecked setNoteChecked) {
        this.setNoteChecked = setNoteChecked;
    }
}
