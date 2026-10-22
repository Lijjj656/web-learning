package com.learning.jwtapi;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<Task> findByUserId(@Param("userId") Long userId);
    Task findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
    int insert(Task task);
    int update(Task task);
    int delete(@Param("id") Long id, @Param("userId") Long userId);
    TaskStats statsByUserId(@Param("userId") Long userId);
}
