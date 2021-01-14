import React, {useState} from "react";
import {Input, Modal} from "antd";
import instance from "../../../axiosInstance";

interface AddMajorModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void,
    majorId: number,
    onSuccess: () => void
}

const AddMajorModal: React.FC<AddMajorModalProps> = ({visible, setVisible, majorId, onSuccess}) => {
    const [name, setName] = useState('')
    const onSubmit = async () => {
        await instance.post("/admin/addClass", {name, majorId})
        setVisible(false)
        onSuccess()
    }

    return (
        <Modal
            title="添加新班级"
            onCancel={() => setVisible(false)}
            onOk={onSubmit}
            visible={visible}>
            <Input placeholder={"班级名"}
                   onChange={(e) => setName(e.target.value)}
                   value={name}/>
        </Modal>
    )
}

export default AddMajorModal;