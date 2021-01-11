import React, {useEffect, useState} from "react";
import {Button, Descriptions, PageHeader, Table} from "antd";
import {healthCodeName, HealthCodeType} from "../../entity/HealthCodeType";
import instance from "../../axiosInstance";
import {TeacherDailyCardStatistic} from "../../entity/TeacherDailyCardStatistic";
import InsertTeacherModal from "./InsertTeacherModal";
import {useSelector} from "react-redux";
import {RootState} from "../../store";
import {Admin, AdminRole} from "../../entity/Admin";
import {TeacherDailyCardVo} from "../../entity/TeacherDailyCardVo";
import {ColumnsType} from "antd/es/table";

const TeacherManagerPage = () => {
    const [visible, setVisible] = useState(false)
    const loginUser = useSelector((state: RootState) => state.login)
    const [loading, setLoading] = useState(false)

    const [data, setData] = useState<TeacherDailyCardStatistic>()
    const columns : ColumnsType<TeacherDailyCardVo>= [
        {title: '工号', dataIndex: 'teacherId', key: 'teacherId'},
        {title: '姓名', dataIndex: 'name', key: 'name'},
        {title: '学院', dataIndex: 'collegeName', key: 'collegeName'},
        {
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
        }];

    useEffect(() => {
        setLoading(true)
        instance.get<TeacherDailyCardStatistic>("/admin/getTeacherStatistic")
            .then(response => setData(response.data))
            .finally(() => setLoading(false))
    }, [])

    const admin = loginUser.user as Admin

    return (
        <div>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="教师管理"
                extra={
                    admin.role === AdminRole.SYSTEM_ADMIN &&
                    <Button onClick={() => setVisible(true)} type={"primary"}>添加新教师</Button>
                }>
                <Descriptions>
                    <Descriptions.Item label="教师总数">{data?.totalTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="绿码教师数量">{data?.greenCodeTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="黄码教师数量">{data?.yellowCodeTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="红码教师数量">{data?.redCodeTeacherCount}</Descriptions.Item>
                </Descriptions>
            </PageHeader>
            <InsertTeacherModal visible={visible} setVisible={setVisible}/>
            <Table loading={loading} columns={columns} rowKey={"teacherId"} dataSource={data?.dailyCardList}/>
        </div>
    )
}

export default TeacherManagerPage;