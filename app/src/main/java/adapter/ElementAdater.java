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

import bean.HistoryAssessBean;

public class ElementAdater extends RecyclerView.Adapter<ElementAdater.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> wxysBeans;

    public ElementAdater(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setWxysBeans(List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> wxysBeans) {
        if (wxysBeans != null) {
            this.wxysBeans = wxysBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ElementAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.wxys_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ElementAdater.ViewHolder holder, int position) {
        if (wxysBeans.size() != 0) {
            HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean wxysBean = wxysBeans.get(position);
            if (wxysBean.getSCORE_SHOW_TYPE() == 30) {
                holder.tv_wxys.setText(wxysBean.getCURRENT_DESC());
                holder.tv_wxys_time.setText("-(" + wxysBean.getANALYSIS_SOURCE_STR() + ")");
            } else {
                holder.tv_wxys.setText(wxysBean.getRISK_FACTOR_NAME());// 危险因素名字
                holder.tv_wxys_time.setText("-(" + wxysBean.getANALYSIS_SOURCE_STR() + ")");
            }
        } else {
            holder.tv_wxys.setText("暂无危险因素");
            holder.tv_wxys_time.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (wxysBeans == null) {
            wxysBeans = new ArrayList<>();
        }
        return wxysBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_wxys;
        TextView tv_wxys_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_wxys = itemView.findViewById(R.id.tv_wxys);
            tv_wxys_time = itemView.findViewById(R.id.tv_wxys_time);
        }
    }
}
