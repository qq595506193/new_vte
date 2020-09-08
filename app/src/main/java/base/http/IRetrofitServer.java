package base.http;

import java.util.HashMap;

import bean.ApproveBean;
import bean.CancellationBean;
import bean.DocAuditBean;
import bean.HistoryAssessBean;
import bean.LoginBean;
import bean.LoginTypeBean;
import bean.MessageBean;
import bean.MessageUpdateBean;
import bean.PatientListBean;
import bean.RefreshPatientBean;
import bean.RiskAssessBean;
import bean.SelectTablesBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IRetrofitServer {

    //登录
    @GET
    Observable<LoginBean> getLogin(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 登录状态监听
    @GET
    Observable<LoginTypeBean> getLoginType(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 患者列表展示
    @GET
    Observable<PatientListBean> getPatientList(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 查询患者状态并提醒
    @GET
    Observable<RefreshPatientBean> getRefreshPatient(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 查询历史记录
    @GET
    Observable<HistoryAssessBean> getHistory(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 查询题目表格列表
    @GET
    Observable<RiskAssessBean> getRisk(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 立即问卷查询
    @GET
    Observable<SelectTablesBean> getRiskTableList(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 消息列表展示
    @GET
    Observable<MessageBean> getMessageList(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 消息状态修改
    @GET
    Observable<MessageUpdateBean> getMessageTypeUpdate(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 查询医生可审核列表
    @GET
    Observable<DocAuditBean> getDocAudit(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 此卷作废
    @GET
    Observable<CancellationBean> getCiJuanZuoFei(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

    // 审核通过
    @GET
    Observable<ApproveBean> getShenHeTongGuo(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

//    // 查询未读消息条数
//    @GET
//    Observable<ResponseBody> doMessageNumGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 查询题目表格列表
//    @GET
//    Observable<RiskAssessmentBean> doRiskTableListGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 历史评估查询
//    @GET
//    Observable<HistoryAssessBean> doHistoryAssessGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 提交表格
//    @GET
//    Observable<CommitBean> doCommitGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 保存表格
//    @GET
//    Observable<SaveCommitBean> doSaveGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 提醒变色
//    @GET
//    Observable<QueryHMBean> doQueryHMGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 取消评估
//    @GET
//    Observable<CancelAssessBean> doCancelAssessGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 评估中监控
//    @GET
//    Observable<EvaluatingBean> doEvaluatingGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 加勾选
//    @GET
//    Observable<CheckRiskBean> doCheckRiskGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 终止评估
//    @GET
//    Observable<AssessCancelBean> doAssessCancelGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 立即问卷查询
//    @GET
//    Observable<NowSelectTablesBean> doNowSelectTablesGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 业务问卷查询
//    @GET
//    Observable<SelectedTablesBean> doSelectedTablesGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 稍后提醒
//    @GET
//    Observable<LaterOnBean> doLaterOnGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);
//
//    // 查看是否可以从消息页面跳转
//    @GET
//    Observable<MessageStratBean> doMessageStartGet(@Url String apiUrl, @QueryMap HashMap<String, Object> params);

}
