package cn.edu.nbpt.facenet.singin.controller;

import cn.edu.nbpt.facenet.singin.entity.Meeting;
import cn.edu.nbpt.facenet.singin.service.MeetingService;
import cn.edu.nbpt.facenet.singin.service.UserService;
import cn.edu.nbpt.facenet.singin.utils.JsonResult;
import cn.edu.nbpt.facenet.singin.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = {"会议接口"})
@RestController
@RequestMapping("api/v1/meeting")
public class MeetingController extends BaseController{

    @Autowired
    private MeetingService meetingService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "title",value ="会议标题",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "startTime",value ="会议开始时间",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "endTime",value ="会议结束时间",dataType ="String")
    })
    @ApiOperation("插入会议")
    @PostMapping("insert")
    public JsonResult<Void> insert(@ApiParam(hidden = true) @RequestParam Map<String,String> params, @RequestHeader String token){
        Meeting meeting=new Meeting();
        meeting.setTitle(params.get("title"));
        meeting.setStartTime(Long.valueOf(params.get("startTime")));
        meeting.setEndTime(Long.valueOf(params.get("endTime")));
        Claims claims= JwtUtil.checkTOKEN(token);
        String UID=String.valueOf(claims.get("UID"));
        meeting.setCreateUser(UID);
        // 根据会议开始结束时间设置会议状态
        meeting.setState(0);
        if (Long.valueOf(params.get("startTime"))<new Date().getTime()){
            meeting.setState(1);
        }
        if (Long.valueOf(params.get("endTime"))<new Date().getTime()){
            meeting.setState(2);
        }
        meetingService.insert(meeting);
        return new JsonResult<Void>(200);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "mId",value ="会议ID",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "title",value ="会议标题",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "startTime",value ="会议开始时间",dataType ="String"),
            @ApiImplicitParam(paramType = "query",name = "endTime",value ="会议结束时间",dataType ="String")
    })
    @ApiOperation("修改会议")
    @PostMapping("updateBymID")
    public JsonResult<Void> updateBymID(@ApiParam(hidden = true) @RequestParam Map<String,String> params){
        Meeting meeting=new Meeting();
        meeting.setmId(Integer.valueOf(params.get("mId")));
        meeting.setTitle(params.get("title"));
        meeting.setStartTime(Long.valueOf(params.get("startTime")));
        meeting.setEndTime(Long.valueOf(params.get("endTime")));
        meetingService.updateBymID(meeting);
        return new JsonResult<Void>(200);
    }



    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "mId",value ="会议ID",dataType ="String")
    })
    @ApiOperation("删除会议")
    @PostMapping("deleteBymId")
    public JsonResult<Void> deleteBymId(@ApiParam(hidden = true) @RequestParam Map<String,String> params){
        meetingService.deleteBymId(Integer.valueOf(params.get("mId")));
        return new JsonResult<Void>(200);
    }

    @ApiOperation("结束会议")
    @PostMapping("endMeeting")
    public JsonResult<Void> endMeeting(@RequestHeader String token,@RequestParam Integer mId){
        Claims claims = JwtUtil.checkTOKEN(token);
        meetingService.endMeeting(mId,(Integer) claims.get("UID"));
        return new JsonResult<Void>(200);
    }

    @ApiOperation("查询全部会议")
    @GetMapping("findAll")
    public JsonResult<List<Meeting>> findAll(){
        List<Meeting> data=meetingService.findAll();
        return new JsonResult<>(200,data);
    }

    @ApiOperation("查询我的会议")
    @GetMapping("findSelfAll")
    public JsonResult<List<Meeting>> findSelfAll(@RequestHeader String token){
        Claims claims = JwtUtil.checkTOKEN(token);
        List<Meeting> data=meetingService.findSelfAll((Integer) claims.get("UID"));
        return new JsonResult<>(200,data);
    }
}
