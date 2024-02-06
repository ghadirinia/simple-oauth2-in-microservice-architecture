/*
package ir.wallet.auth.config;

import ir.wallet.auth.model.AuthUserEntity;
import ir.wallet.auth.repositories.AuthUserRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<AuthUserEntity> userOptional = authUserRepository.findByUsername(username);

        if(userOptional.isEmpty()) {
            return null;
        }
        AuthUserEntity authUserEntity = userOptional.get();
        if(passwordEncoder.matches(CharBuffer.wrap(password),authUserEntity.getPassword())){


            return UsernamePasswordAuthenticationToken.authenticated(username,password, Collections.emptyList());
 }
        return null;


    }

   @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

  */
/*  @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }*//*


}
*/
