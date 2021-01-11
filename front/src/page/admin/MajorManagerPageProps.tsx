import React, {useEffect, useState} from "react";
import {Button, PageHeader, Table} from "antd";
import {Link, useParams} from "react-router-dom";
import {Major} from "../../entity/Major";
import instance from "../../axiosInstance";
import {Admin, AdminRole} from "../../entity/Admin";
import {useSelector} from "react-redux";
import {RootState} from "../../store";

const MajorManagerPage = () => {
    const loginUser = useSelector((state: RootState) => state.login)
    const [loading, setLoading] = useState(false)
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

    const admin = loginUser.user as Admin
    return (
        <div>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="专业管理"
                extra={admin.role === AdminRole.SYSTEM_ADMIN && <Button type={"primary"}>添加新专业</Button>}>
            </PageHeader>
            <Table loading={loading} columns={columns} dataSource={data}/>
        </div>
    )
}

export default MajorManagerPage;

