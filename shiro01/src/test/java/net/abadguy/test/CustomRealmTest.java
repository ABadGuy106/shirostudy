package net.abadguy.test;

import net.abadguy.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 测试自定义realm
 */
public class CustomRealmTest {

    @Test
    public void testAuthentication(){
        CustomRealm customRealm=new CustomRealm();
        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken token = token=new UsernamePasswordToken("user","123");
        try {
            subject.login(token);
        }catch (UnknownAccountException e1){
            System.out.println("用户名不正确");
        }catch (IncorrectCredentialsException e2){
            System.out.println("密码错误");
        }

        System.out.println("登录是否成功："+ subject.isAuthenticated());
        //退出登陆
        subject.logout();


    }
}
