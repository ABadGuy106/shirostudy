package net.abadguy.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm realm=new SimpleAccountRealm();

    @Before
    public void addUser(){
        realm.addAccount("tom","1234");
    }

    @Test
    public void testAuthentication(){
        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken token = null;
        try {
             token=new UsernamePasswordToken("tom","1234");
        }catch (UnknownAccountException e1){
            System.out.println("用户名不正确");
        }catch (IncorrectCredentialsException e2){
            System.out.println("密码错误");
        }
        subject.login(token);

        System.out.println("登录是否成功："+ subject.isAuthenticated());
       //退出登陆
        subject.logout();


    }
}
