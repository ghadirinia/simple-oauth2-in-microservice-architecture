/*
package ir.wallet.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.wallet.auth.dto.AuthUserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class UserAuthenticationConverter implements AuthenticationConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Authentication convert(HttpServletRequest request) {
        AuthUserDto userDto = null;
        try {
            userDto = MAPPER.readValue(request.getInputStream(), AuthUserDto.class);
        } catch (IOException e){
            return null;
        }
        return UsernamePasswordAuthenticationToken.unauthenticated(userDto.getUsername(),userDto.getPassword());
    }
}
*/
