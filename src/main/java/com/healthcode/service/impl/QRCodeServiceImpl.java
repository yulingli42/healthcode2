package com.healthcode.service.impl;

import com.healthcode.domain.HealthCodeType;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.IQRCodeService;
import com.healthcode.utils.QRCodeUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

/**
 * @author zhenghong
 */
public class QRCodeServiceImpl implements IQRCodeService {
    @Override
    public BufferedImage createQRCode(HealthCodeType healthCodeType, String content, String logoPath, boolean needCompress) throws Exception {
        return QRCodeUtil.encode(healthCodeType, content, logoPath, needCompress);
    }

    @Override
    public HealthCodeType judgeQRCodeType(CurrentDailyCard currentDailyCard) {
        if(currentDailyCard.isTheExposed() || currentDailyCard.isSuspectedCase() ||
                (!Objects.isNull(currentDailyCard.getCurrentSymptoms()) && currentDailyCard.getCurrentSymptoms().length >= 2)){
            return HealthCodeType.RED;
        }else if(currentDailyCard.isHaveBeenToKeyEpidemicAreas() || currentDailyCard.isHaveBeenAbroad() ||
                (!Objects.isNull(currentDailyCard.getCurrentSymptoms()) && !"无异常".equals(currentDailyCard.getCurrentSymptoms()[0]))){
            return HealthCodeType.YELLOW;
        }
        return HealthCodeType.GREEN;
    }

    public byte[] getHealthCodeBytes(HealthCodeType healthCodeType, String content, String logoPath, boolean needCompress) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(QRCodeUtil.encode(healthCodeType, content, logoPath, needCompress), "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
