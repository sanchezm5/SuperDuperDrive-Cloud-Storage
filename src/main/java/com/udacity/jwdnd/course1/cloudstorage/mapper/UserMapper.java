package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * MyBatis mapper classes (@Mapper-annotated interfaces to access tables) to handle interacting with the database.
 */

@Mapper
@Component
public interface UserMapper {

    @Results({
            @Result(property = "userId", column = "userid"),
            @Result(property = "username", column = "username"),
            @Result(property = "salt", column = "salt"),
            @Result(property = "password", column = "password"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname")
    })
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Update("UPDATE USERS SET username = #{username}, salt = #{salt}, password = #{password}, firstname = #{firstname}, lastname = #{lastname}) WHERE userId = #{userId}")
    void updateUser(User user);

    @Delete("DELETE FROM USERS WHERE userId = #{userId}")
    void deleteUser(User user);
}
