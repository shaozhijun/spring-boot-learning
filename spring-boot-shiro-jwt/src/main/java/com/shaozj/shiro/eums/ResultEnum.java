package com.shaozj.shiro.eums;

public enum ResultEnum {
	
	/**
	 * �ɹ�
	 */
	SUCCESS(200),
	
	/**
	 * ʧ��
	 */
    FAIL(400),
    
    /**
     * δ��֤��ǩ������
     */
    UNAUTHORIZED(401),
    
    /**
     * �ӿڲ�����
     */
    NOT_FOUND(404),
    
    /**
     * �������ڲ�����
     */
    SERVER_ERROR(500);

    public int code;

    ResultEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
