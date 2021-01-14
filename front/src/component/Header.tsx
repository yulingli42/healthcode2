import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import {Affix, Input, Modal} from "antd";
import instance from "../axiosInstance";
import {setUser} from "../reducer/login/actionCreate";
import {ChangePasswordWrapper, LogoutWrapper, NameWrapper, TopWrapper} from "./style";
import {useHistory} from "react-router";

interface HeaderProps {
    url: string
}

const Header: React.FC<HeaderProps> = ({url}) => {
    const [visible, setVisible] = useState(false)
    const [newPassword, setNewPassword] = useState('')
    const [oldPassword, setOldPassword] = useState('')
    const loginUser = useSelector((root: RootState) => root.login)
    const dispatch = useDispatch()
    const history = useHistory()

    const logout = async () => {
        await instance.post("/logout")
        dispatch(setUser())
        history.push("/")
    }

    const submit = () => {
        instance.post(url, {oldPassword, newPassword}).then(() => setVisible(false))
    }

    return <Affix>
        <TopWrapper>
            {"name" in loginUser.user && <NameWrapper>{loginUser.user.name}</NameWrapper>}
            {"username" in loginUser.user && <NameWrapper>{loginUser.user.username}</NameWrapper>}
            <LogoutWrapper onClick={logout} type={"primary"}>登出</LogoutWrapper>
            <ChangePasswordWrapper type={"link"} onClick={() => setVisible(true)}>修改密码</ChangePasswordWrapper>
            <Modal
                title={"修改密码"}
                visible={visible}
                onCancel={() => setVisible(false)}
                onOk={() => submit()}>
                <Input
                    style={{marginBottom: 15}}
                    placeholder={"请输入旧密码"}
                    value={oldPassword}
                    onChange={e => setOldPassword(e.target.value)}
                    type={"password"}/>
                <Input
                    placeholder={"请输入新密码"}
                    value={newPassword}
                    onChange={e => setNewPassword(e.target.value)}
                    type={"password"}/>
            </Modal>
        </TopWrapper>
    </Affix>
}

export default Header;