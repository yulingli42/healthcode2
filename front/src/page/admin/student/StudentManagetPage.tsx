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
import UploadStudentModal from "./UpdateStudentModal";
import {Helmet} from "react-helmet";

const StudentManagerPage = () => {
    const [insertVisible, setInsertVisible] = useState(false)
    const [uploadVisible, setUploadVisible] = useState(false)
    const [needUpdateStudentId, setNeedUpdateStudentId] = useState<string>()
    const [data, setData] = useState<StudentDailyCardStatistic>()
    const {collegeId, majorId, classId} = useParams<{ collegeId?: string, majorId?: string, classId?: string }>()
    const loginUser = useSelector((state: RootState) => state.login).user as Admin
    const [clazz, setClazz] = useState<Clazz>()
    const [major, setMajor] = useState<Major>()
    const [college, setCollege] = useState<College>()

    const loadStudent = (collegeId?: string, majorId?: string, classId?: string) => {
        instance.get<StudentDailyCardStatistic>("/admin/getStudentStatistic", {
            params: {
                collegeId, majorId, classId
            }
        }).then(response => setData(() => response.data));
    }

    useEffect(() => {
        setClazz(undefined)
        setMajor(undefined)
        setCollege(undefined)
        loadStudent(collegeId, majorId, classId)
        if (classId != null) {
            instance
                .get<Clazz>("/admin/getClass", {params: {id: classId}})
                .then(response => setClazz(() => response.data))
        } else if (majorId != null) {
            instance
                .get<Major>("/admin/getMajor", {params: {id: majorId}})
                .then(response => setMajor(() => response.data))
        } else if (collegeId != null) {
            instance
                .get<College>("/admin/getCollege", {params: {id: collegeId}})
                .then(response => setCollege(() => response.data))
        }
    }, [collegeId, majorId, classId])


    const deleteStudent = async (id: string) => {
        await instance.post("/admin/deleteStudent", {id: id})
        await loadStudent();
    }

    const onClickStudent = (id: string) => {
        setNeedUpdateStudentId(id)
        setUploadVisible(true)
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

    const downloadPdf = () => {
        instance.get("/admin/studentPdf", {responseType: "blob", params: {collegeId, majorId, classId}})
            .then(response => {
                const blob = new Blob([response.data])
                let link = document.createElement("a");
                let evt = document.createEvent("HTMLEvents");
                evt.initEvent("click", false, false);
                link.href = URL.createObjectURL(blob);
                link.download = "学生导出.pdf";
                link.style.display = "none";
                document.body.appendChild(link);
                link.click();
                window.URL.revokeObjectURL(link.href);
            })
    }

    return (
        <div>
            <Helmet title={"学生管理"}/>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title={"学生管理"}
                subTitle={getTitle()}
                extra={[
                    <Button key={"2"} onClick={downloadPdf}>导出为PDF</Button>,
                    <Button
                        key={"1"}
                        onClick={() => setInsertVisible(true)}
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
            <UploadStudentModal
                updateStudentId={needUpdateStudentId}
                visible={uploadVisible}
                setVisible={setUploadVisible}
                onSuccess={() => loadStudent()}/>
            <InsertStudentModal
                visible={insertVisible}
                setVisible={setInsertVisible}
                onSuccess={() => loadStudent()}/>
            <StudentTable
                clickUpdate={(id) => onClickStudent(id)}
                dataSource={data?.dailyCardList} onDelete={(id) => deleteStudent(id)}/>
        </div>
    )
}

export default StudentManagerPage;