import {Button, PageHeader, Table} from "antd";
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {College} from "../../../entity/College";
import instance from "../../../axiosInstance";
import {Admin, AdminRole} from "../../../entity/Admin";
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import AddCollegeModal from "./AddCollegeModal";
import {Helmet} from "react-helmet";

const CollegeManagerPage = () => {
    const loginUser = useSelector((state: RootState) => state.login).user as Admin
    const [loading, setLoading] = useState(false)
    const [data, setData] = useState<College[]>([])
    const [visible, setVisible] = useState(false)

    const loadCollege = () => {
        setLoading(true)
        instance.get<College[]>("/admin/getAllCollege")
            .then((resp) => setData(resp.data))
            .finally(() => setLoading(false))
    }

    useEffect(() => {
        loadCollege();
    }, [])

    const deleteCollege = (id: number) => {
        instance
            .post("/admin/deleteCollege", {id})
            .then(() => loadCollege())
    }

    const columns = [
        {title: '学院名', dataIndex: 'name', key: 'name'},
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/college/${id}`}>查看所有专业</Link>
        },
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/student/${id}`}>查看该学院所有学生信息</Link>
        },
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/teacher/${id}`}>查看该学院所有教师信息</Link>
        },
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => loginUser.role === AdminRole.SYSTEM_ADMIN &&
                <Button type="link" onClick={() => deleteCollege(id)}>删除</Button>
        }
    ]

    return (
        <div>
            <Helmet title={"学院管理"}/>
            <PageHeader
                ghost={false}
                title="学院管理"
                extra={loginUser.role === AdminRole.SYSTEM_ADMIN &&
                <Button type={"primary"} onClick={() => setVisible(true)}>添加新学院</Button>}>
            </PageHeader>
            <Table loading={loading} rowKey={"name"} columns={columns} dataSource={data}/>
            <AddCollegeModal
                visible={visible}
                setVisible={setVisible}
                onSuccess={() => loadCollege()}/>
        </div>
    )
}

export default CollegeManagerPage