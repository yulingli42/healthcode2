import {Input, Modal, Tabs} from "antd";
import React, {useState} from "react";
import instance from "../../../axiosInstance";
import ClassCascader from "./ClassCascader";
import UploadFile from "../../../component/UploadFile";

interface ModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void
}

const {TabPane} = Tabs;

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
            <Tabs>
                <TabPane tab="手动导入" key="1">
                    <ClassCascader changeClass={value => setClassId(value)} style={{marginBottom: 15, width: "100%"}}/>
                    <Input placeholder={"学号"}
                           style={{marginBottom: 15}}
                           onChange={(e) => setStudentId(e.target.value)}
                           value={studentId}/>
                    <Input placeholder={"姓名"}
                           style={{marginBottom: 15}}
                           onChange={(e) => setName(e.target.value)}
                           value={name}/>
                    <Input placeholder={"身份证号"}
                           style={{marginBottom: 15}}
                           onChange={(e) => setIdCard(e.target.value)}
                           value={idCard}/>
                </TabPane>
                <TabPane tab="从 excel 中导入" key="2">
                    <UploadFile url="/admin/addStudentFromExcel" onSuccess={() => setVisible(false)}/>
                </TabPane>
            </Tabs>


        </Modal>
    )
}

export default InsertStudentModal