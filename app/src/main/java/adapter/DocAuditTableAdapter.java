package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.DocAuditBean;

public class DocAuditTableAdapter extends RecyclerView.Adapter<DocAuditTableAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;

    private List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuanBeans;

    private View mHeaderView;

    public View getHeader() {
        return mHeaderView;
    }

    public DocAuditTableAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setHeader(View headerView) {

        this.mHeaderView = headerView;
        //插入新的item
        notifyItemInserted(0);
    }

    public void setSublistBeans(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuanBeans) {
        if (wenjuanBeans != null) {
            this.wenjuanBeans = wenjuanBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DocAuditTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            //需要传入parent，不然item不能居中
//            return new NormalViewholder(mHeaderView);
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_doc_audit_header, parent, false));
        }
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DocAuditTableAdapter.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean = wenjuanBeans.get(position - 1);
        for (DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.SublistBean bean : wenjuanBean.getSublist()) {
            holder.tv_bianma.setText(bean.getREPORT_CODE());
            holder.tv_leve_sum.setText(bean.getCURRENT_RISK_VALUE() + "分");
            holder.tv_leve.setText(bean.getRISK_NAME());
            holder.tv_risk_content.setText(bean.getRISK_DETAIL_DESC());
            holder.tv_risk_time.setText(bean.getREPORT_TIME());
            holder.btn_do.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("DocAuditTableAdapter", "点击了审核报告按钮");
                    setAuditBtn.onAuditBtnListener(wenjuanBean);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (wenjuanBeans == null) {
            wenjuanBeans = new ArrayList<>();
        }
        return wenjuanBeans.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bianma;
        TextView tv_leve_sum;
        TextView tv_leve;
        TextView tv_risk_content;
        TextView tv_risk_time;
        Button btn_do;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_bianma = itemView.findViewById(R.id.tv_bianma);
            tv_leve_sum = itemView.findViewById(R.id.tv_leve_sum);
            tv_leve = itemView.findViewById(R.id.tv_leve);
            tv_risk_content = itemView.findViewById(R.id.tv_risk_content);
            tv_risk_time = itemView.findViewById(R.id.tv_risk_time);
            btn_do = itemView.findViewById(R.id.btn_do);
        }
    }

    private SetAuditBtn setAuditBtn;

    public interface SetAuditBtn {
        void onAuditBtnListener(DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean);
    }

    public void setSetAuditBtn(SetAuditBtn setAuditBtn) {
        this.setAuditBtn = setAuditBtn;
    }
}
