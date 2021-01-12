import {useSelector} from "react-redux";
import {RootState} from "../../store";
import {Redirect, Route, Switch} from "react-router";
import {Layout} from 'antd';
import React from "react";
import StudentManagerPage from "./student/StudentManagetPage";
import TeacherManagerPage from "./teacher/TeacherManagerPage";
import CollegeManagerPage from "./college/CollegeManagerPage";
import ClassManagerPage from "./class/ClassManagerPage";
import MajorManagerPage from "./major/MajorManagerPageProps";
import AdminSider from "./AdminSider";
import AdminManagerPage from "./admin/AdminManagerPage";

const {Content} = Layout;

const AdminPage = () => {
    const loginUser = useSelector((state: RootState) => state.login)

    if (!loginUser.login) {
        return <Redirect to={"/"}/>
    }

    if (loginUser.type === "teacher") {
        return <Redirect to={"/teacher"}/>
    }

    if (loginUser.type === "student") {
        return <Redirect to={"/student"}/>
    }

    return (<div>
        <Layout style={{minHeight: '100vh'}}>
            <AdminSider/>
            <Layout className="site-layout">
                <Content>
                    <div className="site-layout-background" style={{padding: 24, minHeight: 360}}>
                        <Switch>
                            <Route path={"/admin/admin"} exact component={AdminManagerPage}/>
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