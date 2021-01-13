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
import {Clazz} from "../../../entity/Clazz";
import {Major} from "../../../entity/Major";
import {College} from "../../../entity/College";

const StudentManagerPage = () => {
    const [modalVisible, setModalVisible] = useState(false)
    const [data, setData] = useState<StudentDailyCardStatistic>()
    const {collegeId, majorId, classId} = useParams<{ collegeId?: string, majorId?: string, classId?: string }>()
    const loginUser = useSelector((state: RootState) => state.login).user as Admin
    const [clazz, setClazz] = useState<Clazz>()
    const [major, setMajor] = useState<Major>()
    const [college, setCollege] = useState<College>()

    const loadStudent = async () => {
        return await instance.get<StudentDailyCardStatistic>("/admin/getStudentStatistic", {
            params: {
                collegeId, majorId, classId
            }
        });
    }

    useEffect(() => {
        setClazz(undefined)
        setMajor(undefined)
        setCollege(undefined)
        loadStudent().then(response => setData(response.data))
        if (classId != null) {
            instance
                .get<Clazz>("/admin/getClass", {params: {id: classId}})
                .then(response => setClazz(response.data))
        } else if (majorId != null) {
            instance
                .get<Major>("/admin/getMajor", {params: {id: majorId}})
                .then(response => setMajor(response.data))
        } else if (collegeId != null) {
            instance
                .get<College>("/admin/getCollege", {params: {id: collegeId}})
                .then(response => setCollege(response.data))
        }
    }, [collegeId, majorId, classId])


    const deleteStudent = async (id: string) => {
        await instance.post("/admin/deleteStudent", {id: id})
        const response = await loadStudent();
        setData(response.data)
    }
    const getTitle = (): string => {
        if (clazz != null) {
            return `${clazz.name}`
        } else if (major != null) {
            return `${major.name}`
        } else if (college != null) {
            return `${college.name}`
        }
        return ''
    }
    return (
        <div>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title={"学生管理"}
                subTitle={getTitle()}
                extra={[
                    <Button
                        key={3}
                        onClick={() => setModalVisible(true)}
                        type={"primary"}
                        hidden={loginUser.role !== AdminRole.SYSTEM_ADMIN}>添加新学生</Button>
                ]}>
                <Descriptions>
                    {clazz && <>
                        <Descriptions.Item label={"班级名"}> {clazz.name}</Descriptions.Item>
                        <Descriptions.Item label={"专业名"}> {clazz.major.name}</Descriptions.Item>
                        <Descriptions.Item label={"学院名"}> {clazz.major.college.name}</Descriptions.Item>
                    </>}
                    {major && <>
                        <Descriptions.Item label={"专业名"}> {major.name}</Descriptions.Item>
                        <Descriptions.Item label={"学院名"}> {major.college.name}</Descriptions.Item>
                    </>}
                    {college && <Descriptions.Item label={"学院名"}> {college.name}</Descriptions.Item>}
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