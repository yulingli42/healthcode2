import {Input, Modal} from "antd";
import CollegeSelect from "./CollegeSelect";
import React, {useState} from "react";
import instance from "../../../axiosInstance";


interface ModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void,
    onSuccess: () => void,
    teacherId?: string
}

const UpdateTeacherModal: React.FC<ModalProps> = ({visible, setVisible, onSuccess, teacherId}) => {
    const [collegeId, setCollegeId] = useState<number | null>(null)
    const [name, setName] = useState<string>()
    const [idCard, setIdCard] = useState<string>()

    const onSubmit = async () => {
        await instance.post("/admin/updateTeacher", {id: teacherId, collegeId, name, idCard})
        setVisible(false)
        onSuccess()
    }

    return (
        <Modal
            title="添加新教师"
            onCancel={() => setVisible(false)}
            visible={visible}
            onOk={onSubmit}>
            <CollegeSelect onChange={setCollegeId} style={{marginBottom: 15, width: "100%"}}/>
            <Input placeholder={"工号"}
                   style={{marginBottom: 15}}
                   disabled={true}
                   value={teacherId}/>
            <Input placeholder={"姓名"}
                   style={{marginBottom: 15}}
                   onChange={(e) => setName(e.target.value)}
                   value={name}/>
            <Input placeholder={"身份证号"}
                   style={{marginBottom: 15}}
                   onChange={(e) => setIdCard(e.target.value)}
                   value={idCard}/>
        </Modal>
    )
}

export default UpdateTeacherModal;