package cn.edu.nbpt.facenet.singin.controller;

import cn.edu.nbpt.facenet.singin.service.exception.*;
import cn.edu.nbpt.facenet.singin.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class BaseController {
    @ExceptionHandler(BaseException.class)
    public JsonResult<Void> handlerException(Throwable e){
        JsonResult<Void> result = new JsonResult<Void>();
        if (e instanceof PasswordErrorException){
            result.setCode(601);
            result.setMsg("密码错误");
        }else if(e instanceof UserDuplicateException){
            result.setCode(602);
            result.setMsg("该账户已被注册");
        }else if(e instanceof UserNotFoundException){
            result.setCode(603);
            result.setMsg("用户不存在");
        }else if(e instanceof UserNotAuditException){
            result.setCode(604);
            result.setMsg("用户未激活");
        }else if(e instanceof ServiceException){
            result.setCode(400);
            result.setMsg("服务错误，请联系管理员");
        }else if(e instanceof NullPointerException){
            result.setCode(404);
            result.setMsg("数据为空");
        }else if (e instanceof CheckFaceExcep){
            result.setCode(605);
            result.setMsg("人脸认证失败");
        }else if(e instanceof TokenErrorException){
            result.setCode(606);
            result.setMsg("登录失效");
        }else if (e instanceof PermissionExcep){
            result.setCode(607);
            result.setMsg("权限不足，请登录管理员账户");
        }
        return result;
    }

    @RequestMapping("/")
    public String index(){
        System.out.println("首页");
        return "index.html";
    }
}
