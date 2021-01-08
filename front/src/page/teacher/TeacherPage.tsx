import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {RootState} from "../../store";
import {Redirect} from "react-router";
import Header from "../../component/Header";
import instance from "../../axiosInstance";
import SubmitDailyCardForm from "../../component/SubmitDailyCardForm";
import {Skeleton} from "antd";


const TeacherPage = () => {
    const [isSubmitDailyCard, setSubmitDailyCard] = useState<Boolean>(false)
    const loginUser = useSelector((state: RootState) => state.login)
    const [isLoading, setLoading] = useState(true)

    useEffect(() => {
        instance
            .get<Boolean>("/teacher/cardStatus")
            .then(response => setSubmitDailyCard(response.data))
            .finally(() => setLoading(false))
    }, [])


    if (loginUser == null || !loginUser.login) {
        return <Redirect to={"/"}/>
    }
    if (loginUser.type === "student") {
        return <Redirect to={"/student"}/>
    }
    if (loginUser.type !== "teacher") {
        return <Redirect to={"/admin"}/>
    }

    const onFinish = async (haveBeenToKeyEpidemicAreas: boolean,
                            haveBeenAbroad: boolean,
                            isTheExposed: boolean,
                            isSuspectedCase: boolean,
                            currentSymptoms: Array<string>) => {

        instance.post("/student/submit", {
            haveBeenToKeyEpidemicAreas,
            haveBeenAbroad,
            isTheExposed,
            isSuspectedCase,
            currentSymptoms
        }).then(() => setSubmitDailyCard(true))
    }

    return <div style={{marginLeft: '30%', marginRight: "30%", marginTop: 30}}>
        <Header/>
        {isLoading && <Skeleton active={true}/>}
        {!isLoading && isSubmitDailyCard && <img src={"/student/qrcode"} alt={"健康码"}/>}
        {!isLoading && !isSubmitDailyCard && <SubmitDailyCardForm onFinish={onFinish}/>}
    </div>
}

export default TeacherPage