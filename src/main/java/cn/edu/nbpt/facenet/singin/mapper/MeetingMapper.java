package cn.edu.nbpt.facenet.singin.mapper;

import cn.edu.nbpt.facenet.singin.entity.Meeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface MeetingMapper {

    Integer insert(Meeting meeting);

    Integer updateBymId(Meeting meeting);

    Integer deleteBymId(Integer mId);


    Integer endMeeting(@Param("mid") Integer mid,@Param("uid") Integer uid);

    Meeting findbymId(Integer mId);

    List<Meeting> findAll();
    List<Meeting> findSelfAll(Integer uid);


    Integer updateStateByStartTime(Long now);

//    Integer updateStateByEnd();

}
