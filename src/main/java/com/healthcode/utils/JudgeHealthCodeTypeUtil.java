package com.healthcode.utils;

import com.healthcode.domain.HealthCodeType;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JudgeHealthCodeTypeUtil {
    public static HealthCodeType judgeHealthTypeHelper(List<HealthCodeType> healthCodeTypeList){
        //获取以往健康码信息
        //没有过去的记录则作为过去健康用户看待，有记录则继续判断过去是否有红黄码
        int redCycle = -1;
        int yellowCycle = -1;
        HealthCodeType healthCodeType = HealthCodeType.GREEN;

        for (HealthCodeType type : healthCodeTypeList){
            //红码打卡七天转黄码，黄码打卡七天转绿码
            switch (type){
                case RED:{
                    //出现红码，中断当前任何周期，重启红码恢复周期
                    healthCodeType = HealthCodeType.RED;
                    yellowCycle = -1;
                    redCycle = 0;
                }
                case YELLOW:{
                    if(healthCodeType != HealthCodeType.RED){
                        //不处于红码周期，启动黄码周期
                        healthCodeType = HealthCodeType.YELLOW;
                        yellowCycle = 0;
                    }else {
                        //出现异常打卡
                        //中断红码转黄码周期，重启红码恢复周期
                        redCycle = 0;
                    }
                }
                case GREEN:{
                    if(healthCodeType == HealthCodeType.RED){
                        //处于红码周期
                        if (++redCycle == 7){
                            healthCodeType = HealthCodeType.YELLOW;
                            redCycle = -1;
                            yellowCycle = 0;
                        }
                    }else if(healthCodeType == HealthCodeType.YELLOW){
                        //处于黄码周期
                        if (++yellowCycle == 7){
                            healthCodeType = HealthCodeType.GREEN;
                            yellowCycle = -1;
                        }
                    }
                }
            }
        }
        return healthCodeType;
    }

    @NotNull
    public static List<HealthCodeType> getHealthCodeTypes(String teacherId, PreparedStatement statement) throws SQLException {
        statement.setString(1,teacherId);
        List<HealthCodeType> healthCodeTypeList = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                healthCodeTypeList.add(HealthCodeType.of(resultSet.getString("result")));
            }
        }
        return healthCodeTypeList;
    }
}
