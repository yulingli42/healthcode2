import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import {Button} from "antd";
import instance from "../axiosInstance";
import {setUser} from "../reducer/login/actionCreate";

const Header = () => {
    const loginUser = useSelector((root: RootState) => root.login)
    const dispatch = useDispatch()
    if (loginUser == null || !loginUser.login) {
        return <></>
    }
    const logout = async () => {
        await instance.post("/logout")
        dispatch(setUser(null))
    }

    return <>
        <Button onClick={logout} type={"primary"}>登出</Button>
        <span>{loginUser.name}</span>
    </>
}

export default Header;