package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

/**
 * MyBatis mapper classes (@Mapper-annotated interfaces to access tables) to handle interacting with the database.
 */

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE username = #{username}")
    File getFile(String username);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES(#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Update("UPDATE FILES SET (fileName, contentType, fileSize, userId, fileData) WHERE fileId = #{fileid}")
    void updateFile(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileid}")
    void deleteFile(Integer fileId);
}
