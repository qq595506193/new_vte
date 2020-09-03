package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import adapter.suggestAdapter.DocSuggestAdapter;
import adapter.suggestAdapter.NoteSuggestAdapter;
import adapter.suggestAdapter.NurseSuggestAdapter;
import bean.HistoryAssessBean;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuanBeans;

    private final int DOC_SUGGEST = 0;
    private final int NURSE_SUGGEST = 1;
    private final int NOTE_SUGGEST = 2;

    public SuggestAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setWenjuanBeans(List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuanBeans) {
        if (wenjuanBeans != null) {
            this.wenjuanBeans = wenjuanBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SuggestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DOC_SUGGEST) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest, parent, false));
        } else if (viewType == NURSE_SUGGEST) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest, parent, false));
        } else {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_suggest, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestAdapter.ViewHolder holder, int position) {
        if (wenjuanBeans.size() != 0) {
            HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean = wenjuanBeans.get(0);
            holder.rv_suggest_content.setLayoutManager(new LinearLayoutManager(context));
            if (getItemViewType(position) == DOC_SUGGEST) {
                if (wenjuanBean.getDOCTOR_ADVICE().size() != 0) {
                    // 预防处置建议
                    holder.tv_suggest_content.setText("预防处置建议:");
                    DocSuggestAdapter docSuggestAdapter = new DocSuggestAdapter(context);
                    holder.rv_suggest_content.setAdapter(docSuggestAdapter);
                    docSuggestAdapter.setDoctoradviceBeans(wenjuanBean.getDOCTOR_ADVICE());

                    holder.rv_suggest_content.setVisibility(View.VISIBLE);
                    holder.tv_zanwu.setVisibility(View.GONE);
                } else {
                    holder.rv_suggest_content.setVisibility(View.GONE);
                    holder.tv_zanwu.setVisibility(View.VISIBLE);
                }
            } else if (getItemViewType(position) == NURSE_SUGGEST) {
                if (wenjuanBean.getNURSE_ADVICE().size() != 0) {
                    // 护理处置建议
                    holder.tv_suggest_content.setText("护理处置建议:");
                    NurseSuggestAdapter nurseSuggestAdapter = new NurseSuggestAdapter(context);
                    holder.rv_suggest_content.setAdapter(nurseSuggestAdapter);
                    nurseSuggestAdapter.setNurseadviceBeans(wenjuanBean.getNURSE_ADVICE());

                    holder.rv_suggest_content.setVisibility(View.VISIBLE);
                    holder.tv_zanwu.setVisibility(View.GONE);
                } else {
                    holder.rv_suggest_content.setVisibility(View.GONE);
                    holder.tv_zanwu.setVisibility(View.VISIBLE);
                }

            } else {
                if (wenjuanBean.getPATIENT_ADVICE().size() != 0) {
                    // 患者温馨提示
                    holder.tv_suggest_content.setText("患者温馨提示:");
                    NoteSuggestAdapter noteSuggestAdapter = new NoteSuggestAdapter(context);
                    holder.rv_suggest_content.setAdapter(noteSuggestAdapter);
                    noteSuggestAdapter.setPatientadviceBeans(wenjuanBean.getPATIENT_ADVICE());

                    holder.rv_suggest_content.setVisibility(View.VISIBLE);
                    holder.tv_zanwu.setVisibility(View.GONE);
                } else {
                    holder.rv_suggest_content.setVisibility(View.GONE);
                    holder.tv_zanwu.setVisibility(View.VISIBLE);
                }

            }
        } else {
            holder.tv_suggest_content.setText("暂无建议");
        }
    }

    @Override
    public int getItemCount() {
        if (wenjuanBeans == null) {
            wenjuanBeans = new ArrayList<>();
        }
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return DOC_SUGGEST;
        } else if (position == 1) {
            return NURSE_SUGGEST;
        } else {
            return NOTE_SUGGEST;
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
}
