import React, {useState} from "react";
import {Input, Modal} from "antd";
import ClassCascader from "./ClassCascader";
import instance from "../../../axiosInstance";

interface ModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void,
    onSuccess: () => void,
    updateStudentId?: string
}

const UploadStudentModal: React.FC<ModalProps> = ({visible, setVisible, onSuccess, updateStudentId}) => {
    const [classId, setClassId] = useState<number | null>()
    const [name, setName] = useState<string>('')
    const [idCard, setIdCard] = useState<string>('')

    const onSubmit = async () => {
        await instance.post("/admin/updateStudent", {id: updateStudentId, classId, name, idCard})
        setVisible(false)
        onSuccess()
    }

    return (
        <Modal visible={visible}
               onCancel={() => setVisible(false)}
               title="修改学生"
               onOk={() => onSubmit()}>
            <ClassCascader changeClass={value => setClassId(value)} style={{marginBottom: 15, width: "100%"}}/>
            <Input placeholder={"学号"}
                   disabled={true}
                   style={{marginBottom: 15}}
                   value={updateStudentId}/>
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

export default UploadStudentModal;