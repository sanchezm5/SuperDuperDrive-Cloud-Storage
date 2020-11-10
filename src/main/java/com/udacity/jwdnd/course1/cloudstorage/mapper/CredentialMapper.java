package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

/**
 * MyBatis mapper classes (@Mapper-annotated interfaces to access tables) to handle interacting with the database.
 */

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credential getCredential(String username);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET (url, username, key, password, userId) WHERE username = #{username}")
    void updateCredential(String username);

    @Delete("DELETE FROM CREDENTIALS WHERE username = #{username}")
    void deleteCredential(String username);

}
