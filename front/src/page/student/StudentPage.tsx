import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {RootState} from "../../store";
import {Redirect} from "react-router";
import instance from "../../axiosInstance";
import Header from "../../component/Header";
import SubmitDailyCardForm from "../../component/SubmitDailyCardForm";
import {Skeleton} from "antd";
import {Student} from "../../entity/Student";
import QrcodeImage from "../../component/QRCodeImage";

const StudentPage = () => {
    const loginUser = useSelector((state: RootState) => state.login)

    const [isSubmitDailyCard, setSubmitDailyCard] = useState<Boolean>(false)
    const [isLoading, setLoading] = useState(true)

    useEffect(() => {
        instance
            .get<Boolean>("/student/cardStatus")
            .then(response => setSubmitDailyCard(response.data))
            .finally(() => setLoading(false))
    }, [])

    if (!loginUser.login) {
        return <Redirect to={"/"}/>
    }

    if (loginUser.type === "teacher") {
        return <Redirect to={"/teacher"}/>
    }

    if (loginUser.type !== "student") {
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

    return <div>
        <Header/>
        <div style={{marginLeft: '30%', marginRight: "30%", marginTop: 30}}>
            {isLoading && <Skeleton active={true}/>}
            {!isLoading && isSubmitDailyCard && <QrcodeImage/>}
            {!isLoading && !isSubmitDailyCard &&
            <SubmitDailyCardForm onFinish={onFinish} isStudent={true} user={loginUser.user as Student}/>}
        </div>
    </div>
}

export default StudentPage;