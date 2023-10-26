package cn.edu.nbpt.facenet.singin.service;


import cn.edu.nbpt.facenet.singin.entity.MeetingHistory;

import java.util.List;

public interface MeetingHistoryService {
    void insert(MeetingHistory meetingHistory);

    void updateRegTimemIduId(MeetingHistory meetingHistory);

    void deleteBymIduId(Integer mId,Integer uId);
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
