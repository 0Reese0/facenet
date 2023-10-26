package cn.edu.nbpt.facenet.singin.controller;

import cn.edu.nbpt.facenet.singin.entity.MeetingHistory;
import cn.edu.nbpt.facenet.singin.entity.User;
import cn.edu.nbpt.facenet.singin.service.MeetingHistoryService;
import cn.edu.nbpt.facenet.singin.service.UserService;
import cn.edu.nbpt.facenet.singin.service.exception.PermissionExcep;
import cn.edu.nbpt.facenet.singin.service.exception.TokenErrorException;
import cn.edu.nbpt.facenet.singin.utils.JsonResult;
import cn.edu.nbpt.facenet.singin.utils.JwtUtil;
import cn.edu.nbpt.facenet.singin.utils.MultipartFileMutualFileUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.checkerframework.checker.guieffect.qual.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = {"用户接口"})
@RestController
@RequestMapping("api/v1/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingHistoryService historyService;

    @ApiOperation("注册")
    @PostMapping("reg")
    public JsonResult<Void> reg(@ApiParam(hidden = true) @RequestParam MultipartFile avatar, @RequestParam Map<String, Object> params) {
        User user = new User();
        String phone = String.valueOf(params.get("phone"));
        user.setPhone(phone);
        user.setPassword(String.valueOf(params.get("password")));
        user.setUsername(String.valueOf(params.get("username")));
        user.setAddress(String.valueOf(params.get("address")));
        user.setAge(Integer.valueOf(String.valueOf(params.get("age"))));
        user.setSex(Integer.valueOf(String.valueOf(params.get("sex"))));
        userService.reg(user, avatar);
        return new JsonResult<Void>(200);
    }

    @ApiOperation("登录")
    @PostMapping("login")
    public JsonResult<User> login(@ApiParam(hidden = true) @RequestParam Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        User data = userService.login(phone, password);
        String token = JwtUtil.createToken(data);
        return new JsonResult<User>(200, token, data);
    }

    @ApiOperation("删除用户信息")
    @PostMapping("deleteByPhone")
    public JsonResult<Void> deleteByPhone(@ApiParam(hidden = true) @RequestParam String phone) {
        userService.deleteByPhone(phone);
        return new JsonResult<Void>(200);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("updateByPhone")
    public JsonResult<Void> updateByPhone(MultipartFile avatar, @RequestParam Map<String, Object> params, @RequestHeader String token) {
        Claims claims = JwtUtil.checkTOKEN(token);
        User user = new User();
        if (params.get("password") != null) {
            user.setPassword((String) params.get("password"));
        }
        if (params.get("username") != null) {
            user.setUsername((String) params.get("username"));
        }
        if (params.get("address") != null) {
            user.setAddress((String) params.get("address"));
        }
        if (params.get("age") != null) {
            user.setAge(Integer.valueOf((String) params.get("age")));
        }
        if (params.get("sex") != null) {
            user.setSex(Integer.valueOf((String) params.get("sex")));
        }
        if (params.get("phone") != null) {
            user.setPhone(String.valueOf(claims.get("phone")));
        }
        userService.updateByPhone(user, avatar);
        return new JsonResult<Void>(200);
    }

    @PostMapping("updateUserInfo")
    public JsonResult<Void> updateByPhone(@RequestBody User user, @RequestHeader String token) {
        Claims claims = JwtUtil.checkTOKEN(token);
        user.setPhone((String) claims.get("Phone"));
        userService.updateUserInfo(user);
        return new JsonResult<Void>(200);
    }

    @PostMapping("updatePassword")
    public JsonResult<Void> updatePassword(@RequestBody User user, @RequestHeader String token) {
        Claims claims = JwtUtil.checkTOKEN(token);
        user.setPhone((String) claims.get("Phone"));
        userService.updatePassword(user);
        return new JsonResult<Void>(200);
    }
    @PostMapping("updateAvatar")
    public JsonResult<Void> updateAvatar(@RequestParam MultipartFile avatar, @RequestHeader String token) {
        Claims claims = JwtUtil.checkTOKEN(token);

        userService.updateAvatar((String) claims.get("Phone"),avatar,(String) claims.get("avatar"));
        return new JsonResult<Void>(200);
    }

    @ApiOperation("用户人脸验证")
    @PostMapping("checkFace")
    public JsonResult<Void> checkFace(@RequestHeader String token, @RequestParam MultipartFile avatars, Integer mId) {
        Claims claims = JwtUtil.checkTOKEN(token);
        Integer UID = (Integer) claims.get("UID");
        Boolean flag = userService.checkFace(avatars, mId, UID);
        if (flag) {
            MeetingHistory history = new MeetingHistory();
            history.setuId(UID);
            history.setmId(mId);
            history.setRegTime(new Date().getTime());
            history.setMod(1);
            historyService.updateRegTimemIduId(history);
        }
        return new JsonResult<>(200);
    }

    @ApiOperation("用户手动签到")
    @PostMapping("singIn")
    public JsonResult<Void> singIn(@RequestHeader String token, Integer mId) {
        Claims claims = JwtUtil.checkTOKEN(token);
        Integer UID = (Integer) claims.get("UID");
        MeetingHistory history = new MeetingHistory();
        history.setuId(UID);
        history.setmId(mId);
        history.setRegTime(new Date().getTime());
        history.setMod(0);
        historyService.updateRegTimemIduId(history);
        return new JsonResult<>(200);
    }


    @ApiOperation("获取用户列表")
    @GetMapping("findUserList")
    public JsonResult<List<User>> findUserList() {
        List<User> data = userService.findUserList();
        return new JsonResult<List<User>>(200, data);
    }


    @ApiOperation("获取用户全部信息")
    @GetMapping("findAll")
    public JsonResult<List<User>> findAll(@RequestHeader String token) {
        Claims claims = JwtUtil.checkTOKEN(token);
        Integer admin = (Integer) claims.get("Admin");
        if (admin!=1){
            throw new PermissionExcep();
        }
        List<User> data = userService.findAll();
        return new JsonResult<List<User>>(200, data);
    }

    @PostMapping("activeUser")
    public JsonResult<Void> activeUser(@RequestHeader String token,@RequestParam String phone){
        Claims claims = JwtUtil.checkTOKEN(token);
        Integer admin = (Integer) claims.get("Admin");
        if (admin!=1){
            throw new PermissionExcep();
        }
        userService.activeUser(phone);
        return new JsonResult<>(200);
    }

    @ApiOperation("token失效返回异常")
    @GetMapping("result")
    public void result(@RequestHeader String token) {
        throw new TokenErrorException();
    }
}
