package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author jyx
 * @date 2019/3/20
 */
public interface UserService {

    /**
     * 新增、编辑、保存用户
     *
     * @param user
     * @return
     */
    User saveOrUpdateUser(User user);

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    User registerUser(User user);

    /**
     * 删除用户
     *
     * @param id
     */
    void removeUser(Long id);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    Optional<User> getUserById(Long id);

    /**
     * 根据用户名进行分页模糊查询
     *
     * @param name
     * @param pageable
     * @return
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);

    /**
     * 根据用户名的集合，查询用户详细的信息列表
     *
     * @param username
     * @return
     */
    List<User> listUsersByUsernames(Collection<String> usernames);

}
