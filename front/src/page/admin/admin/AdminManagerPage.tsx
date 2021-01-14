import React, {useEffect, useState} from "react";
import {Admin, AdminRole} from "../../../entity/Admin";
import {Button, PageHeader, Table} from "antd";
import {ColumnsType} from "antd/es/table";
import {useSelector} from "react-redux";
import {RootState} from "../../../store";
import {College} from "../../../entity/College";
import AddAdminModal from "./AddAdminModal";
import instance from "../../../axiosInstance";
import {Helmet} from "react-helmet";

const AdminManagerPage = () => {
    const loginUser = useSelector((state: RootState) => state.login).user as Admin
    const [visible, setVisible] = useState(false)

    const [admins, setAdmins] = useState<Admin[]>([])

    const loadAdmin = () => {
        instance.get<Admin[]>("/admin/getAllAdmin",)
            .then(response => setAdmins(response.data))
    }

    const deleteAdmin = (id: number) => {
        instance.post("/admin/deleteAdmin", {id})
            .then(() => loadAdmin())
    }

    useEffect(() => {
        loadAdmin();
    }, [])

    const columns: ColumnsType<Admin> = [
        {title: '名称', dataIndex: 'username', key: 'username'},
        {
            title: '身份', dataIndex: 'role', key: 'role',
            render: (role: AdminRole) => {
                switch (role) {
                    case AdminRole.COLLEGE_ADMIN:
                        return "院级管理员"
                    case AdminRole.SCHOOL_ADMIN:
                        return "校级管理员"
                    case AdminRole.SYSTEM_ADMIN:
                        return "系统管理员"
                }
            }
        },
        {title: '学院', dataIndex: 'college', key: 'college', render: (college?: College) => college?.name},
        {
            title: '',
            dataIndex: 'id',
            key: 'id',
            render: (id: number) => id === loginUser.id ? null :

                <Button type={"link"} onClick={() => deleteAdmin(id)}>删除</Button>
        },
    ]

    return (
        <div>
            <Helmet title={"管理员管理"}/>
            <PageHeader
                ghost={false}
                title="管理员管理"
                extra={loginUser.role === AdminRole.SYSTEM_ADMIN &&
                <Button type={"primary"} onClick={() => setVisible(true)}>添加新管理员</Button>}>
            </PageHeader>
            <AddAdminModal visible={visible} setVisible={setVisible} onSuccess={() => loadAdmin()}/>
            <Table rowKey={"username"} dataSource={admins} columns={columns}/>
        </div>
    )

}

export default AdminManagerPage