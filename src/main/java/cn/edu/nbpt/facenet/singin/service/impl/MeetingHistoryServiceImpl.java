package cn.edu.nbpt.facenet.singin.service.impl;

import cn.edu.nbpt.facenet.singin.service.exception.*;
import cn.edu.nbpt.facenet.singin.entity.MeetingHistory;
import cn.edu.nbpt.facenet.singin.mapper.MeetingHistoryMapper;
import cn.edu.nbpt.facenet.singin.service.MeetingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MeetingHistoryServiceImpl implements MeetingHistoryService {

    @Autowired
    private MeetingHistoryMapper meetingHistoryMapper;


    @Override
    public void insert(MeetingHistory meetingHistory) {
        Integer rows = meetingHistoryMapper.insert(meetingHistory);
        if (rows != 1) throw new ServiceException("数据插入异样，请联系管理员");
    }

    @Override
    public void updateRegTimemIduId(MeetingHistory meetingHistory) {
        Integer rows = meetingHistoryMapper.updateRegTimemIduId(meetingHistory);
        if (rows != 1) throw new ServiceException("数据插入异样，请联系管理员");
    }

    @Override
    public void deleteBymIduId(Integer mId, Integer uId) {
        Integer rows = meetingHistoryMapper.deleteBymIduId(mId, uId);
        if (rows != 1) throw new ServiceException("数据插入异样，请联系管理员");
    }

    @Override
    public void deleteBymId(Integer mId) {
        meetingHistoryMapper.deleteBymId(mId);
    }

    @Override
    public List<MeetingHistory> findAll() {
        return meetingHistoryMapper.findAll();
    }

    @Override
    public List<MeetingHistory> findBymId(Integer mId) {
        return meetingHistoryMapper.findBymId(mId);
    }

    @Override
    public List<MeetingHistory> findByuId(Integer uId) {
        return meetingHistoryMapper.findByuId(uId);
    }

    @Override
    public Integer findCount(Integer mid) {
        return meetingHistoryMapper.findCount(mid);
    }

    @Override
    public Integer findNoSingInNum(Integer mid) {
        return meetingHistoryMapper.findNoSingInNum(mid);
    }

    @Override
    public Integer findFaceSingInNum(Integer mid) {
        return meetingHistoryMapper.findFaceSingInNum(mid);
    }

    @Override
    public Integer findSingInNum(Integer mid) {
        return meetingHistoryMapper.findSingInNum(mid);
    }

    @Override
    public List<MeetingHistory> findJoinUserList(Integer mid) {
        return meetingHistoryMapper.findJoinUserList(mid);
    }
}
