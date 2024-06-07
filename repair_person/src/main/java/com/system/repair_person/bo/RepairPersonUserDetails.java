package com.system.repair_person.bo;

import com.system.repair_person.model.RepairPerson;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


public class RepairPersonUserDetails implements UserDetails {
    private final RepairPerson repairPerson;

    public RepairPersonUserDetails(RepairPerson repairPerson) {
        this.repairPerson = repairPerson;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("TEST"));
    }

    @Override
    public String getPassword() {
        return repairPerson.getPasswd();
    }

    @Override
    public String getUsername() {
        return repairPerson.getUsername();
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
    public RepairPerson getRepairPerson() {
        return repairPerson;
    }


}
