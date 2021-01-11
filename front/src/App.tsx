import React, {useEffect} from 'react';
import LoginPage from "./page/login/LoginPage";
import {Route, Switch} from "react-router";
import AdminPage from "./page/admin/AdminPage";
import StudentPage from "./page/student/StudentPage";
import TeacherPage from "./page/teacher/TeacherPage";
import {useDispatch, useSelector} from "react-redux";
import instance from "./axiosInstance";
import {LoginUser} from "./entity/LoginUser";
import {setUser} from "./reducer/login/actionCreate";
import {RootState} from "./store";
import {Spin} from 'antd';

function App() {
    const dispatch = useDispatch()
    const loginUser = useSelector((state: RootState) => state.login)
    useEffect(() => {
        instance
            .get<LoginUser>("/checkLoginStatus")
            .then(response => dispatch(setUser(response.data)))
    }, [dispatch])
    return <>

        {!loginUser && <div style={{width: "100%", height: "100%", textAlign: "center", marginTop: "20%"}}>
            <Spin size={"large"}/>
        </div>}
        {loginUser && <Switch>
            <Route path="/" exact component={LoginPage}/>
            <Route path="/teacher" component={TeacherPage}/>
            <Route path="/admin" component={AdminPage}/>
            <Route path="/student" component={StudentPage}/>
        </Switch>}
    </>
}

export default App;
