import React, {useEffect, useState} from "react";
import {Button, PageHeader, Table} from "antd";
import {Link, useParams} from "react-router-dom";
import {Major} from "../../../entity/Major";
import instance from "../../../axiosInstance";
import {Admin, AdminRole} from "../../../entity/Admin";
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import AddMajorModal from "./AddMajorModal";
import {Helmet} from "react-helmet";

const MajorManagerPage = () => {
    const loginUser = useSelector((state: RootState) => state.login).user as Admin
    const [loading, setLoading] = useState(false)
    const [visible, setVisible] = useState(false)
    const [data, setData] = useState<Major[]>([])
    const {collegeId} = useParams<{ collegeId: string }>();

    useEffect(() => {
        setLoading(true)
        instance.get<Major[]>("/admin/getMajorByCollegeId", {params: {collegeId: collegeId}})
            .then(response => setData(response.data))
            .finally(() => setLoading(false))
    }, [collegeId])

    const columns = [
        {title: '专业名', dataIndex: 'name', key: 'name'},
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/college/${collegeId}/major/${id}`}>查看所有班级</Link>
        },
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/student/${collegeId}/${id}`}>查看该专业所有学生信息</Link>
        }
    ]

    return (
        <div>
            <Helmet title={"专业管理"}/>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="专业管理"
                extra={loginUser.role === AdminRole.SYSTEM_ADMIN &&
                <Button type={"primary"} onClick={() => setVisible(true)}>添加新专业</Button>}>
            </PageHeader>
            <AddMajorModal visible={visible} setVisible={setVisible} collegeId={Number(collegeId)}/>
            <Table loading={loading} rowKey={"name"} columns={columns} dataSource={data}/>
        </div>
    )
}

export default MajorManagerPage;

