package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

import bean.DocAuditBean;
import bean.HistoryAssessBean;

public class DiagnoseAdapter extends RecyclerView.Adapter<DiagnoseAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<DocAuditBean.ServerParamsBean.JibinlistBean> jibinlistBeans;


    //隐藏
    public void setHideList(List<DocAuditBean.ServerParamsBean.JibinlistBean> newList) {
        this.jibinlistBeans = newList;
        notifyDataSetChanged();
    }

    //展开
    public void setOpenList(List<DocAuditBean.ServerParamsBean.JibinlistBean> openList) {
        this.jibinlistBeans = openList;
        notifyDataSetChanged();
    }

    //不需要隐藏/展开
    public void setRealList(List<DocAuditBean.ServerParamsBean.JibinlistBean> realList) {
        this.jibinlistBeans = realList;
        notifyDataSetChanged();
    }

    //清除数据
    public void clearData() {
        if (jibinlistBeans != null) {
            this.jibinlistBeans.clear();
            notifyDataSetChanged();
        }
    }




    public DiagnoseAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setJibinlistBeans(List<DocAuditBean.ServerParamsBean.JibinlistBean> jibinlistBeans) {
        if (jibinlistBeans != null) {
            this.jibinlistBeans = jibinlistBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiagnoseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_jibing_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnoseAdapter.ViewHolder holder, int position) {
        DocAuditBean.ServerParamsBean.JibinlistBean jibinlistBean = jibinlistBeans.get(position);
        holder.jibingName.setText(jibinlistBean.getDIAGNOSIS_DISEASE_NAME());
        holder.jibingTime.setText(jibinlistBean.getDIAGNOSIS_DATE());
    }

    @Override
    public int getItemCount() {
        if (jibinlistBeans == null) {
            jibinlistBeans = new ArrayList<>();
        }
        return jibinlistBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jibingName;
        TextView jibingTime;

        public ViewHolder(View itemView) {
            super(itemView);
            jibingName = itemView.findViewById(R.id.jibing_name);
            jibingTime = itemView.findViewById(R.id.jibing_time);
        }
    }
}
