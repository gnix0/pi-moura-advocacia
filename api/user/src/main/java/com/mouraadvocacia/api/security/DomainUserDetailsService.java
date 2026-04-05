package com.mouraadvocacia.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mouraadvocacia.api.domain.port.inbound.FindUserByEmailUseCase;

@Service
public class DomainUserDetailsService implements UserDetailsService {

    private final FindUserByEmailUseCase findUserByEmailUseCase;

    public DomainUserDetailsService(FindUserByEmailUseCase findUserByEmailUseCase) {
        this.findUserByEmailUseCase = findUserByEmailUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByEmailUseCase.findByEmail(username)
                .map(AuthenticatedUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado."));
    }
}
