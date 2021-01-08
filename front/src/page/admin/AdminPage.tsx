import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../store";
import {Redirect, Route, Switch, useHistory} from "react-router";
import {Layout, Menu} from 'antd';
import React from "react";
import StudentManagerPage from "./StudentManagetPage";
import TeacherManagerPage from "./TeacherManagerPage";
import CollegeManagerPage from "./CollegeManagerPage";
import {GoldOutlined, LogoutOutlined, TeamOutlined, UserOutlined} from '@ant-design/icons'
import ClassManagerPage from "./ClassManagerPage";
import MajorManagerPage from "./MajorManagerPage";
import instance from "../../axiosInstance";
import {setUser} from "../../reducer/login/actionCreate";

const {Content, Sider} = Layout;

const AdminPage = () => {
    const loginUser = useSelector((state: RootState) => state.login)
    const history = useHistory()
    const dispatch = useDispatch()


    if (loginUser == null || !loginUser.login) {
        return <Redirect to={"/"}/>
    }

    if (loginUser.type === "teacher") {
        return <Redirect to={"/teacher"}/>
    }

    if (loginUser.type === "student") {
        return <Redirect to={"/student"}/>
    }

    const logout = async () => {
        await instance.post("/logout")
        dispatch(setUser(null))
    }

    return (<div>
        <Layout style={{minHeight: '100vh'}}>
            <Sider collapsible className="site-layout-background">
                <div className="logo"/>
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
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
                    <Menu.Item key="4"
                               icon={<LogoutOutlined/>}
                               onClick={logout}>
                        登出
                    </Menu.Item>
                </Menu>
            </Sider>
            <Layout className="site-layout">
                <Content>
                    <div className="site-layout-background" style={{padding: 24, minHeight: 360}}>
                        <Switch>
                            <Route path={"/admin/student"} exact component={StudentManagerPage}/>
                            <Route path={"/admin/student/:collegeId"} exact component={StudentManagerPage}/>
                            <Route path={"/admin/student/:collegeId/:majorId"} exact component={StudentManagerPage}/>
                            <Route path={"/admin/student/:collegeId/:majorId/:classId"} exact
                                   component={StudentManagerPage}/>
                            <Route path={"/admin/teacher"} exact component={TeacherManagerPage}/>
                            <Route path={"/admin/teacher/:collegeId"} exact component={TeacherManagerPage}/>
                            <Route path={"/admin/college"} exact component={CollegeManagerPage}/>
                            <Route path={"/admin/college/:collegeId"} exact component={MajorManagerPage}/>
                            <Route path={"/admin/college/:collegeId/major/:majorId"} exact
                                   component={ClassManagerPage}/>
                        </Switch>
                    </div>
                </Content>
            </Layout>
        </Layout>
    </div>)

}

export default AdminPage;