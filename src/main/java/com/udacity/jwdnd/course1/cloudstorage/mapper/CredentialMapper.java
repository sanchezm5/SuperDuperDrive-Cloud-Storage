package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MyBatis mapper classes (@Mapper-annotated interfaces to access tables) to handle interacting with the database.
 */

@Mapper
@Component
public interface CredentialMapper {

    @Results({
            @Result(property = "credentialId", column = "credentialid"),
            @Result(property = "url", column = "column"),
            @Result(property = "username", column = "username"),
            @Result(property = "key", column = "key"),
            @Result(property = "password",column = "password"),
            @Result(property = "userId", column = "userid")
    })

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET (url, username, key, password, userId) WHERE credentialid = #{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(Integer credentialId);
}

