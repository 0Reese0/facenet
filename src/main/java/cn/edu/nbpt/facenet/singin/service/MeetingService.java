package cn.edu.nbpt.facenet.singin.service;



import cn.edu.nbpt.facenet.singin.entity.Meeting;

import java.util.List;
import java.util.Map;

public interface MeetingService {
    void insert(Meeting meeting);

    void updateBymID(Meeting meeting);

    void deleteBymId(Integer mId);

    void endMeeting(Integer mid,Integer uid);

    List<Meeting> findAll();

    List<Meeting> findSelfAll(Integer uid);

    void updateStateByStartTime();
}
