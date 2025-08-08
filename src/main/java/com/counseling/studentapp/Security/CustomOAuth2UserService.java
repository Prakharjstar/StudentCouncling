package com.counseling.studentapp.Security;

import com.counseling.studentapp.model.User;
import com.counseling.studentapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Get user details from Google
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        // Save user if not present
        if (userRepository.findByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword("GOOGLE_USER"); // dummy password
            user.setRole("ROLE_STUDENT");    // Set student role explicitly
            userRepository.save(user);
        }

        // ✅ Assign ROLE_STUDENT manually
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_STUDENT")),
                attributes,
                "email" // or "sub" based on Google's structure
        );
    }
}
