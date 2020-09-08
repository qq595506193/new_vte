package adapter;

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

import bean.DocAuditBean;

public class SupplementAdapter extends RecyclerView.Adapter<SupplementAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> wxysBeans;

    public SupplementAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setWxysBeans(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> wxysBeans) {
        if (wxysBeans != null) {
            this.wxysBeans = wxysBeans;
        }
        notifyDataSetChanged();
    }

    //隐藏
    public void setHideList(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> newList) {
        this.wxysBeans = newList;
        notifyDataSetChanged();
    }

    //展开
    public void setOpenList(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> openList) {
        this.wxysBeans = openList;
        notifyDataSetChanged();
    }

    //不需要隐藏/展开
    public void setRealList(List<DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> realList) {
        this.wxysBeans = realList;
        notifyDataSetChanged();
    }

    //清除数据
    public void clearData() {
        if (wxysBeans != null) {
            this.wxysBeans.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public SupplementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_jibing_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SupplementAdapter.ViewHolder holder, int position) {
        DocAuditBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean wxysBean = wxysBeans.get(position);

        holder.jibingName.setText(wxysBean.getRISK_FACTOR_NAME());
        holder.jibingTime.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (wxysBeans == null) {
            wxysBeans = new ArrayList<>();
        }
        return wxysBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView jibingName;
        TextView jibingTime;

        public ViewHolder(View itemView) {
            super(itemView);
            jibingName = itemView.findViewById(R.id.jibing_name);
            jibingTime = itemView.findViewById(R.id.jibing_time);
        }
    }
}
