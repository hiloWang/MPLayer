package com.hhly.lawyer.data.entity;

public class HttpResult<T> {

    /**
     * code : 200
     * message : 成功
     * data : {"token":"b46ec8a75be14d0facbca882540689a7","user":{"createdate":null,"createby":null,"updatedate":null,"updateby":null,"version":null,"userId":"hhly001","email":null,"nickName":"hhly001","realName":null,"registerTime":1466387749000,"mobilePhone":null,"headImg":null,"userType":null,"status":null,"description":null,"cardType":null,"cardNo":null,"sex":null,"birthday":null,"qq":null,"address":null}}
     * error : null
     */

    public int code;
    public String message;

    public T data;
    public Boolean error;

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}
