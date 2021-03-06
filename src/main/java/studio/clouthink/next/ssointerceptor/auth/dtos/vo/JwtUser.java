package studio.clouthink.next.ssointerceptor.auth.dtos.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 */
@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private final String id;

    private final String username;

    private final String nickName;

    private final String sex;

    @JsonIgnore
    private final String password;

    private final String email;

    private final String mobile;

    @JsonIgnore
    private final Collection<GrantedAuthority> authorities;

    private final boolean enabled;

    private final Date createdAt;

    @JsonIgnore
    private final Date updateAt;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Collection getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
