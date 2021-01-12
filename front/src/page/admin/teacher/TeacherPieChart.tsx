import React from "react";
import {TeacherDailyCardStatistic} from "../../../entity/TeacherDailyCardStatistic";
import {Pie} from "@ant-design/charts";
import {PieConfig} from "@ant-design/charts/es/pie";

interface TeacherPieChartProps {
    data?: TeacherDailyCardStatistic
}

const TeacherPieChart: React.FC<TeacherPieChartProps> = ({data}) => {
    if (data == null) {
        return <></>
    }
    const notSubmitNumber = data.totalTeacherCount - data.greenCodeTeacherCount -
        data.yellowCodeTeacherCount - data.redCodeTeacherCount

    const config: PieConfig = {
        autoFit: false,
        data: [
            {type: '绿码', value: data?.greenCodeTeacherCount},
            {type: '黄码', value: data?.yellowCodeTeacherCount},
            {type: '红码', value: data?.redCodeTeacherCount},
            {type: '未填报', value: notSubmitNumber}
        ],
        angleField: 'value',
        colorField: 'type',
        width: 200,
        height: 200,
        color: ({type}) => {
            switch (type) {
                case "绿码":
                    return "green";
                case "黄码":
                    return "yellow";
                case "红码":
                    return "red"
                default:
                    return "grey"
            }
        }
    };

    return <Pie style={{paddingLeft: "41%", display: "block"}} {...config} />
}

export default TeacherPieChart;