import React, {useEffect, useState} from "react";
import {Button, Descriptions, PageHeader, Table} from "antd";
import {HealthCodeType} from "../../entity/HealthCodeType";
import instance from "../../axiosInstance";
import {TeacherDailyCardStatistic} from "../../entity/TeacherDailyCardStatistic";
import InsertTeacherModal from "./InsertTeacherModal";
import {useSelector} from "react-redux";
import {RootState} from "../../store";

const TeacherManagerPage = () => {
    const [visible, setVisible] = useState(false)
    const loginUser = useSelector((state: RootState) => state.login)!!

    const [data, setData] = useState<TeacherDailyCardStatistic>()
    const columns = [
        {title: '工号', dataIndex: 'teacherId', key: 'teacherId'},
        {title: '姓名', dataIndex: 'name', key: 'name'},
        {title: '学院', dataIndex: 'className', key: 'className'},
        {
            title: '健康码', dataIndex: 'type', key: 'type',
            render: (type: HealthCodeType) => {
                switch (type) {
                    case HealthCodeType.GREEN:
                        return "绿码"
                    case HealthCodeType.RED:
                        return "红码"
                    case HealthCodeType.YELLOW:
                        return "黄码"
                }
            }
        }];

    useEffect(() => {
        instance.get<TeacherDailyCardStatistic>("/admin/getTeacherStatistic")
            .then(response => setData(response.data))
    }, [])

    return (
        <div>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="教师管理"
                extra={
                    loginUser.type === "systemAdmin" &&
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
            <Table columns={columns} dataSource={data?.dailyCardList}/>
        </div>
    )
}

export default TeacherManagerPage;