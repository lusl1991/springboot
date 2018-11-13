package com.softel.springboot.service;

import com.softel.springboot.model.Email;

public interface EmailService {

	/**
     * 纯文本
     * @param emailEntity
     * @throws Exception
     */
    public void send(Email email) throws Exception;

    /**
     * 富文本
     * @param emailEntity
     * @throws Exception
     */
    public void sendHtml(Email email) throws Exception;

    /**
     * 模板发送 freemarker
     * @param emailEntity
     * @throws Exception
     */
    public void sendFreemarker(Email email) throws Exception;

    /**
     * 模板发送 thymeleaf
     * @param emailEntity
     * @throws Exception
     */
    public void sendThymeleaf(Email email) throws Exception;
    
    /**
     * 队列发送
     * @param mail
     * @throws Exception
     */
	public void sendQueue(Email mail) throws Exception;

}