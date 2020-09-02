package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.PatientListBean;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {

    private Context context;

    private List<PatientListBean.ServerParamsBean.LevlistBean> mLevlistBeans;

    private LayoutInflater mLayoutInflater;

    public IconAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置数据
     *
     * @param levlistBeans
     */
    public void setLevlistBeans(List<PatientListBean.ServerParamsBean.LevlistBean> levlistBeans) {
        mLevlistBeans = levlistBeans;
        for (PatientListBean.ServerParamsBean.LevlistBean mLevlistBean : mLevlistBeans) {
            if (mLevlistBean.getCURRENT_RISK_LEVEL().equals("5")) {
                mLevlistBean.setLevelColor(R.mipmap.d);
            } else if (mLevlistBean.getCURRENT_RISK_LEVEL().equals("6")) {
                mLevlistBean.setLevelColor(R.mipmap.z);
            } else if (mLevlistBean.getCURRENT_RISK_LEVEL().equals("7")) {
                mLevlistBean.setLevelColor(R.mipmap.g);
            } else if (mLevlistBean.getCURRENT_RISK_LEVEL().equals("8")) {
                mLevlistBean.setLevelColor(R.mipmap.j);
            } else if (mLevlistBean.getCURRENT_RISK_LEVEL().equals("9")) {
                mLevlistBean.setLevelColor(R.mipmap.q);
            } else if (mLevlistBean.getCURRENT_RISK_LEVEL().equals("21")) {
                mLevlistBean.setLevelColor(R.mipmap.cd);
            } else if (mLevlistBean.getCURRENT_RISK_LEVEL().equals("22")) {
                mLevlistBean.setLevelColor(R.mipmap.cq);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IconAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconAdapter.ViewHolder holder, int position) {
        PatientListBean.ServerParamsBean.LevlistBean levlistBean = mLevlistBeans.get(position);
        holder.iv_icon.setImageResource(levlistBean.getLevelColor());
    }

    @Override
    public int getItemCount() {
        if (mLevlistBeans == null) {
            mLevlistBeans = new ArrayList<>();
        }
        return mLevlistBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
