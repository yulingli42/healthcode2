package com.healthcode.service;

import com.healthcode.domain.Teacher;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.vo.TeacherDailyCardStatistic;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Part;

/**
 * @author zhenghong
 */
public interface ITeacherService {
    /**
     * 教师登录处理
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回教师信息，否则返回 null
     */
    Teacher login(String username, String password);

    /**
     * 获取该教师今日是否已经打卡
     *
     * @param teacher 需要获取打卡信息的教师
     * @return 是否已经打卡
     */
    Boolean getDailyCardStatus(Teacher teacher);

    /**
     * 教师提交打卡信息
     *
     * @param teacher 需要提交打卡信息的教师
     * @param card    今日打卡信息
     */
    void submitDailyCard(Teacher teacher, CurrentDailyCard card);

    /**
     * 根据学院编号获取到整个学院教师的打卡状态
     *
     * @param collegeId 学院编号
     * @return 整个学院教师的打卡状态
     */
    TeacherDailyCardStatistic getTeacherStatistic(@Nullable Integer collegeId);

    /**
     * 添加新的教师
     *
     * @param teacherId 教师工号
     * @param name      姓名
     * @param idCard    身份证号
     * @param collegeId 学院编号
     */
    void insertTeacher(String teacherId, String name, String idCard, Integer collegeId);

    /**
     * 展示学生健康码
     *
     * @param teacher 教师
     * @return 健康码byte数组
     */
    byte[] showHealthCode(Teacher teacher);

    /**
     * 从 excel 表格中添加教师
     *
     * @param filePart 上传的文件
     */
    void addTeacherFromExcel(Part filePart);
}
