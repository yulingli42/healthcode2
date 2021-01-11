import React, {useEffect, useState} from "react";
import {Button, PageHeader, Table} from "antd";
import {Clazz} from "../../entity/Clazz";
import instance from "../../axiosInstance";
import {Link, useParams} from "react-router-dom";
import {Admin, AdminRole} from "../../entity/Admin";
import {useSelector} from "react-redux";
import {RootState} from "../../store";

const ClassManagerPage = () => {
    const loginUser = useSelector((state: RootState) => state.login)!!
    const [loading, setLoading] = useState(false)
    const [data, setData] = useState<Clazz[]>([])
    const {collegeId, majorId} = useParams<{ collegeId: string, majorId: string }>()

    useEffect(() => {
        setLoading(true)
        instance.get<Clazz[]>("/admin/getAllClazzByMajor", {params: {majorId: majorId}})
            .then(response => setData(response.data))
            .finally(() => setLoading(false))
    }, [majorId])
    const columns = [
        {title: '班级名', dataIndex: 'name', key: 'name'},
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/student/${collegeId}/${majorId}/${id}`}>查看该班级所有学生信息</Link>
        }
    ]
    const admin = loginUser.user as Admin

    return <div>
        <PageHeader
            onBack={() => window.history.back()}
            ghost={false}
            title="班级管理"
            extra={admin.role === AdminRole.SYSTEM_ADMIN && <Button type={"primary"}>添加新班级</Button>}>
        </PageHeader>
        <Table loading={loading} columns={columns} dataSource={data}/>
    </div>
}

export default ClassManagerPage