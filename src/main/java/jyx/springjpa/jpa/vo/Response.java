package jyx.springjpa.jpa.vo;

/**
 * @author jyx
 * @date 2019/3/20
 */
public class Response {

    private boolean success;
    private String message;
    private Object body;

    /**
     * 响应处理是否成功
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 响应处理的消息
     * @return
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 响应处理的返回内容
     * @return
     */
    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }
}
