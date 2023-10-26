package cn.edu.nbpt.facenet.singin.mapper;

import cn.edu.nbpt.facenet.singin.entity.MeetingHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MeetingHistoryMapper {
    Integer insert(MeetingHistory meetingHistory);

    Integer updateRegTimemIduId(MeetingHistory meetingHistory);

    Integer deleteBymIduId(@Param("mId") Integer mId,@Param("uId") Integer uId);

    void deleteBymId(Integer mId);

    List<MeetingHistory> findAll();

    List<MeetingHistory> findBymId(Integer mId);

    List<MeetingHistory> findByuId(Integer uId);

    //总人数
    Integer findCount(Integer mid);
    //没有签到的人数
    Integer findNoSingInNum(Integer mid);
    //人脸签到的人数
    Integer findFaceSingInNum(Integer mid);
    //手动签到的人数
    Integer findSingInNum(Integer mid);

    List<MeetingHistory> findJoinUserList(Integer mid);
}
