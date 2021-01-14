import React from "react";
import {Button, Popconfirm, Table} from "antd";
import {TeacherDailyCardVo} from "../../../entity/TeacherDailyCardVo";
import {ColumnsType} from "antd/es/table";
import {healthCodeName, HealthCodeType} from "../../../entity/HealthCodeType";
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import {Admin, AdminRole} from "../../../entity/Admin";

interface TeacherTableProps {
    dailyCardList?: TeacherDailyCardVo[],
    onDelete: (id: string) => void,
    clickUpdate: (id: string) => void,
}

const TeacherTable: React.FC<TeacherTableProps> = ({dailyCardList, onDelete, clickUpdate}) => {
    const admin = useSelector((root: RootState) => root.login).user as Admin

    const columns: ColumnsType<TeacherDailyCardVo> = [
        {title: '工号', dataIndex: 'teacherId', key: 'teacherId'},
        {title: '姓名', dataIndex: 'name', key: 'name'},
        {title: '学院', dataIndex: 'collegeName', key: 'collegeName'},
        {
            title: "今日打卡情况",
            dataIndex: "hadSubmitDailyCard",
            key: "hadSubmitDailyCard",
            render: (submit: boolean) => submit ? "已打卡" : "未打卡",
            filters: [{text: "已打卡", value: true}, {text: '未打卡', value: false}],
            onFilter: (value: string | number | boolean, record: TeacherDailyCardVo) => record.hadSubmitDailyCard === value,
        }, {
            title: '健康码',
            dataIndex: 'type',
            key: 'type',
            render: (type?: HealthCodeType) => healthCodeName(type),
            filters: [{text: '红码', value: '红码'},
                {text: '绿码', value: '绿码'},
                {text: '黄码', value: '黄码'},
                {text: '未填报', value: '未填报'}
            ],
            onFilter: (value: string | number | boolean, record: TeacherDailyCardVo) => healthCodeName(record.type) === value,
        }, {
            title: "操作",
            dataIndex: "teacherId",
            key: "teacherId",
            render: (id: string) => <>
                {
                    admin.role === AdminRole.SYSTEM_ADMIN && <>
                        <Button type="link" onClick={() => clickUpdate(id)}>编辑</Button>
                        <Popconfirm title={"是否删除该教师"} onConfirm={() => onDelete(id)}>
                            <Button type="link">删除</Button>
                        </Popconfirm>
                    </>
                }
            </>
        }];

    return <Table loading={dailyCardList == null} columns={columns} rowKey={"teacherId"} dataSource={dailyCardList}/>
}

export default TeacherTable