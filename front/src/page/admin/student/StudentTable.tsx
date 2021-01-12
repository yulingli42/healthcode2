import {StudentDailyCardVo} from "../../../entity/StudentDailyCardVo";
import {Button, Table} from "antd";
import React from "react";
import {ColumnsType} from "antd/es/table";
import {healthCodeName, HealthCodeType} from "../../../entity/HealthCodeType";

interface StudentTableProps {
    dataSource?: StudentDailyCardVo[],
    onDelete: (id: string) => void
}

const StudentTable: React.FC<StudentTableProps> = ({dataSource, onDelete}) => {
    const columns: ColumnsType<StudentDailyCardVo> = [
        {title: '学号', dataIndex: 'studentId', key: 'studentId'},
        {title: '姓名', dataIndex: 'name', key: 'name'},
        {title: '学院', dataIndex: 'collegeName', key: 'collegeName'},
        {title: '专业', dataIndex: 'majorName', key: 'majorName'},
        {title: '班级', dataIndex: 'className', key: 'className'},
        {
            title: "今日打卡情况",
            dataIndex: "hadSubmitDailyCard",
            key: "hadSubmitDailyCard",
            render: (submit: boolean) => submit ? "已打卡" : "未打卡",
            filters: [{text: "已打卡", value: true}, {text: '未打卡', value: false}],
            onFilter: (value: string | number | boolean, record: StudentDailyCardVo) => record.hadSubmitDailyCard === value,
        },
        {
            title: '健康码',
            dataIndex: 'type',
            key: 'type',
            render: (type?: HealthCodeType) => healthCodeName(type),
            filters: [{text: '红码', value: '红码'},
                {text: '绿码', value: '绿码'},
                {text: '黄码', value: '黄码'}
            ],
            onFilter: (value: string | number | boolean, record: StudentDailyCardVo) => healthCodeName(record.type) === value,
        }, {
            title: "操作",
            dataIndex: "studentId",
            key: "studentId",
            render: (id: string) => <>
                <Button type="link">编辑</Button>
                <Button type="link" onClick={() => onDelete(id)}>删除</Button>
            </>
        }];

    return <Table loading={dataSource == null} columns={columns} rowKey={"studentId"} dataSource={dataSource}/>

}

export default StudentTable;