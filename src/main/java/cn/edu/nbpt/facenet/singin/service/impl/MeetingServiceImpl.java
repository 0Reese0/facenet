package cn.edu.nbpt.facenet.singin.service.impl;

import cn.edu.nbpt.facenet.singin.service.exception.*;
import cn.edu.nbpt.facenet.singin.entity.Meeting;
import cn.edu.nbpt.facenet.singin.mapper.MeetingMapper;
import cn.edu.nbpt.facenet.singin.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private MeetingMapper meetingMapper;

    @Override
    public void insert(Meeting meeting) {

        meeting.setCreateTime(new Date().getTime());
        Integer rows = meetingMapper.insert(meeting);
        if (rows != 1) throw new ServiceException("数据插入异样，请联系管理员");
    }

    @Override
    public void updateBymID(Meeting meeting) {
        Integer rows = meetingMapper.updateBymId(meeting);
        if (rows != 1) throw new ServiceException("数据修改异样，请联系管理员");
    }

    @Override
    public void deleteBymId(Integer mId) {
        Integer rows = meetingMapper.deleteBymId(mId);
        if (rows != 1) throw new ServiceException("数据删除异样，请联系管理员");
    }

    @Override
    public void endMeeting(Integer mid, Integer uid) {
        Integer row = meetingMapper.endMeeting(mid, uid);
        if (row != 1) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Meeting> findAll() {
        List<Meeting> meetings = meetingMapper.findAll();
        return meetings;
    }

    @Override
    public List<Meeting> findSelfAll(Integer uid) {
        return meetingMapper.findSelfAll(uid);
    }

    @Override
    @Scheduled(cron = "*/5 * * * * ?")
    public void updateStateByStartTime() {
        Long now = new Date().getTime();
        meetingMapper.updateStateByStartTime(now);
    }
}
