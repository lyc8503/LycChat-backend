package site.lyc8503.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import site.lyc8503.chat.pojo.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
