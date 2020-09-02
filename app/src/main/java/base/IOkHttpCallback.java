package base;

public interface IOkHttpCallback {
    void onSuccess(Object result);

    void onFailed(Object error);
}
