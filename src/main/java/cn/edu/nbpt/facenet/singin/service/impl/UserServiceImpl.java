package cn.edu.nbpt.facenet.singin.service.impl;

import cn.edu.nbpt.facenet.service.MilvusService;
import cn.edu.nbpt.facenet.singin.entity.User;
import cn.edu.nbpt.facenet.singin.mapper.MeetingMapper;
import cn.edu.nbpt.facenet.singin.mapper.UserMapper;
import cn.edu.nbpt.facenet.singin.service.MeetingService;
import cn.edu.nbpt.facenet.singin.service.UserService;
import cn.edu.nbpt.facenet.singin.service.exception.*;
import cn.edu.nbpt.facenet.singin.utils.MultipartFileMutualFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MilvusService milvusService;

    @Override
    public void reg(User user, MultipartFile avatar) {
        String phone = user.getPhone();
        String password = user.getPassword();
        User result = userMapper.findByPhone(phone);
        System.out.println(result);
        if (result != null) throw new UserDuplicateException("此手机号已被注册！");
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(password, salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
        Long now = new Date().getTime();
        user.setChangeTime(now);
        user.setCreateTime(now);
        if (userMapper.findAll().size()==0){
            user.setAdmin(1);
            user.setAudit(1);
        }else {
            user.setAdmin(0);
            user.setAudit(0);
        }

        /**
         * 把得到的MultipartFile类型变量转为file
         */
        File file = MultipartFileMutualFileUtil.multipartFileToFile(avatar);

        try {
            // 获取文件名
            String fileName = avatar.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = phone;
            // 设置文件存储路径
            ApplicationHome h = new ApplicationHome(getClass());
            File jarFile = h.getSource();
            String filePath = jarFile.getParentFile().toString() + "/avatar/";
            String path = filePath + fileName + suffixName;
            File dest = new File(path);
            user.setAvatar("/avatar/" + fileName + suffixName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            avatar.transferTo(dest);// 文件写入
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("数据");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);

        /**
         * 添加到人脸数据库
         */
        if (rows == 1) {
            Integer id = userMapper.selectUId(user.getPhone());
            try {
                milvusService.milvusAdd(id, file);
                System.out.println("添加成功");//添加成功处理(管理员可自定义)
            } catch (Exception e) {
                throw new UserFaceAddException("用户人脸数据库添加失败");//异常处理(管理员可自定义)
            }
        }

        if (rows != 1) throw new ServiceException("数据插入异样，请联系管理员");
    }

    @Override
    public User login(String phone, String password) {
        User result = userMapper.findByPhone(phone);
        if (result == null) throw new UserNotFoundException("用户未注册");
        if (result.getAudit() == 0) throw new UserNotAuditException("用户未激活，请联系管理员");
        String salt = result.getSalt();
        String md5Psaaword = getMd5Password(password, salt);
        if (!result.getPassword().equals(md5Psaaword)) {
            throw new PasswordErrorException("密码输入错误");
        }
        result.setSalt(null);
        result.setPassword(null);
        return result;
    }

    @Override
    public void deleteByPhone(String phone) {
        Integer integer = userMapper.selectUId(phone);//根据电话号码查询用户id（查询id为MySql里面的用户信息的id）
        /**
         * 删除人脸数据库的用户信息
         */
        try {
//            File file = new File("");
//            milvusService.milvusSelect(file);
            milvusService.milvusDelete(integer);//根据用户id删除人脸数据库的用户信息（人脸数据库用户的id与Mysql数据库中用户id一致）
            System.out.println("用户人脸数据库删除成功");//删除成功处理(管理员可自定义)
        } catch (Exception e) {
            throw new UserFaceDeleteException("用户人脸数据库删除失败");//异常处理(管理员可自定义)
        }
        Integer rows = userMapper.deleteByPhone(phone);
        if (rows != 1) throw new ServiceException("数据删除异样，请联系管理员");
    }

    @Override
    public void updateByPhone(User user, MultipartFile avatar) {
        Integer rows = userMapper.updateByPhone(user);//修改用户信息
        if (rows != 1) throw new ServiceException("数据插入异样，请联系管理员");
        if (avatar!=null){
            File file = MultipartFileMutualFileUtil.multipartFileToFile(avatar);//转File格式
            /**
             * 人脸数据库的修改（修改就是先删除在新增，人脸数据库的用户id不会自增）
             */
            try {
                Integer id = userMapper.selectUId(user.getPhone());//根据电话号码查询用户id（查询id为MySql里面的用户信息的id）
                System.out.println("id：" + id);
                System.out.println("删除人脸数据库");

                milvusService.milvusDelete(id);//删除人脸数据库

                int i = milvusService.milvusSelect(file);//中间添加一个查询，为了让修改成功。数据没什么用，就单纯加载，但这条千万不能删

                System.out.println(file.getName());
                System.out.println("添加人脸数据库");
                milvusService.milvusAdd(id, file);//添加人脸数据库
                System.out.println("用户人脸数据库修改成功");
            } catch (Exception e) {
                throw new UserFaceUpdateException("用户人脸数据库修改失败");
            }
        }
    }

    @Override
    public void updateUserInfo(User user) {
        Integer row = userMapper.updateByPhone(user);
        if(row!=1){
            throw new ServiceException();
        }
    }

    @Override
    public void updatePassword(User user) {
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
        Integer row = userMapper.updateByPhone(user);
        if(row!=1){
            throw new ServiceException();
        }
    }

    @Override
    public void updateAvatar(String phone, MultipartFile avatar,String avatarPath) {

        File file = MultipartFileMutualFileUtil.multipartFileToFile(avatar);//转File格式
        /**
         * 人脸数据库的修改（修改就是先删除在新增，人脸数据库的用户id不会自增）
         */
        try {
            Integer id = userMapper.selectUId(phone);//根据电话号码查询用户id（查询id为MySql里面的用户信息的id）
            System.out.println("id：" + id);
            System.out.println("删除人脸数据库");

            milvusService.milvusDelete(id);//删除人脸数据库

            int i = milvusService.milvusSelect(file);//中间添加一个查询，为了让修改成功。数据没什么用，就单纯加载，但这条千万不能删

            System.out.println(file.getName());
            System.out.println("添加人脸数据库");
            milvusService.milvusAdd(id, file);//添加人脸数据库
            System.out.println("用户人脸数据库修改成功");
        } catch (Exception e) {
            throw new UserFaceUpdateException("用户人脸数据库修改失败");
        }

        System.out.println(avatarPath);
        // TODO 图片旧数据清理
        ApplicationHome h = new ApplicationHome(getClass());
        File jarFile = h.getSource();
        String filePath = jarFile.getParentFile().toString();
        File delFile = new File(filePath + avatarPath);
        if (delFile.delete()) {
            System.out.println("旧的人脸图片删除成功");
        } else {
            System.out.println("旧的人脸图片删除失败");
        }

        // TODO 写入文件
        // 获取文件名
        String fileName = avatar.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = phone;
        String path = filePath + "/avatar/" + fileName + suffixName;
        File dest = new File(path);
        User user = new User();
        user.setPhone(phone);
        user.setAvatar("/avatar/" + fileName + suffixName);
        Integer row = userMapper.updateByPhone(user);
        if (row != 1) {
            throw new ServiceException("修改头像服务异常");
        }
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();// 新建文件夹
        }
        try {
            System.out.println("写出文件");
            avatar.transferTo(dest);// 文件写入
        } catch (Exception e) {
            throw new ServiceException("头像文件写入异常");
        }
    }


    @Override
    public List<User> findUserList() {
        List<User> users = userMapper.findUserList();
        return users;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userMapper.findAll();
        return users;
    }

    //定义加密方法
    public String getMd5Password(String password, String salt) {
        for (int i = 1; i <= 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    @Override
    public Boolean checkFace(MultipartFile avatars, Integer mId,int uId) {
        File file = MultipartFileMutualFileUtil.multipartFileToFile(avatars);//进行人脸识别
        int UID = milvusService.milvusSelect(file);//如果查询成功则返回用户id，如果查询失败则返回id为0
        if (UID == 0) {
            throw new CheckFaceExcep("人脸认证失败");
        }
        if (UID != uId){
            throw new CheckFaceExcep("人脸认证失败");
        }
        return true;
    }

    @Override
    public Integer selectUId(String phone) {
        Integer integer = userMapper.selectUId(phone);
        return integer;
    }

    @Override
    public void activeUser(String phone) {
        System.out.println(phone);
        Integer row = userMapper.activeUser(phone);
        if (row!=1) throw new ServiceException();
    }

}
