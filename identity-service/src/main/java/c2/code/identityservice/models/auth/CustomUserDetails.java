package c2.code.identityservice.models.auth;

import c2.code.identityservice.entity.sql.Agent;
import c2.code.identityservice.models.dto.AuthorizeDTO;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;



@Data
@ToString
public class CustomUserDetails implements UserDetails {
    // Trả về đối tượng User
    @Getter
    private final Agent agent;
    private final Collection<? extends GrantedAuthority> authorities;
    private final AuthorizeDTO customAuthorize;







    @Override
    public String getUsername() {
        return agent.getEmail(); // Hoặc bất kỳ thuộc tính nào bạn muốn sử dụng
    }

    @Override
    public String getPassword() {
        return agent.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Hoặc logic của bạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Hoặc logic của bạn
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Hoặc logic của bạn
    }

    @Override
    public boolean isEnabled() {
        return true; // Hoặc logic của bạn
    }
}
