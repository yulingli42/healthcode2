import {PieConfig} from "@ant-design/charts/es/pie";
import {Pie} from "@ant-design/charts";
import React from "react";
import {StudentDailyCardStatistic} from "../../../entity/StudentDailyCardStatistic";

interface StudentPieChartProps {
    data?: StudentDailyCardStatistic
}

const StudentPieChart: React.FC<StudentPieChartProps> = ({data}) => {
    if (data == null) {
        return <></>
    }

    const testData: Record<string, any>[] = [
        {type: '绿码', value: data?.greenCodeStudentCount},
        {type: '黄码', value: data?.yellowCodeStudentCount},
        {type: '红码', value: data?.redCodeStudentCount}
    ];

    const config: PieConfig = {
        autoFit: false,
        data: testData,
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
    return <Pie style={{marginLeft: "41%", display: "block"}} {...config} />
}

export default StudentPieChart;