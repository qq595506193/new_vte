package interceptor;

import java.io.IOException;

import app.App;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import utils.SpUtil;

//在请求头里添加token的拦截器处理
public class TokenHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = SpUtil.getString(App.getContext(), "tk", null); //SpUtils是SharedPreferences的工具类，自行实现
        if (token.isEmpty()) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        } else {
            Request originalRequest = chain.request();
            //key的话以后台给的为准，我这边是叫token
            Request updateRequest = originalRequest.newBuilder().header("tk", token).build();
            return chain.proceed(updateRequest);
        }
    }
}