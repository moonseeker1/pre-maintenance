package com.system.admin.bo;


import com.system.admin.model.Admin;
import com.system.admin.model.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户信息封装类
 * Created by macro on 2018/4/26.
 */
public class AdminUserDetails implements UserDetails {
    //后台用户
    private final Admin admin;
    //拥有资源列表
    private final List<Resource> resourceList;

    public AdminUserDetails(Admin umsAdmin,List<Resource> resourceList) {
        this.admin = umsAdmin;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户所拥有的资源
        return resourceList.stream()
                .map(resource ->new SimpleGrantedAuthority(resource.getId()+":"+resource.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return admin.getPasswd();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getState().equals(1);
    }
}
