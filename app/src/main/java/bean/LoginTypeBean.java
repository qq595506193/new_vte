package bean;

import java.io.Serializable;

public class LoginTypeBean implements Serializable {

    /**
     * code : 99
     * message : 您的账号已在其它设备登录。如果不是您本人操作，请及时修改密码。
     * server_code :
     * server_params :
     */

    private String code;
    private String message;
    private String server_code;
    private String server_params;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServer_code() {
        return server_code;
    }

    public void setServer_code(String server_code) {
        this.server_code = server_code;
    }

    public String getServer_params() {
        return server_params;
    }

    public void setServer_params(String server_params) {
        this.server_params = server_params;
    }
}
