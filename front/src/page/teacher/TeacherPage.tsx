import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {RootState} from "../../store";
import {Redirect} from "react-router";
import Header from "../../component/Header";
import instance from "../../axiosInstance";
import SubmitDailyCardForm from "../../component/SubmitDailyCardForm";
import {Skeleton} from "antd";
import {Teacher} from "../../entity/Teacher";
import QrcodeImage from "../../component/QRCodeImage";
import {Helmet} from "react-helmet";


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

    if (!loginUser.login) {
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

        instance.post("/teacher/submit", {
            haveBeenToKeyEpidemicAreas,
            haveBeenAbroad,
            isTheExposed,
            isSuspectedCase,
            currentSymptoms
        }).then(() => setSubmitDailyCard(true))
    }

    return <div>
        <Helmet title={isSubmitDailyCard ? "健康码" : "每日一报"}/>
        <Header url={"/teacher/changePassword"}/>
        <div style={{marginLeft: '30%', marginRight: "30%", marginTop: 30}}>
            {isLoading && <Skeleton active={true}/>}
            {!isLoading && isSubmitDailyCard && <QrcodeImage/>}
            {!isLoading && !isSubmitDailyCard &&
            <SubmitDailyCardForm onFinish={onFinish} user={loginUser.user as Teacher} isStudent={false}/>}
        </div>
    </div>
}

export default TeacherPage