package com.healthcode.service;

import com.healthcode.domain.Student;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.vo.StudentDailyCardStatistic;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Part;

/**
 * @author zhenghong
 */
public interface IStudentService {
    /**
     * 学生登录处理
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回学生信息，否则返回 null
     */
    Student login(String username, String password);

    /**
     * 学生今日是否已经打卡
     *
     * @param student 学生
     * @return 今日是否已经打卡
     */
    Boolean checkStudentDailyCardToday(Student student);

    /**
     * @param student 学生
     * @param card    当前提交的每日一报
     */
    void submitDailyCard(Student student, CurrentDailyCard card);

    /**
     * 根据学院编号或者专业编号或者班级编号获取到所有学生的打卡状态信息
     * <p>
     * 如果为空则表示不限制该项
     *
     * @param clazzId   班级编号
     * @param majorId   专业编号
     * @param collegeId 学院编号
     * @return 所有学生的打卡状态信息
     */
    StudentDailyCardStatistic getStudentStatistic(
            @Nullable Integer clazzId,
            @Nullable Integer majorId,
            @Nullable Integer collegeId);

    /**
     * 添加新学生
     *
     * @param id      学号
     * @param name    姓名
     * @param classId 班级号
     * @param idCard  身份证号
     */
    void insertStudent(String id, String name, Integer classId, String idCard);

    /**
     * 展示学生健康码
     *
     * @param student 学生
     * @return 健康码byte数组
     */
    byte[] showHealthCode(Student student);

    /**
     * 从 excel 表格中添加学生
     *
     * @param filePart 上传的文件
     */
    void addStudentFromExcel(Part filePart);

    /**
     * 删除学生
     *
     * @param id 学生 id
     */
    void deleteById(String id);

    /**
     * 根据学生学号获取信息
     *
     * @param id 学生学号
     * @return 学生信息
     */
    Student getById(String id);

    /**
     * 更新学生
     *
     * @param id      学号
     * @param classId 班级号
     * @param name    姓名
     * @param idCard  身份证号
     */
    void updateStudent(String id, Integer classId, String name, String idCard);
}
