package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import app.App;
import bean.PatientListBean;
import bean.RefreshPatientBean;

/**
 * 床位图适配器
 */
public class BedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<PatientListBean.ServerParamsBean> mParamsBeanList;

    private RefreshPatientBean.ServerParamsBean mServerParamsBean;


    private static final int HAVE_BED = 0;// 有人
    private static final int NULL_BED = 1;// 没人
    private List<String> sqnq = new ArrayList<>();

    public BedAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    /**
     * 设置床位图数据
     *
     * @param paramsBeanList
     */
    public void setmParamsBeanList(List<PatientListBean.ServerParamsBean> paramsBeanList) {
        mParamsBeanList = paramsBeanList;
        notifyDataSetChanged();
    }

    public void setmServerParamsBeans(RefreshPatientBean.ServerParamsBean serverParamsBean) {
        mServerParamsBean = serverParamsBean;

        // 遍历对比两组数据中VISIT_SQ_NO如果相等就需要设置提醒，
        for (PatientListBean.ServerParamsBean paramsBean : mParamsBeanList) {
            sqnq.add(paramsBean.getVISIT_SQ_NO());
            for (RefreshPatientBean.ServerParamsBean.LISTBean listBean : serverParamsBean.getLIST()) {
                if (paramsBean.getVISIT_SQ_NO() != null) {
//                    if (sqnq.contains(listBean.getVISIT_SQ_NO())) {
//                        refreshList.setRefreshListener();
//                    }
                    if (paramsBean.getVISIT_SQ_NO().equals(listBean.getVISIT_SQ_NO())) {
                        paramsBean.setType(true);
                        paramsBean.setColor(listBean.getREMINDE_COLOR());
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HAVE_BED) {
            return new HaveBedViewHolder(mLayoutInflater.inflate(R.layout.have_bed_layout, parent, false));
        } else {
            return new NullBedViewHolder(mLayoutInflater.inflate(R.layout.null_bed_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final PatientListBean.ServerParamsBean serverParamsBean = mParamsBeanList.get(position);
        if (getItemViewType(position) == HAVE_BED) {
            HaveBedViewHolder haveBedViewHolder = (HaveBedViewHolder) holder;

            // 1.设置患者基本信息卡
            if (serverParamsBean.getBED_NUMBER() != null) {// 床位号
                haveBedViewHolder.tvPatientBedNumber.setText(serverParamsBean.getBED_NUMBER() + "床");
            } else {
                haveBedViewHolder.tvPatientBedNumber.setText("");
            }
            if (serverParamsBean.getPATIENT_NAME() != null) {// 患者姓名
                haveBedViewHolder.tvPatientName.setText(serverParamsBean.getPATIENT_NAME());
            } else {
                haveBedViewHolder.tvPatientName.setText("");
            }
            if (serverParamsBean.getPATIENT_SEX() != null) {// 患者性别
                if (serverParamsBean.getPATIENT_SEX().equals("M")) {
                    haveBedViewHolder.ivPatientIcon.setImageResource(R.mipmap.nan);
                } else {
                    haveBedViewHolder.ivPatientIcon.setImageResource(R.mipmap.nv);
                }
            }
            if (serverParamsBean.getVISIT_SQ_NO() != null) {// 患者流水号
                haveBedViewHolder.tvPatientLiushui.setText(serverParamsBean.getVISIT_SQ_NO());
            }
            if (serverParamsBean.getBIRTHDAY() != null) {// 患者年龄
                haveBedViewHolder.tvPatientAge.setText("年龄:  " + serverParamsBean.getBIRTHDAY() + "岁");
            }
            if (serverParamsBean.getDOCTOR_NAME() != null) {// 患者主治医生
                haveBedViewHolder.tvPatientYisheng.setText("主治医生:  " + serverParamsBean.getDOCTOR_NAME());
            }
            if (serverParamsBean.getCARE_UNIT_NAME() != null) {// 科室
                haveBedViewHolder.tvPatientKeshi.setText("科室:  " + serverParamsBean.getCARE_UNIT_NAME());
            } else {
                haveBedViewHolder.tvPatientKeshi.setText("科室:  " + "");
            }
            if (serverParamsBean.getJibinlist().size() != 0 && serverParamsBean.getJibinlist() != null) {// 患者描述
                haveBedViewHolder.tvPatientContent.setText(serverParamsBean.getJibinlist().get(0).getDIAGNOSIS_DISEASE_NAME());
            } else {
                haveBedViewHolder.tvPatientContent.setText("暂无就诊信息");
            }

            // 2.判断是否展示评字
            if (serverParamsBean.isType()) {
                if (haveBedViewHolder.tvPing.getVisibility() == View.GONE) {
                    haveBedViewHolder.tvPing.setVisibility(View.VISIBLE);
                }
            } else {
                if (haveBedViewHolder.tvPing.getVisibility() == View.VISIBLE) {
                    haveBedViewHolder.tvPing.setVisibility(View.GONE);
                }
            }

            if (haveBedViewHolder.tvPing != null) {
                if (haveBedViewHolder.tvPing.getVisibility() == View.VISIBLE) {
                    haveBedViewHolder.tvPing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemPingClick.setItemPingClickListener(serverParamsBean);
                        }
                    });
                }
            }

            // 横向展示VTE等图标
            LinearLayoutManager ms = new LinearLayoutManager(App.getContext());
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
            haveBedViewHolder.rvVte.setLayoutManager(ms);
            IconAdapter iconAdapter = new IconAdapter(context);
            haveBedViewHolder.rvVte.setAdapter(iconAdapter);
            iconAdapter.setLevlistBeans(serverParamsBean.getLevlist());

            haveBedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.setItemClickListener(serverParamsBean, position);
                }
            });


        } else {
            // 空床
            NullBedViewHolder nullBedViewHolder = (NullBedViewHolder) holder;
            ((NullBedViewHolder) holder).tvWuBedId.setText(serverParamsBean.getBED_NUMBER() + "床");
        }
    }

    @Override
    public int getItemCount() {
        if (mParamsBeanList == null) {
            mParamsBeanList = new ArrayList<>();
        }
        return mParamsBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        PatientListBean.ServerParamsBean serverParamsBean = mParamsBeanList.get(position);
        if (serverParamsBean.getPATIENT_NAME() != null) {
            return HAVE_BED;
        } else {
            return NULL_BED;
        }
    }

    /**
     * 有人的view
     */
    public static class HaveBedViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvVte;
        TextView tvPing;
        ImageView ivPatientIcon;
        TextView tvPatientName;
        TextView tvPatientBedNumber;
        TextView tvPatientLiushui;
        TextView tvPatientAge;
        TextView tvPatientKeshi;
        TextView tvPatientYisheng;
        TextView tvPatientContent;
        TextView tvPatientType;

        public HaveBedViewHolder(View itemView) {
            super(itemView);
            rvVte = itemView.findViewById(R.id.rv_vte);
            tvPing = itemView.findViewById(R.id.tv_ping);
            ivPatientIcon = itemView.findViewById(R.id.iv_patient_icon);
            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvPatientBedNumber = itemView.findViewById(R.id.tv_patient_bed_number);
            tvPatientLiushui = itemView.findViewById(R.id.tv_patient_liushui);
            tvPatientAge = itemView.findViewById(R.id.tv_patient_age);
            tvPatientKeshi = itemView.findViewById(R.id.tv_patient_keshi);
            tvPatientYisheng = itemView.findViewById(R.id.tv_patient_yisheng);
            tvPatientContent = itemView.findViewById(R.id.tv_patient_content);
            tvPatientType = itemView.findViewById(R.id.tv_patient_type);

        }
    }

    /**
     * 没人的view
     */
    public static class NullBedViewHolder extends RecyclerView.ViewHolder {
        TextView tvWuBedId;

        public NullBedViewHolder(View itemView) {
            super(itemView);
            tvWuBedId = itemView.findViewById(R.id.tv_wu_bed_id);
        }
    }

    private RefreshList refreshList;// 通知activity刷新床位图

    public interface RefreshList {
        void setRefreshListener();
    }

    public void setRefreshList(RefreshList refreshList) {
        this.refreshList = refreshList;
    }

    private ItemClick itemClick;// 条目卡片点击事件

    public interface ItemClick {
        void setItemClickListener(PatientListBean.ServerParamsBean serverParamsBean, int position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    private ItemPingClick itemPingClick;// 点击评字跳转回调

    public interface ItemPingClick {
        void setItemPingClickListener(PatientListBean.ServerParamsBean serverParamsBean);
    }

    public void setItemPingClick(ItemPingClick itemPingClick) {
        this.itemPingClick = itemPingClick;
    }
}
