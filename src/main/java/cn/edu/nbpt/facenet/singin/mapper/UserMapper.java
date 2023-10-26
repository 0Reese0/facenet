package cn.edu.nbpt.facenet.singin.mapper;

import cn.edu.nbpt.facenet.singin.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    Integer insert(User user);

    User findByPhone(String phone);

    Integer deleteByPhone(String phone);

    Integer updateByPhone(User user);

    List<User> findUserList();

    List<User> findAll();

    Integer selectUId(String phone);

    Integer activeUser(String phone);

    String checkFace(Integer uId);

}
