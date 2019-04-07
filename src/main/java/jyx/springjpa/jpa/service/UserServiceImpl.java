package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.User;
import jyx.springjpa.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author jyx
 * @date 2019/3/20
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {

        //模糊查询
        name = "%" + name + "%";
        Page<User> users = userRepository.findByNameLike(name, pageable);

        return users;
    }

    @Override
    public List<User> listUsersByUsernames(Collection<String> usernames) {
        return userRepository.findByUsernameIn(usernames);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
