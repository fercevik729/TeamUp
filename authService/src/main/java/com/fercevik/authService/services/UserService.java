package com.fercevik.authService.services;

import com.fercevik.authService.dto.GoogleOAuth2UserInfo;
import com.fercevik.authService.model.Provider;
import com.fercevik.authService.model.User;
import com.fercevik.authService.dao.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService extends OidcUserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findUserByEmail(username);
        return user.orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser user = super.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setId((String) attributes.get("sub"));
        userInfo.setImageUrl((String) attributes.get("picture"));
        userInfo.setName((String) attributes.get("name"));
        updateGoogleOAuth2User(userInfo);
        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteAccount(User user) {
        userRepository.deleteUserByEmail(user.getEmail());
    }

    private void updateGoogleOAuth2User(GoogleOAuth2UserInfo userInfo) {
        // Updates a user's information in the user database to the new values from the OAuth2 Provider
        User user = userRepository.findUserByEmail(userInfo.getEmail())
                .orElseGet(User::new);
        user.setEmail(userInfo.getEmail());
        user.setImageUrl(userInfo.getImageUrl());
        user.setName(userInfo.getName());
        user.setProvider(Provider.GOOGLE);

        userRepository.save(user);
    }

}
