import React, {useState} from "react";
import {Input, Modal} from "antd";
import instance from "../../../axiosInstance";
import UploadFile from "../../../component/UploadFile";
import CollegeSelect from "./CollegeSelect";


interface ModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void
}


const InsertTeacherModal: React.FC<ModalProps> = ({visible, setVisible}) => {
    const [collegeId, setCollegeId] = useState<number | null>(null)

    const [teacherId, setTeacherId] = useState<string>()
    const [name, setName] = useState<string>()
    const [idCard, setIdCard] = useState<string>()

    const onSubmit = async () => {
        await instance.post("/admin/insertTeacher", {
            teacherId: teacherId,
            name: name,
            collegeId: collegeId,
            idCard: idCard
        })
        setVisible(false)
    }

    return (
        <Modal
            title="添加新教师"
            onCancel={() => setVisible(false)}
            visible={visible}
            onOk={onSubmit}>
            <UploadFile url="/admin/addTeacherFromExcel" onSuccess={() => setVisible(false)}/>
            <CollegeSelect onChange={setCollegeId}/>
            <Input placeholder={"工号"}
                   onChange={(e) => setTeacherId(e.target.value)}
                   value={teacherId}/>
            <Input placeholder={"姓名"}
                   onChange={(e) => setName(e.target.value)}
                   value={name}/>
            <Input placeholder={"身份证号"}
                   onChange={(e) => setIdCard(e.target.value)}
                   value={idCard}/>
        </Modal>
    )
}

export default InsertTeacherModal