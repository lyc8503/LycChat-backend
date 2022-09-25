package site.lyc8503.chat.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import site.lyc8503.chat.pojo.entity.UserEntity;

import java.util.List;

public interface UserMapper {

    @Select("SELECT * FROM user")
    public List<UserEntity> getUsers();

    @Select("SELECT * FROM user WHERE id = #{id}")
    public UserEntity getUser(int id);

    @Insert("INSERT INTO user (nickname, username, email, password_hash) VALUES (#{nickname}, #{username}, #{email}, #{passwordHash})")
    public void insertUser(UserEntity userEntity);

    @Update("UPDATE user SET nickname = #{nickname}, username = #{username}, email = #{email}, password_hash = #{passwordHash} WHERE id = #{id}")
    public void updateUser(UserEntity userEntity);

    @Delete("DELETE FROM user WHERE id = #{id}")
    public void deleteUser(int id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    public UserEntity getUserByUsername(String username);

    @Delete("DELETE FROM user WHERE username = #{username}")
    public void deleteUserByUsername(String username);
}
