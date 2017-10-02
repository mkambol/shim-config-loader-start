package com.epam.spring;

import com.epam.kerberos.HadoopKerberosUtil;
import com.epam.spring.authenticate.impl.BaseConfigLoadAuthentication;
import com.epam.spring.component.SpringComponent;
import com.epam.spring.component.SpringComponent2;
import com.epam.spring.component.SpringComponent3;
import com.epam.spring.component.SpringComponent4;
import com.epam.spring.config.HttpCredentials;
import com.epam.spring.config.Krb5Credentials;
import com.epam.spring.config.SpringAppConfig;
import com.epam.spring.config.SshCredentials;
import com.epam.spring.security.AutheticationManagerImpl;
import com.epam.spring.util.HttpCommonUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Date;

public class SpringTaskApp {
    public static void main(String[] args) throws Exception{
        /*KerberosRestTemplate restTemplate =
                new KerberosRestTemplate(null, "devuser@PENTAHOQA.COM", client);
        restTemplate.getForObject("http://svqxbdcn6hdp26secn1.pentahoqa.com:8080/api/v1/clusters/HDP26Secure/services/HIVE/components", String.class);*/
        Date date = new Date();
        long start = date.getTime();
        System.out.println(date.getTime());

        Krb5Credentials krb5Credentials = new Krb5Credentials("devuser", "password", "PENTAHOQA.COM");
        Thread thread = new Thread(() -> {
            try {
                //Thread.sleep(10000);
                HadoopKerberosUtil.doLogin(krb5Credentials.getUsername(), krb5Credentials.getPassword()).getSubject();
            } catch (LoginException | IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringAppConfig.class);
        date = new Date();
        System.out.println("tt- " + String.valueOf(start - date.getTime()));

        SecurityContextHolder.getContext()
                .setAuthentication(applicationContext.getBean(AutheticationManagerImpl.class)
                        .authenticate(new BaseConfigLoadAuthentication(new HttpCredentials("admin", "admin"),
                                new Krb5Credentials("devuser", "password", "PENTAHOQA.COM"), new SshCredentials())));
        thread.join();

        date = new Date();
        System.out.println(start - date.getTime());
        SpringComponent springComponent = applicationContext.getBean(SpringComponent.class);
        SpringComponent2 springComponent2 = applicationContext.getBean(SpringComponent2.class);
        date = new Date();
        System.out.println(start - date.getTime());
        SpringComponent3 springComponent3 = applicationContext.getBean(SpringComponent3.class);
        SpringComponent4 springComponent4 = applicationContext.getBean(SpringComponent4.class);
        date = new Date();
        System.out.println(start - date.getTime());

        springComponent.sendMessage("haha", true);

        springComponent.sendMessage("haha", true);

        springComponent.sendMessage("haha", true);

        date = new Date();
        System.out.println(start - date.getTime());
    }
}
