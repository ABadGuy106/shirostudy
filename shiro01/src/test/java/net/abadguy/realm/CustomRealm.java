package net.abadguy.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {
    //使用map模拟数据库中的信息
    Map<String,String> userMap=new HashMap<String, String>();
    {
        userMap.put("user","e10adc3949ba59abbe56e057f20f883e");
        super.setName("coutemRole");
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.从主体传过来的认证信息中，获取用户名
        String userName= (String) principalCollection.getPrimaryPrincipal();
        //从数据库中获取角色数据
        Set<String> roles=getRolesByUserName(userName);

        Set<String> permissions=getPermissionsByUserName(userName);

        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets=new HashSet<String>();
        sets.add("user:del");
        sets.add("user:update");
        return sets;
    }

    private Set<String> getRolesByUserName(String userName) {
        Set<String> sets=new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获取用户名
        String userName= (String) authenticationToken.getPrincipal();
        //2.通过用户名到数据库中获取凭证
        String password=getPasswordByUserName(userName);
        if(password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(userName,password,"coutemRole");

        return authenticationInfo;
    }


    /**
     * 模拟数据库查询
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }

    //计算MD5加密后的值
    public static void main(String[] args){
        Md5Hash md5Hash=new Md5Hash("123456");
        System.out.println(md5Hash.toString());
    }
}
