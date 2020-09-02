package ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.new_vte_2020_01.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.ElementAdater;
import adapter.HistoryTitleAdapter;
import app.App;
import base.BaseMvpActivity;
import base.BasePresenter;
import bean.HistoryAssessBean;
import bean.LoginBean;
import bean.PatientListBean;
import butterknife.BindView;
import butterknife.OnClick;
import contract.IHistoryContract;
import presenter.HistoryPresenter;
import utils.LoadingDialog;
import utils.SpUtil;

public class HistoryActivity extends BaseMvpActivity<IHistoryContract.IHistoryModel, IHistoryContract.HistoryPresenter> implements IHistoryContract.IHistoryView {

    @BindView(R.id.tv_back)
    TextView tvBack;// 上一页按钮
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;// 页面标题
    @BindView(R.id.tv_doc_name)
    TextView tvDocName;// 医生姓名
    @BindView(R.id.iv_message)
    ImageView ivMessage;// 消息按钮
    @BindView(R.id.iv_select_back)
    ImageView ivSelectBack;// 返回登录页面按钮
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;// 患者姓名
    @BindView(R.id.tv_patient_sex)
    TextView tvPatientSex;// 患者性别
    @BindView(R.id.tv_patient_office)
    TextView tvPatientOffice;// 患者科室
    @BindView(R.id.tv_patient_mark)
    TextView tvPatientMark;// 患者住院号
    @BindView(R.id.tv_zhenduan)
    TextView tvZhenduan;
    @BindView(R.id.cly_02)
    ConstraintLayout cly02;// 表头
    @BindView(R.id.tv_zhenduan_content)
    TextView tvZhenduanContent;// 诊断内容
    @BindView(R.id.rv_table_list)
    RecyclerView rvTableList;// 不同表标题
    @BindView(R.id.tv_table_name)
    TextView tvTableName;// 不同表名
    @BindView(R.id.btn_log)
    Button btnLog;// 评估按钮
    @BindView(R.id.line_chart)
    LineChart chartview;// 统计图
    @BindView(R.id.tv_date)
    TextView tvDate;// 日期
    @BindView(R.id.txt_coding)
    TextView txtCoding;// 报告编码
    @BindView(R.id.txt_time)
    TextView txtTime;// 评估时间
    @BindView(R.id.txt_total)
    TextView txtTotal;// 评估总分
    @BindView(R.id.txt_grade)
    TextView txtGrade;// 风险等级
    @BindView(R.id.txt_show)
    TextView txtShow;// 评估说明
    @BindView(R.id.rv_element)
    RecyclerView rvElement;// 危险因素列表
    @BindView(R.id.tv_txt_element)
    TextView tvTxtElement;
    @BindView(R.id.txt_advise)
    TextView txtAdvise;// 预防处理建议
    @BindView(R.id.txt_nursing)
    TextView txtNursing;// 护理处置建议
    @BindView(R.id.txt_note)
    TextView txtNote;// 患者温馨提示
    @BindView(R.id.txt_other)
    TextView txtOther;// 其他
    @BindView(R.id.cly_visib)
    ConstraintLayout clyVisib;// 有数据
    @BindView(R.id.cly_visib_02)
    ConstraintLayout clyVisib02;// 没数据
    @BindView(R.id.iv_yuan)
    ImageView ivYuan;// 危险等级图标

    private LoadingDialog loadingDialog;// 加载中动画

    private PatientListBean.ServerParamsBean serverParamsBean;// 当前患者信息
    private LoginBean loginBean;// 登录信息
    private HistoryTitleAdapter historyTitleAdapter;
    private ElementAdater elementAdater;

    @Override
    @OnClick({R.id.tv_back, R.id.iv_message, R.id.iv_select_back, R.id.btn_log})
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                if (tvBack != null) {
                    finish();
                }
                break;
            case R.id.iv_message:
                break;
            case R.id.iv_select_back:
                break;
            case R.id.btn_log:
                Intent intent = new Intent(this, SelectTableActivity.class);
                intent.putExtra("patientInfo", serverParamsBean);
                intent.putExtra("loginMsg", loginBean);
                startActivity(intent);
                break;
        }
    }


    @Override
    public int bindLayout() {
        return R.layout.activity_history;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(Intent intent) {
        if (tvBack.getVisibility() == View.GONE) {
            tvBack.setVisibility(View.VISIBLE);
        }
        serverParamsBean = (PatientListBean.ServerParamsBean) intent.getSerializableExtra("patientInfo");
        loginBean = (LoginBean) intent.getSerializableExtra("loginMsg");

        tvMainTitle.setText("历史报告");
        if (loginBean != null) {
            tvDocName.setText(loginBean.getServer_params().getUSER_NAME());
        }
        if (serverParamsBean != null) {
            tvPatientName.setText(serverParamsBean.getPATIENT_NAME());
            tvPatientSex.setText(serverParamsBean.getPATIENT_SEX());
            tvPatientOffice.setText(serverParamsBean.getCARE_UNIT_NAME());
            tvPatientMark.setText(serverParamsBean.getPATIENT_ID());
        }

        // 标题列表
        rvTableList.setLayoutManager(new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false));
        historyTitleAdapter = new HistoryTitleAdapter(this);
        rvTableList.setAdapter(historyTitleAdapter);
        // 危险因素列表
        rvElement.setLayoutManager(new LinearLayoutManager(this));
        elementAdater = new ElementAdater(this);
        rvElement.setAdapter(elementAdater);
    }

    @Override
    protected void initData() {
        super.initData();
        if (serverParamsBean != null) {
            getHistory(serverParamsBean.getPATIENT_ID());
        }
    }

    /**
     * 设置数据
     *
     * @param reportList
     */
    private void setHistoryData(List<HistoryAssessBean.ServerParamsBean.ReportListBean> reportList) {
        if (reportList != null) {
            reportList.get(0).che_color = true;
            historyTitleAdapter.setListBeans(reportList);
        }
        for (HistoryAssessBean.ServerParamsBean.ReportListBean reportListBean : reportList) {
            setReportData(reportListBean);
            setLineView(reportListBean);// 设置散点统计图
        }

    }

    private List<Entry> entries = new ArrayList<>();
    private ArrayList<Integer> colors = new ArrayList<Integer>();

    /**
     * 设置散点统计图
     *
     * @param reportListBean
     */
    private void setLineView(HistoryAssessBean.ServerParamsBean.ReportListBean reportListBean) {
        final List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuanBeans = reportListBean.getWENJUAN();
        List<String> xTime = new ArrayList<>();
        List<String> yLever = new ArrayList<>();
        for (int i = 0; i < wenjuanBeans.size(); i++) {
            xTime.add(wenjuanBeans.get(i).getREPORT_TIME());// 设置x轴时间
        }
        // 设置Y轴危险等级
        if (reportListBean.getFORM_ID() == 1) {
            yLever.clear();
            yLever.add(0, "");
            yLever.add(1, "低危");
            yLever.add(2, "中危");
            yLever.add(3, "高危");
            yLever.add(4, "极高危");
        } else {
            yLever.clear();
            yLever.add(0, "低风险");
            yLever.add(1, "高风险");
        }

        //是否缩放X轴
        chartview.setScaleXEnabled(false);
        //是否缩放Y轴
        chartview.setScaleYEnabled(false);
        //设置支持触控手势
        chartview.setTouchEnabled(true);
        //设置推动
        chartview.setScaleEnabled(false);
        chartview.setDragDecelerationFrictionCoef(0.9f);
        //设置是否可以拖拽
        chartview.setDragEnabled(true);
        //设置一页最大显示个数为6，超出部分就滑动
        float ratio = (float) wenjuanBeans.size() / (float) 6;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        chartview.zoom(ratio, ratio, 0, 0);
        //无数据时显示的文字
        chartview.setNoDataText("暂无数据");

        chartview.getXAxis().setGridColor(getResources().getColor(R.color.color_shenhui_));
        //设置从X轴出来的动画时间
        //mLineChart.animateX(1500);
        //设置XY轴动画
        chartview.animateXY(1500, 1500, Easing.EasingOption.EaseInSine, Easing.EasingOption.EaseInSine);
        //准备好每个点对应的x轴数
        Log.e(TAG, "问卷长度===" + wenjuanBeans.size());
        // 点击事件
        chartview.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            private float valEntry;
            private int iEntry;

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // 获取Entry
                iEntry = (int) e.getX();
                valEntry = e.getY();

                Log.e(TAG, "e.getX() = " + iEntry + "     e.getY() = " + valEntry);
                for (int i = 0; i < wenjuanBeans.size(); i++) {
//                    HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean_chart = wenjuan.get(i);
//                    if (i + 1 == iEntry) {
//                        if (wenjuanBean_chart.getFORM_ID() == form_id) {
//                            initInfo_02(wenjuanBean_chart);
//                        }
//                    }
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

        xTime.add("");
//        for (int i = 0; i < wenjuanBeans.size(); i++) {
//            HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean = wenjuanBeans.get(i);
//            String send_time = wenjuanBean.getREPORT_TIME();
//            String year = send_time.substring(0, 4);// 年
//            String month = send_time.substring(4, 6);// 月
//            String day = send_time.substring(6, 8);// 日
//            String time = send_time.substring(8, 10);// 时
//            String minute = send_time.substring(10, 12);// 分
//            String second = send_time.substring(12, 14);// 秒
//            xTime.add(year + "-" + month + "-" + day + "  " + time + "：" + minute + "：" + second);
//        }
        Log.e(TAG, "X轴的长度===" + xTime.size());
        //设置样式
        YAxis rightAxis = chartview.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis yAxis = chartview.getAxisLeft();
        yAxis.setValueFormatter(new IndexAxisValueFormatter(yLever));
        //设置Y极值，我这里没设置最大值，因为项目需要没有设置最大值
        yAxis.setYOffset(0);
        yAxis.setAxisMinimum(0.5f);
        yAxis.setAxisMaximum((float) yLever.size() + 1);
        yAxis.setDrawGridLines(false);
        //1.设置x轴和y轴的点
        for (int i = 0; i < wenjuanBeans.size(); i++) {
            HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean = wenjuanBeans.get(i);
            List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.SublistBean> sublist = wenjuanBean.getSublist();
            for (HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.SublistBean sublistBean : sublist) {
                Log.e(TAG, "subList长度==" + sublist.size() + "");
                String current_risk_level = sublistBean.getCURRENT_RISK_LEVEL();
                final int s = Integer.parseInt(current_risk_level);
                if (5 == s) {
                    entries.add(new Entry(i + 1, 1));
                    colors.add(Color.parseColor("#05a558"));
                } else if (6 == s) {
                    entries.add(new Entry(i + 1, 2));
                    colors.add(Color.parseColor("#6cbdfe"));
                } else if (7 == s) {
                    entries.add(new Entry(i + 1, 3));
                    colors.add(Color.parseColor("#fed700"));
                } else if (8 == s) {
                    entries.add(new Entry(i + 1, 4));
                    colors.add(Color.parseColor("#ff7e00"));
                } else if (9 == s) {
                    entries.add(new Entry(i + 1, 5));
                    colors.add(Color.parseColor("#ff4e6b"));
                } else if (21 == s) {
                    entries.add(new Entry(i + 1, 1));
                    colors.add(Color.parseColor("#05a558"));
                } else if (22 == s) {
                    entries.add(new Entry(i + 1, 3));
                    colors.add(Color.parseColor("#ff4e6b"));
                }
            }
        }
        initX(xTime, wenjuanBeans);
    }

    /**
     * 配置X轴
     *
     * @param xTime
     * @param wenjuanBeans
     */
    private void initX(List<String> xTime, List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean> wenjuanBeans) {
        //隐藏描述
        Description description = new Description();
        description.setEnabled(false);
        chartview.setDescription(description);
        //X轴
        XAxis xAxis = chartview.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xTime));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        //设置X轴高度
        xAxis.setAxisLineWidth(1);
        xAxis.setGranularity(1f);
        //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
        xAxis.setLabelCount(xTime.size() + 1, false);
        // 标签倾斜
        //xAxis.setLabelRotationAngle(45);
        // 设置x轴数据偏移量
        xAxis.setXOffset(0);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0.5f);
        xAxis.setAxisMaximum((float) xTime.size() + 1);
        //设置字体大小10sp
        xAxis.setTextSize(9f);
        // 设置垂直线
        xAxis.setDrawGridLines(false);
        setDiscounting(entries);
    }

    public void setDiscounting(List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "");
        // 设置数据内容的样式
        dataSet.setColor(Color.WHITE);                     // 设置数据中线的颜色
        dataSet.setDrawValues(false);                     // 设置是否显示数据点的值
        dataSet.setDrawCircleHole(false);                // 设置数据点是空心还是实心，默认空心
        dataSet.setCircleColors(colors);                // 设置数据点的颜色
        dataSet.setCircleSize(12);                     // 设置数据点大小
        dataSet.setHighlightEnabled(true);            //设置显示十字线，必须显示十字线，否则MarkerView不生效
        // 设置数据点的大小
        dataSet.setHighLightColor(Color.WHITE);
        //chart设置数据
        LineData lineData = new LineData(dataSet);
        //是否绘制线条上的文字
        lineData.setDrawValues(false);
        // x轴执行动画
        //chartview.animateX(2000);
        chartview.setData(lineData);
        chartview.invalidate(); // refresh
    }


    /**
     * 设置报告数据
     *
     * @param reportListBean
     */
    private void setReportData(HistoryAssessBean.ServerParamsBean.ReportListBean reportListBean) {
        for (HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean wenjuanBean : reportListBean.getWENJUAN()) {
            if (wenjuanBean.getSublist().size() != 0 && wenjuanBean.getSublist() != null) {
                for (HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.SublistBean sublistBean : wenjuanBean.getSublist()) {
                    txtCoding.setText(sublistBean.getREPORT_CODE());
                    txtTime.setText(sublistBean.getREPORT_TIME());
                    // 危险等级
                    switch (sublistBean.getCURRENT_RISK_LEVEL()) {
                        case "5":
                            ivYuan.setImageResource(R.mipmap.diwei_yuan);
                            txtGrade.setText(sublistBean.getRISK_NAME());
                            txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelGreenColor));
                            break;
                        case "6":
                            ivYuan.setImageResource(R.mipmap.zhongwei_yuan);
                            txtGrade.setText(sublistBean.getRISK_NAME());
                            txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelBlueColor));

                            break;
                        case "7":
                            ivYuan.setImageResource(R.mipmap.gaowei_yuan);
                            txtGrade.setText(sublistBean.getRISK_NAME());
                            txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelYellowColor));

                            break;
                        case "8":
                            ivYuan.setImageResource(R.mipmap.jigaowei_yuan);
                            txtGrade.setText(sublistBean.getRISK_NAME());
                            txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelOrangeColor));

                            break;
                        case "9":
                            ivYuan.setImageResource(R.mipmap.quezhen_yuan);
                            txtGrade.setText(sublistBean.getRISK_NAME());
                            txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelRedColor));

                            break;
                        case "21":
                            ivYuan.setImageResource(R.mipmap.chuxue_diwei);
                            txtGrade.setText(sublistBean.getRISK_NAME());
                            txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelGreenColor));

                            break;
                        case "22":
                            ivYuan.setImageResource(R.mipmap.chuxue_gaowei);
                            txtGrade.setText(sublistBean.getRISK_NAME());
                            txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelRedColor));

                            break;
                        default:
                            txtGrade.setText("暂无危险等级");
                            txtGrade.setTextColor(this.getResources().getColorStateList(R.color.levelRedColor));
                            break;
                    }
                    ///
                    txtShow.setText(sublistBean.getRISK_DETAIL_DESC());
                }
                // 危险因素
                elementAdater.setWxysBeans(wenjuanBean.getWxys());
                // 预防处理建议
                if (wenjuanBean.getDOCTOR_ADVICE().size() != 0) {
                    List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.DOCTORADVICEBean> doctor_advice = wenjuanBean.getDOCTOR_ADVICE();
                    String s = "";
                    for (int i = 0; i < doctor_advice.size(); i++) {
                        HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.DOCTORADVICEBean doctoradviceBean = doctor_advice.get(i);
                        s += doctoradviceBean.getADVICE_CONTENT() + "\n";
                    }
                    txtAdvise.setText(s);
                } else {
                    txtAdvise.setText("暂无建议");
                }
                // 护理处置建议
                if (wenjuanBean.getNURSE_ADVICE().size() != 0) {
                    List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.NURSEADVICEBean> nurse_advice = wenjuanBean.getNURSE_ADVICE();
                    String s = "";
                    for (int i = 0; i < nurse_advice.size(); i++) {
                        HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.NURSEADVICEBean nurseadviceBean = nurse_advice.get(i);
                        s += nurseadviceBean.getADVICE_CONTENT() + "\n";
                    }
                    txtNursing.setText(s);
                } else {
                    txtNursing.setText("暂无建议");
                }
                // 患者温馨提示
                if (wenjuanBean.getPATIENT_ADVICE().size() != 0) {
                    List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.PATIENTADVICEBean> patient_advice = wenjuanBean.getPATIENT_ADVICE();
                    String s = "";
                    for (int i = 0; i < patient_advice.size(); i++) {
                        HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.PATIENTADVICEBean patientadviceBean = patient_advice.get(i);
                        if (patientadviceBean.getADVICE_CONTENT() != null) {
                            s += patientadviceBean.getADVICE_CONTENT() + "\n";
                        } else {
                            s = "暂无建议";
                        }
                    }
                    txtNote.setText(s);
                } else {
                    txtNote.setText("暂无建议");
                }
                // 其他
                if (wenjuanBean.getELSE_ADVICE().size() != 0) {
                    List<HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.ELSEADVICEBean> else_advice = wenjuanBean.getELSE_ADVICE();
                    String s = "";
                    for (int i = 0; i < else_advice.size(); i++) {
                        HistoryAssessBean.ServerParamsBean.ReportListBean.WENJUANBean.ELSEADVICEBean else_adviceBean = else_advice.get(i);
                        if (else_adviceBean.getADVICE_CONTENT() != null) {
                            s += else_adviceBean.getADVICE_CONTENT() + "\n";
                        } else {
                            s = "暂无建议";
                        }
                    }
                    txtOther.setText(s);
                } else {
                    txtOther.setText("暂无建议");
                }
            }
        }
    }

    /**
     * 查询历史报告
     *
     * @param patient_id
     */
    private void getHistory(String patient_id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("Type", "queryJibingReportLIST");
        params.put("PATIENT_ID", patient_id);
        params.put("tk", SpUtil.getString(App.getContext(), "tk", null));
        params.put("CONFIRM_FLAG", "20");
        presenter.getHistory(params);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void setListener() {
        historyTitleAdapter.setSetTableItem(new HistoryTitleAdapter.SetTableItem() {
            @Override
            public void setOnClickTableItem(int form_id, HistoryAssessBean.ServerParamsBean.ReportListBean reportListBean) {
                setReportData(reportListBean);
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    public BasePresenter initPresenter() {
        return new HistoryPresenter();
    }

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.getDialog(this,
                    "努力加载中",
                    true,
                    null);
        } else if (loadingDialog.isShowing()) {
            loadingDialog.setMessage("努力加载中");
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void failure(Object msg) {
        Log.e(TAG, (String) msg);
    }

    /**
     * 查询历史成功回调
     *
     * @param result
     */
    @Override
    public void onHistorySuccess(Object result) {
        if (result != null) {
            if (result instanceof HistoryAssessBean) {
                if (((HistoryAssessBean) result).getCode().equals("0")) {
                    Log.e(TAG, ((HistoryAssessBean) result).getMessage());
                    if (((HistoryAssessBean) result).getServer_params().getReportList().size() != 0) {// 为0则无历史报告
                        cly02.setVisibility(View.VISIBLE);
                        clyVisib.setVisibility(View.VISIBLE);
                        clyVisib02.setVisibility(View.GONE);
                        setHistoryData(((HistoryAssessBean) result).getServer_params().getReportList());// 设置数据
                    } else {
                        cly02.setVisibility(View.GONE);
                        clyVisib.setVisibility(View.GONE);
                        clyVisib02.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}