import React from 'react';
import LoginPage from "./page/login/LoginPage";
import {Route, Switch} from "react-router";
import AdminPage from "./page/admin/AdminPage";
import StudentPage from "./page/student/StudentPage";
import TeacherPage from "./page/teacher/TeacherPage";

function App() {
    return <>
        <Switch>
            <Route path="/" exact component={LoginPage}/>
            <Route path="/teacher" component={TeacherPage}/>
            <Route path="/admin" component={AdminPage}/>
            <Route path="/student" component={StudentPage}/>
        </Switch>
    </>
}

export default App;
