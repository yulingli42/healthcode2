import {Button, Col, Form, Input, Radio, Row} from "antd";
import React from "react";
import {LockOutlined, UserOutlined} from "@ant-design/icons";
import instance from "../../axiosInstance";
import {LoginUser} from "../../entity/LoginUser";
import {useDispatch, useSelector} from "react-redux";
import {setUser} from "../../reducer/login/actionCreate";
import {RootState} from "../../store";
import {Redirect} from "react-router";
import ParticlesBg from 'particles-bg'
import {LoginForm} from "./style";
import {Helmet} from "react-helmet";

const LoginPage = () => {
    const loginUser = useSelector((state: RootState) => state.login)
    const dispatch = useDispatch()

    if (loginUser.login) {
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
            <Helmet title={"登录"}/>
            <ParticlesBg type="cobweb" bg={true} num={60}/>
            <LoginForm onFinish={onFinish}>
                <h2 style={{display: "block", fontSize: 30, textAlign: "center"}}>健康码管理系统</h2>
                <Form.Item
                    name="type"
                    style={{textAlign: "center"}}
                    rules={[{required: true, message: '请选择用户类型'}]}>
                    <Radio.Group style={{display: "block", width: "100%"}}>
                        <Row>
                            <Col span={8}><Radio value={"student"}>学生</Radio></Col>
                            <Col span={8}><Radio value={"teacher"}>教师</Radio></Col>
                            <Col span={8}><Radio value={"admin"}>管理员</Radio></Col>
                        </Row>
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
                    <Button type="primary" htmlType="submit" block>登录</Button>
                </Form.Item>
            </LoginForm>
        </div>
    )
}

export default LoginPage