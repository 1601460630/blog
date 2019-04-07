package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Authority;

import java.util.Optional;

/**
 * @author jyx
 * @date 2019/3/24
 */
public interface AuthorityService {
    /**
     * 根据Id查询Authority
     * @param id
     * @return
     */
    Optional<Authority> getAuthorityById(Long id);
}
