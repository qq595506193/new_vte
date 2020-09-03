package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.HistoryAssessBean;

public class ElementAdater extends RecyclerView.Adapter<ElementAdater.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean> wxysBeans;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private View mHeaderView;

    public View getHeader() {
        return mHeaderView;
    }

    public void setHeader(View headerView) {

        this.mHeaderView = headerView;
        //插入新的item
        notifyItemInserted(0);
    }

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
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            //需要传入parent，不然item不能居中
//            return new NormalViewholder(mHeaderView);
            return new ViewHolder(mLayoutInflater.inflate(R.layout.item_wxys_header, parent, false));
        }
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_wxys, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ElementAdater.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.WxysBean wxysBean = wxysBeans.get(position - 1);
        holder.tv_wxys.setText(wxysBean.getRISK_FACTOR_NAME());// 危险因素
        holder.tv_time.setText(wxysBean.getEFFECTIVE_TIME() + "");
        holder.tv_leve_sum.setText(wxysBean.getFACTOR_SEQ() + "分");
        holder.tv_index.setText(wxysBean.getANALYSIS_SOURCE_STR());
    }

    @Override
    public int getItemCount() {
        if (wxysBeans == null) {
            wxysBeans = new ArrayList<>();
        }
        return wxysBeans.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_wxys;
        TextView tv_time;
        TextView tv_leve_sum;
        TextView tv_index;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_wxys = itemView.findViewById(R.id.tv_wxys);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_leve_sum = itemView.findViewById(R.id.tv_leve_sum);
            tv_index = itemView.findViewById(R.id.tv_index);
        }
    }
}
