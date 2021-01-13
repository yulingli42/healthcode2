import {Layout, Menu} from "antd";
import {Admin, AdminRole} from "../../entity/Admin";
import {GoldOutlined, LogoutOutlined, TeamOutlined, UserOutlined, PartitionOutlined} from "@ant-design/icons";
import React from "react";
import {useHistory} from "react-router";
import instance from "../../axiosInstance";
import {setUser} from "../../reducer/login/actionCreate";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../store";

const {Sider} = Layout;
const AdminSider = () => {

    const admin = useSelector((state: RootState) => state.login).user as Admin
    const history = useHistory()
    const dispatch = useDispatch()

    const logout = async () => {
        await instance.post("/logout")
        dispatch(setUser())
        history.push("/")
    }

    return <Sider collapsible className="site-layout-background">
        <div className="logo"/>
        <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
            <Menu.Item disabled={true} key="0">
                {admin.role === AdminRole.SYSTEM_ADMIN && "系统管理员"}
                {admin.role === AdminRole.SCHOOL_ADMIN && "校级管理员"}
                {admin.role === AdminRole.COLLEGE_ADMIN && "院级管理员"}
            </Menu.Item>
            <Menu.Item key="1"
                       icon={<TeamOutlined/>}
                       onClick={() => history.push("/admin/student")}>
                学生管理
            </Menu.Item>
            <Menu.Item key="2"
                       icon={<UserOutlined/>}
                       onClick={() => history.push("/admin/teacher")}>
                教师管理
            </Menu.Item>
            <Menu.Item key="3"
                       icon={<GoldOutlined/>}
                       onClick={() => history.push("/admin/college")}>
                学院管理
            </Menu.Item>
            {
                admin.role === AdminRole.SYSTEM_ADMIN &&
                <Menu.Item key="4"
                           icon={<PartitionOutlined/>}
                           onClick={() => history.push("/admin/admin")}>
                    管理员管理
                </Menu.Item>
            }
            <Menu.Item key="5"
                       icon={<LogoutOutlined/>}
                       onClick={logout}>
                登出
            </Menu.Item>
        </Menu>
    </Sider>
}

export default AdminSider;