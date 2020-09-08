package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import adapter.auditSuggestAdapter.AuditDocSuggestAdapter;
import adapter.auditSuggestAdapter.AuditNoteSuggestAdapter;
import adapter.auditSuggestAdapter.AuditNurseSuggestAdapter;
import adapter.suggestAdapter.DocSuggestAdapter;
import adapter.suggestAdapter.NoteSuggestAdapter;
import adapter.suggestAdapter.NurseSuggestAdapter;
import bean.DocAuditBean;
import bean.HistoryAssessBean;
import ui.AuditActivity;

public class AuditSuggestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuanBeans;

    private final int DOC_SUGGEST = 0;
    private final int NURSE_SUGGEST = 1;
    private final int NOTE_SUGGEST = 2;
    private final int ELSE_SUGGEST = 3;

    public AuditSuggestAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setWenjuanBeans(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuanBeans) {
        if (wenjuanBeans != null) {
            this.wenjuanBeans = wenjuanBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DOC_SUGGEST) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest, parent, false));
        } else if (viewType == NURSE_SUGGEST) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest, parent, false));
        } else if (viewType == NOTE_SUGGEST) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest, parent, false));
        } else {
            return new ElseViewHolder(mLayoutInflater.inflate(R.layout.item_else_suggest, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ELSE_SUGGEST) {// 其他建议
            final ElseViewHolder elseViewHolder = (ElseViewHolder) holder;
            elseViewHolder.tv_suggest_content.setText("其他建议");
            elseViewHolder.et_else_content.setTag(position);//存tag值
            elseViewHolder.et_else_content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int position = (int) elseViewHolder.et_else_content.getTag();//取tag值
                    ((AuditActivity) context).saveEditData(position, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (wenjuanBeans.size() != 0) {
                DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean = wenjuanBeans.get(0);
                viewHolder.rv_suggest_content.setLayoutManager(new LinearLayoutManager(context));
                if (getItemViewType(position) == DOC_SUGGEST) {
                    if (wenjuanBean.getDOCTOR_ADVICE().size() != 0) {
                        // 预防处置建议
                        viewHolder.tv_suggest_content.setText("预防处置建议:");
                        AuditDocSuggestAdapter auditDocSuggestAdapter = new AuditDocSuggestAdapter(context);
                        viewHolder.rv_suggest_content.setAdapter(auditDocSuggestAdapter);
                        auditDocSuggestAdapter.setDoctoradviceBeans(wenjuanBean.getDOCTOR_ADVICE());
                        auditDocSuggestAdapter.setSetCheckBoxChecked(new AuditDocSuggestAdapter.SetCheckBoxChecked() {
                            @Override
                            public void onCheckBoxCheckedListener(List<String> docAdviceStr) {
                                Log.e("SuggestStr", docAdviceStr.size() + "");
                                setSuggestArray.onDocSuggestArrayListener(docAdviceStr);
                            }
                        });
                        viewHolder.rv_suggest_content.setVisibility(View.VISIBLE);
                        viewHolder.tv_zanwu.setVisibility(View.GONE);
                    } else {
                        viewHolder.rv_suggest_content.setVisibility(View.GONE);
                        viewHolder.tv_zanwu.setVisibility(View.VISIBLE);
                    }
                } else if (getItemViewType(position) == NURSE_SUGGEST) {
                    if (wenjuanBean.getNURSE_ADVICE().size() != 0) {
                        // 护理处置建议
                        viewHolder.tv_suggest_content.setText("护理处置建议:");
                        AuditNurseSuggestAdapter auditNurseSuggestAdapter = new AuditNurseSuggestAdapter(context);
                        viewHolder.rv_suggest_content.setAdapter(auditNurseSuggestAdapter);
                        auditNurseSuggestAdapter.setNurseadviceBeans(wenjuanBean.getNURSE_ADVICE());

                        auditNurseSuggestAdapter.setSetNurseChecked(new AuditNurseSuggestAdapter.SetNurseChecked() {
                            @Override
                            public void onNurseCheckedListener(List<String> nuresStr) {
                                Log.e("SuggestStr", nuresStr.size() + "");
                                setSuggestArray.onNurseSuggestArrayListener(nuresStr);
                            }
                        });

                        viewHolder.rv_suggest_content.setVisibility(View.VISIBLE);
                        viewHolder.tv_zanwu.setVisibility(View.GONE);
                    } else {
                        viewHolder.rv_suggest_content.setVisibility(View.GONE);
                        viewHolder.tv_zanwu.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (wenjuanBean.getPATIENT_ADVICE().size() != 0) {
                        // 患者温馨提示
                        viewHolder.tv_suggest_content.setText("患者温馨提示:");
                        AuditNoteSuggestAdapter auditNoteSuggestAdapter = new AuditNoteSuggestAdapter(context);
                        viewHolder.rv_suggest_content.setAdapter(auditNoteSuggestAdapter);
                        auditNoteSuggestAdapter.setPatientadviceBeans(wenjuanBean.getPATIENT_ADVICE());
                        auditNoteSuggestAdapter.setSetNoteChecked(new AuditNoteSuggestAdapter.SetNoteChecked() {
                            @Override
                            public void NoteCheckedListener(List<String> noteStr) {
                                Log.e("SuggestStr", noteStr.size() + "");
                                setSuggestArray.onNoteSuggestArrayListener(noteStr);
                            }
                        });
                        viewHolder.rv_suggest_content.setVisibility(View.VISIBLE);
                        viewHolder.tv_zanwu.setVisibility(View.GONE);
                    } else {
                        viewHolder.rv_suggest_content.setVisibility(View.GONE);
                        viewHolder.tv_zanwu.setVisibility(View.VISIBLE);
                    }

                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (wenjuanBeans == null) {
            wenjuanBeans = new ArrayList<>();
        }
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return DOC_SUGGEST;
        } else if (position == 1) {
            return NURSE_SUGGEST;
        } else if (position == 2) {
            return NOTE_SUGGEST;
        } else {
            return ELSE_SUGGEST;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_suggest_content;
        RecyclerView rv_suggest_content;
        TextView tv_zanwu;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_suggest_content = itemView.findViewById(R.id.tv_suggest_content);
            rv_suggest_content = itemView.findViewById(R.id.rv_suggest_content);
            tv_zanwu = itemView.findViewById(R.id.tv_zanwu);
        }
    }

    public static class ElseViewHolder extends RecyclerView.ViewHolder {
        TextView tv_suggest_content;
        EditText et_else_content;

        public ElseViewHolder(View itemView) {
            super(itemView);
            tv_suggest_content = itemView.findViewById(R.id.tv_suggest_content);
            et_else_content = itemView.findViewById(R.id.et_else_content);
        }
    }

    private SetSuggestArray setSuggestArray;

    public interface SetSuggestArray {
        void onDocSuggestArrayListener(List<String> docAdviceStr);
        void onNurseSuggestArrayListener(List<String> nuresStr);
        void onNoteSuggestArrayListener(List<String> noteStr);
    }

    public void setSetSuggestArray(SetSuggestArray setSuggestArray) {
        this.setSuggestArray = setSuggestArray;
    }
}
