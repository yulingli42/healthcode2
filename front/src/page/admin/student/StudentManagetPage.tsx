import React, {useEffect, useState} from "react";
import {Button, Descriptions, PageHeader} from "antd";
import instance from "../../../axiosInstance";
import {StudentDailyCardStatistic} from "../../../entity/StudentDailyCardStatistic";
import {useParams} from "react-router-dom";
import InsertStudentModal from "./InsertStudentModal";
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import {Admin, AdminRole} from "../../../entity/Admin";
import StudentTable from "./StudentTable";
import StudentPieChart from "./StudentPieChart";

const StudentManagerPage = () => {
    const [modalVisible, setModalVisible] = useState(false)
    const [data, setData] = useState<StudentDailyCardStatistic>()
    const {collegeId, majorId, classId} = useParams<{ collegeId: string, majorId: string, classId: string }>()
    const loginUser = useSelector((state: RootState) => state.login).user as Admin

    const loadStudent = async () => {
        return await instance.get<StudentDailyCardStatistic>("/admin/getStudentStatistic", {
            params: {
                collegeId, majorId, classId
            }
        });
    }

    useEffect(() => {
        loadStudent().then(response => setData(response.data))
    }, [collegeId, majorId, classId])


    const deleteStudent = async (id: string) => {
        await instance.post("/admin/deleteStudent", {id: id})
        const response = await loadStudent();
        setData(response.data)
    }

    return (
        <div>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="学生管理"
                extra={[
                    <Button
                        key={3}
                        onClick={() => setModalVisible(true)}
                        type={"primary"}
                        hidden={loginUser.role !== AdminRole.SYSTEM_ADMIN}>添加新学生</Button>
                ]}>
                <Descriptions>
                    <Descriptions.Item label="学生总数">{data?.totalStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="绿码学生数量">{data?.greenCodeStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="黄码学生数量">{data?.yellowCodeStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="红码学生数量">{data?.redCodeStudentCount}</Descriptions.Item>
                </Descriptions>
                <StudentPieChart data={data}/>
            </PageHeader>
            <InsertStudentModal visible={modalVisible} setVisible={setModalVisible}/>
            <StudentTable dataSource={data?.dailyCardList} onDelete={(id) => deleteStudent(id)}/>
        </div>
    )
}

export default StudentManagerPage;