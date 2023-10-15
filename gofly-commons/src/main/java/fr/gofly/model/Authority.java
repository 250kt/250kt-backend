package fr.gofly.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ADMIN(0),
    BUDDING_PILOT(100),
    EXPERIENCED_PILOT(200),
    ACE_OF_THE_SKIES(300);

    final int authority;

    Authority(int authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
