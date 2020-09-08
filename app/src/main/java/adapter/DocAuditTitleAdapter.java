package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.DocAuditBean;

public class DocAuditTitleAdapter extends RecyclerView.Adapter<DocAuditTitleAdapter.ViewHodler> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<DocAuditBean.ServerParamsBean.ReportListBean> reportListBeans;
    private DocAuditTableAdapter docAuditTableAdapter;

    public DocAuditTitleAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setReportListBeans(List<DocAuditBean.ServerParamsBean.ReportListBean> reportListBeans) {
        if (reportListBeans != null) {
            this.reportListBeans = reportListBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DocAuditTitleAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHodler(mLayoutInflater.inflate(R.layout.item_risk_table, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DocAuditTitleAdapter.ViewHodler holder, @SuppressLint("RecyclerView") final int position) {
        final DocAuditBean.ServerParamsBean.ReportListBean reportListBean = reportListBeans.get(position);
        holder.tv_risk_table.setText(reportListBean.getFORM_NAME());
//        holder.tv_risk_table.setTextColor(Color.BLACK);

        //
        // recyclerview 添加item的下划线，和方向
        holder.rv_do.setItemAnimator(new DefaultItemAnimator());
        holder.rv_do.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL));
        //
        holder.rv_do.setLayoutManager(new LinearLayoutManager(context));

        docAuditTableAdapter = new DocAuditTableAdapter(context);
        holder.rv_do.setAdapter(docAuditTableAdapter);
        setAdaperHeader();// 设置表头
        docAuditTableAdapter.setSublistBeans(reportListBean.getWENJUAN());
        docAuditTableAdapter.setSetAuditBtn(new DocAuditTableAdapter.SetAuditBtn() {
            @Override
            public void onAuditBtnListener(DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean) {
                setTableItem.setOnClickTableItem(position, wenjuanBean);
            }
        });

    }

    private void setAdaperHeader() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doc_audit_header, null);
        docAuditTableAdapter.setHeader(view);
    }

    @Override
    public int getItemCount() {
        if (reportListBeans == null) {
            reportListBeans = new ArrayList<>();
        }
        return reportListBeans.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        TextView tv_risk_table;

        RecyclerView rv_do;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_risk_table = itemView.findViewById(R.id.tv_risk_table);
            rv_do = itemView.findViewById(R.id.rv_do);
        }
    }

    private SetTableItem setTableItem;// 点击审核报告回调

    public interface SetTableItem {
        void setOnClickTableItem(int position, DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean);
    }

    public void setSetTableItem(SetTableItem setTableItem) {
        this.setTableItem = setTableItem;
    }
}
