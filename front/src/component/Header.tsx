import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import {Affix} from "antd";
import instance from "../axiosInstance";
import {setUser} from "../reducer/login/actionCreate";
import {LogoutWrapper, NameWrapper, TopWrapper} from "./style";
import {useHistory} from "react-router";

const Header = () => {
    const loginUser = useSelector((root: RootState) => root.login)
    const dispatch = useDispatch()
    const history = useHistory()

    const logout = async () => {
        await instance.post("/logout")
        dispatch(setUser())
        history.push("/")
    }

    return <Affix>
        <TopWrapper>
            {"name" in loginUser.user && <NameWrapper>{loginUser.user.name}</NameWrapper>}
            {"username" in loginUser.user && <NameWrapper>{loginUser.user.username}</NameWrapper>}
            <LogoutWrapper onClick={logout} type={"primary"}>登出</LogoutWrapper>
        </TopWrapper>
    </Affix>
}

export default Header;