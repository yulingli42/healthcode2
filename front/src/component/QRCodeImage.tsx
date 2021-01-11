import React from "react";
import {Image} from "antd";
import {Student} from "../entity/Student";
import {useSelector} from "react-redux";
import {RootState} from "../store";
import {Teacher} from "../entity/Teacher";


const QrcodeImage = () => {
    const loginUser = useSelector((state: RootState) => state.login)!!
    const user = loginUser.user as Student | Teacher

    return <div style={{display: "block", textAlign: "center"}}>
        <h2>{user.name} 你好</h2>
        <h2>请在接受检查时主动出示工大通行码</h2>
        <h2>配合管理员的工作，做好自主防护工作</h2>
        <h2>你的健康码状态将根据你的申报动态刷新</h2>
        <Image
            preview={false}
            src={loginUser.type === "student" ? "/student/qrcode" : "/teacher/qrcode"}
            alt={"健康码"}/>
    </div>
}

export default QrcodeImage;