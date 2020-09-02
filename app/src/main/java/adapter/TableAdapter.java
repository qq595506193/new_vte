package adapter;

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

import bean.SelectTablesBean;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<SelectTablesBean.ServerParamsBean.BusinesslistBean> businesslistBeans;

    private List<SelectTablesBean.ServerParamsBean.BusinesslistBean.ListformsBean> listformsBeans;

    public TableAdapter(Context context) {
        this.context = context;
        businesslistBeans = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setBusinesslistBeans(List<SelectTablesBean.ServerParamsBean.BusinesslistBean> businesslistBeans) {
        if (businesslistBeans != null) {
            this.businesslistBeans = businesslistBeans;
        }
    }

    public void setListformsBeans(List<SelectTablesBean.ServerParamsBean.BusinesslistBean.ListformsBean> listformsBeans) {
        if (listformsBeans != null) {
            this.listformsBeans = listformsBeans;
        }
    }

    @NonNull
    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_table_name, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TableAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final SelectTablesBean.ServerParamsBean.BusinesslistBean.ListformsBean listformsBean = listformsBeans.get(position);
        holder.ck_tables.setText(listformsBean.getFORM_NAME());
        if (listformsBean.isSelect()) {
            holder.ck_tables.setChecked(true);
        } else {
            holder.ck_tables.setChecked(false);
        }
        holder.ck_tables.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listformsBeans == null) {
            listformsBeans = new ArrayList<>();
        }
        return listformsBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox ck_tables;
        TextView tv_remind;

        public ViewHolder(View itemView) {
            super(itemView);
            ck_tables = itemView.findViewById(R.id.ck_tables);
            tv_remind = itemView.findViewById(R.id.tv_remind);
        }
    }

    private SetCheckedItem setCheckedItem;// 选中后回调

    public interface SetCheckedItem {
        void onCheckedItemListener();
    }

    public void setSetCheckedItem(SetCheckedItem setCheckedItem) {
        this.setCheckedItem = setCheckedItem;
    }
}
