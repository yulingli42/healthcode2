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

const TeacherManagerPage = () => {
    const [visible, setVisible] = useState(false)
    const loginUser = useSelector((state: RootState) => state.login).user as Admin
    const {collegeId} = useParams<{ collegeId?: string }>()
    const [data, setData] = useState<TeacherDailyCardStatistic>()
    const [college, setCollege] = useState<College>()

    function loadTeacher() {
        return instance.get<TeacherDailyCardStatistic>("/admin/getTeacherStatistic", {params: {collegeId: collegeId}});
    }

    useEffect(() => {
        setCollege(undefined)
        loadTeacher().then(response => setData(response.data))
        if (collegeId != null) {
            instance
                .get<College>("/admin/getCollege", {params: {id: collegeId}})
                .then(response => setCollege(response.data))
        }
    }, [collegeId])

    const deleteTeacher = async (id: string) => {
        await instance.post("/admin/deleteTeacher", {id: id})
        loadTeacher().then(response => setData(response.data))
    }

    return (
        <div>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="教师管理"
                subTitle={college?.name}
                extra={
                    loginUser.role === AdminRole.SYSTEM_ADMIN &&
                    <Button onClick={() => setVisible(true)} type={"primary"}>添加新教师</Button>
                }>
                <Descriptions>
                    {college && <Descriptions.Item label={"学院名"}> {college.name}</Descriptions.Item>}
                    <Descriptions.Item label="教师总数">{data?.totalTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="绿码教师数量">{data?.greenCodeTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="黄码教师数量">{data?.yellowCodeTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="红码教师数量">{data?.redCodeTeacherCount}</Descriptions.Item>
                </Descriptions>
                <TeacherPieChart data={data}/>
            </PageHeader>
            <InsertTeacherModal
                visible={visible}
                setVisible={setVisible}
                onSuccess={() => loadTeacher().then(response => setData(response.data))}/>
            <TeacherTable dailyCardList={data?.dailyCardList} onDelete={(id) => deleteTeacher(id)}/>
        </div>
    )
}

export default TeacherManagerPage;