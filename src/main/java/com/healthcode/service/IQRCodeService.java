package com.healthcode.service;

import com.healthcode.domain.HealthCodeType;
import com.healthcode.dto.CurrentDailyCard;

import java.awt.image.BufferedImage;

/**
 * @author zhenghong
 */
public interface IQRCodeService {
    /**
     * 二维码生成和解析处理
     *
     * @param healthCodeType 健康码类型
     * @param content 二维码内容
     * @return 生成二维码成功返回图像，否则返回null
     */
    BufferedImage createQRCode(HealthCodeType healthCodeType, String content, String logoPath, boolean needCompress) throws Exception;

    /**
     * 健康码类型判断
     *
     * @param currentDailyCard 正在提交的每日一报信息
     * @return HealthCodeType 健康码类型
     */
    HealthCodeType judgeQRCodeType(CurrentDailyCard currentDailyCard);
}
