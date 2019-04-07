package jyx.springjpa.jpa.repository;

import jyx.springjpa.jpa.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jyx
 * @date 2019/3/20
 */
public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
