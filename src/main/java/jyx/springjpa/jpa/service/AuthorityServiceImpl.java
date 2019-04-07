package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Authority;
import jyx.springjpa.jpa.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author jyx
 * @date 2019/3/20
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Optional<Authority> getAuthorityById(Long id) {
        return authorityRepository.findById(id);
    }
}
