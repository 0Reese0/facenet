package cn.edu.nbpt.facenet.service;

import java.io.File;

public interface MilvusService {
    /**
     * 增加milvus数据库数据
     * @param id 存入数据库的id，id为MySql中对应用户的id
     * @param file 存入数据库的人脸图片
     */
    void milvusAdd(Integer id, File file);

    /**
     * 删除milvus数据库数据
     * @param id 指定要删除的id
     */
    void milvusDelete(Integer id);

    /**
     * 进行人脸搜索对比
     * @param file 要进行对比的人脸图片
     * @return
     */
    int milvusSelect(File file);

    /**
     * 可直接创建表
     */
    void createCollection();

    /**
     * 清空数据库
     */
    void clear();


}
