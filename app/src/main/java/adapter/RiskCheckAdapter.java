package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.RiskAssessBean;

public class RiskCheckAdapter extends RecyclerView.Adapter<RiskCheckAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean.SublistBean> sublistBeans;

    public RiskCheckAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setSublistBeans(List<RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean.SublistBean> sublistBeans) {
        if (sublistBeans != null) {
            this.sublistBeans = sublistBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RiskCheckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_check, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RiskCheckAdapter.ViewHolder holder, int position) {
        RiskAssessBean.ServerParamsBean.WENJUANNAMEBean.XUANXIANGBean.WENJUANBean.SublistBean sublistBean = sublistBeans.get(position);
        holder.cbCheck.setText(sublistBean.getRISK_FACTOR_NAME());
    }

    @Override
    public int getItemCount() {
        if (sublistBeans == null) {
            sublistBeans = new ArrayList<>();
        }
        return sublistBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbCheck;
        ImageView ivWenhao;
        EditText etOther;
        TextView tvWenhaoContent;

        public ViewHolder(View itemView) {
            super(itemView);
            cbCheck = itemView.findViewById(R.id.cb_check);
        }
    }
}
