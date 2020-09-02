package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.SelectTablesBean;

/**
 * Created by TriumphalSun
 * on 2019/7/15 0015
 */
public class SelectTablesAdapter extends RecyclerView.Adapter<SelectTablesAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<SelectTablesBean.ServerParamsBean.BusinesslistBean> businesslistBeans;

    public SelectTablesAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setBusinesslistBeans(List<SelectTablesBean.ServerParamsBean.BusinesslistBean> businesslistBeans) {
        if (businesslistBeans != null) {
            this.businesslistBeans = businesslistBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public SelectTablesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_select_tables, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTablesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final SelectTablesBean.ServerParamsBean.BusinesslistBean businesslistBean = businesslistBeans.get(position);
        holder.tv_table_name.setText(businesslistBean.getASSESS_BUSINESS());// 评估项名字
        if (businesslistBean.isChe_color()) {
            holder.tv_table_name.setBackgroundColor(Color.WHITE);
            holder.tv_table_name.setTextColor(Color.parseColor("#abd7b4"));
        } else {
            holder.tv_table_name.setBackgroundColor(Color.parseColor("#eceff3"));
            holder.tv_table_name.setTextColor(Color.BLACK);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < businesslistBeans.size(); i++) {
                    SelectTablesBean.ServerParamsBean.BusinesslistBean businesslistBean = businesslistBeans.get(i);
                    if (i == position) {
                        businesslistBean.setChe_color(true);
                    } else {
                        businesslistBean.setChe_color(false);
                    }
                }
//                setSelectTables.onSelectTables(businesslistBeans,businesslistBean);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (businesslistBeans == null) {
            businesslistBeans = new ArrayList<>();
        }
        return businesslistBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_table_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_table_name = itemView.findViewById(R.id.tv_table_name);
        }
    }

    // 选表切换回调
    private SetSelectTables setSelectTables;

    public interface SetSelectTables {
        void onSelectTables(List<SelectTablesBean.ServerParamsBean.BusinesslistBean> businesslistBeans, SelectTablesBean.ServerParamsBean.BusinesslistBean businesslistBean);
    }

    public void setSetSelectTables(SetSelectTables setSelectTables) {
        this.setSelectTables = setSelectTables;
    }
}
