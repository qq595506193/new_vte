package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;

import java.util.ArrayList;
import java.util.List;

import bean.MessageBean;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<MessageBean.ServerParamsBean.ListBean> listBeans;

    public MsgAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListBeans(List<MessageBean.ServerParamsBean.ListBean> listBeans) {
        if (listBeans != null) {
            this.listBeans = listBeans;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.im_message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        MessageBean.ServerParamsBean.ListBean listBean = listBeans.get(position);
        holder.tv_message_content.setText(listBean.getMESSAGE_CONTENT());
        holder.tv_message_time.setText(listBean.getSEND_TIME());
    }

    @Override
    public int getItemCount() {
        if (listBeans == null) {
            listBeans = new ArrayList<>();
        }
        return listBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_message_content;
        TextView tv_message_time;
        ImageView iv_dian;
        TextView tv_system_message;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_system_message = itemView.findViewById(R.id.tv_system_message);
            iv_dian = itemView.findViewById(R.id.iv_dian);
            tv_message_time = itemView.findViewById(R.id.tv_message_time);
            tv_message_content = itemView.findViewById(R.id.tv_message_content);
        }
    }
}
