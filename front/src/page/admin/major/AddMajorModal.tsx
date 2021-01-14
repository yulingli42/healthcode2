import React, {useState} from "react";
import {Input, Modal} from "antd";
import instance from "../../../axiosInstance";

interface AddMajorModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void,
    collegeId: number,
    onSuccess: () => void
}

const AddMajorModal: React.FC<AddMajorModalProps> = ({visible, setVisible, collegeId, onSuccess}) => {
    const [name, setName] = useState('')
    const onSubmit = async () => {
        await instance.post("/admin/addMajor", {name, collegeId})
        setVisible(false)
        onSuccess()
    }

    return (
        <Modal
            title="添加新专业"
            onCancel={() => setVisible(false)}
            onOk={onSubmit}
            visible={visible}>
            <Input placeholder={"专业名"}
                   onChange={(e) => setName(e.target.value)}
                   value={name}/>
        </Modal>
    )
}

export default AddMajorModal;