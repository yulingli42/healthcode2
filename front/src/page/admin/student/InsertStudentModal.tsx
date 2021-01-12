import {Input, Modal} from "antd";
import React, {useState} from "react";
import instance from "../../../axiosInstance";
import ClassCascader from "./ClassCascader";
import UploadFile from "../../../component/UploadFile";

interface ModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void
}

const InsertStudentModal: React.FC<ModalProps> = ({visible, setVisible}) => {
    const [classId, setClassId] = useState<number | null>(null)
    const [studentId, setStudentId] = useState<string>('')
    const [name, setName] = useState<string>('')
    const [idCard, setIdCard] = useState<string>('')

    const onSubmit = async () => {
        await instance.post("/admin/insertStudent", {id: studentId, name: name, classId: classId, idCard: idCard})
        setVisible(false)
    }

    return (
        <Modal
            title="添加新学生"
            onCancel={() => setVisible(false)}
            visible={visible}
            onOk={onSubmit}>
            <UploadFile url="/admin/addStudentFromExcel" onSuccess={() => setVisible(false)}/>
            <ClassCascader changeClass={value => setClassId(value)}/>
            <Input placeholder={"学号"}
                   onChange={(e) => setStudentId(e.target.value)}
                   value={studentId}/>
            <Input placeholder={"姓名"}
                   onChange={(e) => setName(e.target.value)}
                   value={name}/>
            <Input placeholder={"身份证号"}
                   onChange={(e) => setIdCard(e.target.value)}
                   value={idCard}/>
        </Modal>
    )
}

export default InsertStudentModal