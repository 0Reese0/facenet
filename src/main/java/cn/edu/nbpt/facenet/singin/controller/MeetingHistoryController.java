package cn.edu.nbpt.facenet.singin.controller;

import cn.edu.nbpt.facenet.singin.entity.MeetingHistory;
import cn.edu.nbpt.facenet.singin.service.MeetingHistoryService;
import cn.edu.nbpt.facenet.singin.utils.JsonResult;
import cn.edu.nbpt.facenet.singin.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = {"会议人员接口"})
@RestController
@RequestMapping("api/v1/meeting_history")
public class MeetingHistoryController extends BaseController {

    @Autowired
    private MeetingHistoryService mettingHistoryService;

    @ApiOperation("插入会议人员")
    @PostMapping("insert")
    public JsonResult<Void> insert(@ApiParam(hidden = true) @RequestParam Integer[] ids, Integer mId) {
        mettingHistoryService.deleteBymId(mId);
        MeetingHistory history = new MeetingHistory();
        history.setmId(mId);
        history.setMod(0);
        for (int i = 0; i < ids.length; i++) {
            history.setuId(ids[i]);
            mettingHistoryService.insert(history);
        }
        return new JsonResult<Void>(200);
    }

    @ApiOperation("会议人员签到")
    @PostMapping("updateRegTimemIduId")
    public JsonResult<Void> updateRegTimemIduId(@RequestHeader String token,@RequestBody MeetingHistory meetingHistory) {
        Claims claims = JwtUtil.checkTOKEN(token);
        meetingHistory.setRegTime((new Date()).getTime());
        meetingHistory.setuId((Integer) claims.get("UID"));
        mettingHistoryService.updateRegTimemIduId(meetingHistory);
        return new JsonResult<Void>(200);
    }

    @ApiOperation("删除会议人员")
    @PostMapping("deleteBymIduId")
    public JsonResult<Void> deleteBymIduId(@ApiParam(hidden = true) @RequestParam Map<String, String> params) {
        Integer uId = Integer.valueOf(params.get("uId"));
        Integer mId = Integer.valueOf(params.get("mId"));
        mettingHistoryService.deleteBymIduId(mId, uId);
        return new JsonResult<Void>(200);
    }

    @ApiOperation("查询全部会议人员")
    @GetMapping("findAll")
    public JsonResult<List<MeetingHistory>> findAll() {
        List<MeetingHistory> data = mettingHistoryService.findAll();
        return new JsonResult<List<MeetingHistory>>(200, data);
    }


    @ApiOperation("通过会议ID查询会议人员")
    @GetMapping("findBymId")
    public JsonResult<List<MeetingHistory>> findBymId(@ApiParam(hidden = true) @RequestParam Integer mId) {
        List<MeetingHistory> data = mettingHistoryService.findBymId(mId);
        return new JsonResult<List<MeetingHistory>>(200, data);
    }

    @ApiOperation("通过用户ID查询会议")
    @GetMapping("findByuId")
    public JsonResult<List<MeetingHistory>> findByuId(@RequestHeader String token) {
        Claims claims = JwtUtil.checkTOKEN(token);
        List<MeetingHistory> data = mettingHistoryService.findByuId((Integer) claims.get("UID"));
        return new JsonResult<List<MeetingHistory>>(200, data);
    }

    @ApiOperation("查询会议总人数")
    @GetMapping("findCount")
    public JsonResult<Integer> findCount(@RequestHeader String token,@RequestParam Integer mId) {
        return new JsonResult<>(200, mettingHistoryService.findCount(mId));
    }

    @ApiOperation("查询未签到的人数")
    @GetMapping("findNoSingInNum")
    public JsonResult<Integer> findNoSingInNum(@RequestHeader String token,@RequestParam Integer mId) {
        return new JsonResult<>(200, mettingHistoryService.findNoSingInNum(mId));
    }

    @ApiOperation("查询人脸方式签到的人数")
    @GetMapping("findFaceSingInNum")
    public JsonResult<Integer> findFaceSingInNum(@RequestHeader String token,@RequestParam Integer mId) {
        return new JsonResult<>(200, mettingHistoryService.findFaceSingInNum(mId));
    }

    @ApiOperation("查询普通签到方式的人数")
    @GetMapping("findSingInNum")
    public JsonResult<Integer> findSingInNum(@RequestHeader String token,@RequestParam Integer mId) {
        return new JsonResult<>(200, mettingHistoryService.findSingInNum(mId));
    }

    @ApiOperation("查询普通签到方式的人数")
    @GetMapping("findJoinUserList")
    public JsonResult<List> findJoinUserList(@RequestHeader String token,@RequestParam Integer mId) {
        return new JsonResult<>(200, mettingHistoryService.findJoinUserList(mId));
    }

}
