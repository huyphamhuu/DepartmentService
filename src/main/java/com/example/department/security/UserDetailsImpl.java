package com.example.department.security;

import com.example.department.models.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Department department;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Bạn có thể tùy chỉnh để trả về danh sách quyền hạn của Department
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // Nếu Department không có mật khẩu, bạn có thể trả về một giá trị cố định hoặc null
        return null;
    }

    @Override
    public String getUsername() {
        return department.getName();
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
        return true;
    }
}
