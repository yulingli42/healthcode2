import {Button, Form, Input, Radio} from "antd";
import React, {useEffect} from "react";
import {LockOutlined, UserOutlined} from "@ant-design/icons";
import instance from "../../axiosInstance";
import {LoginUser} from "../../entity/LoginUser";
import {useDispatch, useSelector} from "react-redux";
import {setUser} from "../../reducer/login/actionCreate";
import {RootState} from "../../store";
import {Redirect} from "react-router";

const LoginPage = () => {
    const loginUser = useSelector((state: RootState) => state.login)
    const dispatch = useDispatch()


    useEffect(() => {
        instance
            .get<LoginUser>("/checkLoginStatus")
            .then(response => dispatch(setUser(response.data)))
    }, [dispatch])

    if (loginUser != null && loginUser.login) {
        switch (loginUser.type) {
            case "teacher":
                return <Redirect to="/teacher"/>
            case "student":
                return <Redirect to="/student"/>
            default:
                return <Redirect to="/admin"/>
        }

    }

    const onFinish = async (value: any) => {
        try {
            const username = value.username;
            const password = value.password;
            const type = value.type;
            const response = await instance.post<LoginUser>('/login', {type, username, password})
            dispatch(setUser(response.data))
        } catch (e) {
        }
    }

    return (
        <div>
            <Form
                style={{marginLeft: "30%", marginRight: "30%", marginTop: 30}}
                onFinish={onFinish}>
                <Form.Item
                    name="type"
                    rules={[{required: true, message: '请选择用户类型'}]}>
                    <Radio.Group>
                        <Radio value={"student"}>学生</Radio>
                        <Radio value={"teacher"}>教师</Radio>
                        <Radio value={"admin"}>管理员</Radio>
                    </Radio.Group>
                </Form.Item>
                <Form.Item
                    name="username"
                    rules={[{required: true, message: '请填写用户名'}]}>
                    <Input prefix={<UserOutlined className="site-form-item-icon"/>}
                           autoComplete={"username"}
                           placeholder={"用户名"}/>
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[{required: true, message: '请填写密码'}]}>
                    <Input prefix={<LockOutlined className="site-form-item-icon"/>}
                           placeholder={"密码"}
                           autoComplete={"current-password"}
                           type="password"/>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit">登录</Button>
                </Form.Item>
            </Form>
        </div>
    )
}

export default LoginPage