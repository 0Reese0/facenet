package cn.edu.nbpt.facenet.singin.service;


import cn.edu.nbpt.facenet.singin.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    void reg(User user, MultipartFile avatar);
    User login(String phone,String password);
    void deleteByPhone(String phone);
    void updateByPhone(User user,MultipartFile avatar);
    void updateUserInfo(User user);
    void updatePassword(User user);
    void updateAvatar(String phone,MultipartFile file,String avatarPath);
    List<User> findUserList();
    List<User> findAll();
    Boolean checkFace(MultipartFile avatars,Integer mId,int UID);
    Integer selectUId(String phone);
    void activeUser(String phone);

}
