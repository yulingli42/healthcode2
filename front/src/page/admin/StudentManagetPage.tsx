import React, {useEffect, useState} from "react";
import {Button, Descriptions, PageHeader, Table} from "antd";
import instance from "../../axiosInstance";
import {StudentDailyCardStatistic} from "../../entity/StudentDailyCardStatistic";
import {HealthCodeType} from "../../entity/HealthCodeType";
import {useParams} from "react-router-dom";
import InsertStudentModal from "./InsertStudentModal";
import {useSelector} from "react-redux";
import {RootState} from "../../store";

const StudentManagerPage = () => {
    const [modalVisible, setModalVisible] = useState(false)
    const [data, setData] = useState<StudentDailyCardStatistic>()
    const {collegeId, majorId, classId} = useParams<{ collegeId: string, majorId: string, classId: string }>()
    const loginUser = useSelector((state: RootState) => state.login)!!

    const columns = [
        {title: '学号', dataIndex: 'studentId', key: 'studentId'},
        {title: '姓名', dataIndex: 'name', key: 'name'},
        {title: '学院', dataIndex: 'className', key: 'className'},
        {title: '专业', dataIndex: 'majorName', key: 'majorName'},
        {title: '班级', dataIndex: 'collegeName', key: 'collegeName'},
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
        instance.get<StudentDailyCardStatistic>("/admin/getStudentStatistic", {
            params: {
                collegeId, majorId, classId
            }
        }).then(response => setData(response.data))
    }, [collegeId, majorId, classId])


    return (
        <div>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="学生管理"
                extra={
                    loginUser.type === "systemAdmin" &&
                    <Button onClick={() => setModalVisible(true)} type={"primary"}>添加新学生</Button>
                }>
                <Descriptions>
                    <Descriptions.Item label="学生总数">{data?.totalStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="绿码学生数量">{data?.greenCodeStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="黄码学生数量">{data?.yellowCodeStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="红码学生数量">{data?.redCodeStudentCount}</Descriptions.Item>
                </Descriptions>
            </PageHeader>

            <InsertStudentModal visible={modalVisible} setVisible={setModalVisible}/>
            <Table columns={columns} dataSource={data?.dailyCardList}/>
        </div>
    )
}

export default StudentManagerPage;