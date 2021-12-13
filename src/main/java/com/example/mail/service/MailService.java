package com.example.mail.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangjie
 * @date 2021/12/8 14:43
 */
@Service
public class MailService {

    public void sendMail1(){
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("用户名@qq.com");
        account.setUser("用户名");
        account.setPass("uokkkppebvaqh");
        MailUtil.send(account, CollUtil.newArrayList("用户名@qq.com"), "测试", "邮件测试", false);

    }

    /**
     * mail.setting设置MailAccount
     */
    public void sendMail2(){
        MailUtil.send(CollUtil.newArrayList("1440879349@qq.com"), "测试", "邮件测试2", false);
    }

    /**
     * 模板方式发送邮件
     */
    public void sendMail3(){
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.FILE));
        Template template = engine.getTemplate("mail_template.html");
        Map<String, Object> map = new HashMap<>(1);
        map.put("mail", "测试3");
        String content = template.render(map);
        MailUtil.send(CollUtil.newArrayList("1440879349@qq.com"), "测试", content, true);
    }
}
