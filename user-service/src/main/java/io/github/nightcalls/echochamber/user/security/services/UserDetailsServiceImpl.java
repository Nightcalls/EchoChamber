package io.github.nightcalls.echochamber.user.security.services;

import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.service.UserSearchService;
import io.github.nightcalls.echochamber.user.service.change.UserChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserSearchService userSearchService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // FIXME move this exception to standalone class and create constructor for username
        User user = userSearchService
                .getUserByName(username)
                .orElseThrow(() -> new UserChangeService.NoSuchUserException(0));
        return UserDetailsImpl.build(user);
    }
}
