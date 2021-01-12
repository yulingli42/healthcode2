import React, {useEffect, useState} from "react";
import {Button, PageHeader, Table} from "antd";
import {Clazz} from "../../../entity/Clazz";
import instance from "../../../axiosInstance";
import {Link, useParams} from "react-router-dom";
import {Admin, AdminRole} from "../../../entity/Admin";
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import AddClassModal from "./AddClassModal";

const ClassManagerPage = () => {
    const loginUser = useSelector((state: RootState) => state.login).user as Admin
    const [loading, setLoading] = useState(false)
    const [data, setData] = useState<Clazz[]>([])
    const [visible, setVisible] = useState(false)
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

    return <div>
        <PageHeader
            onBack={() => window.history.back()}
            ghost={false}
            title="班级管理"
            extra={loginUser.role === AdminRole.SYSTEM_ADMIN &&
            <Button type={"primary"} onClick={() => setVisible(true)}>添加新班级</Button>}>
        </PageHeader>
        <AddClassModal visible={visible} setVisible={setVisible} majorId={Number(majorId)}/>
        <Table loading={loading} rowKey={"name"} columns={columns} dataSource={data}/>
    </div>
}

export default ClassManagerPage