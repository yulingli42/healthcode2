import React, {useEffect, useState} from "react";
import {Button, Descriptions, PageHeader} from "antd";
import instance from "../../../axiosInstance";
import {TeacherDailyCardStatistic} from "../../../entity/TeacherDailyCardStatistic";
import InsertTeacherModal from "./InsertTeacherModal";
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import {Admin, AdminRole} from "../../../entity/Admin";
import TeacherTable from "./TeacherTable";
import TeacherPieChart from "./TeacherPieChart";
import {useParams} from "react-router-dom";
import {College} from "../../../entity/College";
import UpdateTeacherModal from "./UpdateTeacherModal";
import {Helmet} from "react-helmet";

const TeacherManagerPage = () => {
    const [insertVisible, setInsertVisible] = useState(false)
    const [updateVisible, setUpdateVisible] = useState(false)
    const [updateTeacherId, setUpdateTeacherId] = useState<string>()
    const loginUser = useSelector((state: RootState) => state.login).user as Admin
    const {collegeId} = useParams<{ collegeId?: string }>()
    const [data, setData] = useState<TeacherDailyCardStatistic>()
    const [college, setCollege] = useState<College>()

    const loadTeacher = async (collegeId?: string) => {
        const response = await instance
            .get<TeacherDailyCardStatistic>("/admin/getTeacherStatistic", {params: {collegeId: collegeId}});
        setData(() => response.data)
    }

    useEffect(() => {
        setCollege(undefined)
        loadTeacher(collegeId)
        if (collegeId != null) {
            instance
                .get<College>("/admin/getCollege", {params: {id: collegeId}})
                .then(response => setCollege(response.data))
        }
    }, [collegeId])

    const deleteTeacher = async (id: string) => {
        await instance.post("/admin/deleteTeacher", {id: id})
        await loadTeacher()
    }

    const downloadPdf = () => {
        instance.get("/admin/teacherPdf", {responseType: "blob"})
            .then(response => {
                const blob = new Blob([response.data])
                let link = document.createElement("a");
                let evt = document.createEvent("HTMLEvents");
                evt.initEvent("click", false, false);
                link.href = URL.createObjectURL(blob);
                link.download = "教师导出.pdf";
                link.style.display = "none";
                document.body.appendChild(link);
                link.click();
                window.URL.revokeObjectURL(link.href);
            })
    }
    return (
        <div>
            <Helmet title={"教师管理"}/>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="教师管理"
                subTitle={college?.name}
                extra={[
                    <Button key={"2"} onClick={downloadPdf}>导出为PDF</Button>,
                    <Button
                        hidden={loginUser.role !== AdminRole.SYSTEM_ADMIN}
                        onClick={() => setInsertVisible(true)}
                        type={"primary"}>添加新教师</Button>
                ]}>
                <Descriptions>
                    {college && <Descriptions.Item label={"学院名"}> {college.name}</Descriptions.Item>}
                    <Descriptions.Item label="教师总数">{data?.totalTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="绿码教师数量">{data?.greenCodeTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="黄码教师数量">{data?.yellowCodeTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="红码教师数量">{data?.redCodeTeacherCount}</Descriptions.Item>
                </Descriptions>
                <TeacherPieChart data={data}/>
            </PageHeader>
            <UpdateTeacherModal
                visible={updateVisible}
                setVisible={setUpdateVisible}
                onSuccess={() => loadTeacher()}
                teacherId={updateTeacherId}
            />
            <InsertTeacherModal
                visible={insertVisible}
                setVisible={setInsertVisible}
                onSuccess={() => loadTeacher()}/>
            <TeacherTable
                clickUpdate={(id) => {
                    setUpdateTeacherId(id);
                    setUpdateVisible(true);
                }}
                dailyCardList={data?.dailyCardList}
                onDelete={(id) => deleteTeacher(id)}/>
        </div>
    )
}

export default TeacherManagerPage;