import React, {useState} from "react";
import {Input, Modal, Tabs} from "antd";
import instance from "../../../axiosInstance";
import UploadFile from "../../../component/UploadFile";
import CollegeSelect from "./CollegeSelect";


interface ModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void,
    onSuccess: () => void
}

const {TabPane} = Tabs;

const InsertTeacherModal: React.FC<ModalProps> = ({visible, setVisible, onSuccess}) => {
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
        setName('')
        setIdCard('')
        onSuccess()
        setVisible(false)
    }

    return (
        <Modal
            title="添加新教师"
            onCancel={() => setVisible(false)}
            visible={visible}
            onOk={onSubmit}>
            <Tabs>
                <TabPane tab="手动导入" key="1">
                    <CollegeSelect onChange={setCollegeId} style={{marginBottom: 15, width: "100%"}}/>
                    <Input placeholder={"工号"}
                           style={{marginBottom: 15}}
                           onChange={(e) => setTeacherId(e.target.value)}
                           value={teacherId}/>
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
                    <UploadFile url="/admin/addTeacherFromExcel" onSuccess={() => setVisible(false)}/>
                </TabPane>
            </Tabs>

        </Modal>
    )
}

export default InsertTeacherModal