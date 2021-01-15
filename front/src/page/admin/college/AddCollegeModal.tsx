import React, {useState} from "react";
import {Input, Modal} from "antd";
import instance from "../../../axiosInstance";

interface AddCollegeModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void,
    onSuccess: () => void
}

const AddCollegeModal: React.FC<AddCollegeModalProps> = ({visible, setVisible, onSuccess}) => {
    const [name, setName] = useState('')
    const onSubmit = async () => {
        await instance.post("/admin/addCollege", {name})
        setVisible(false)
        onSuccess()
    }

    return (
        <Modal
            title="添加新学院"
            onCancel={() => setVisible(false)}
            onOk={onSubmit}
            visible={visible}>
            <Input placeholder={"学院名"}
                   onChange={(e) => setName(e.target.value)}
                   value={name}/>
        </Modal>
    )
}

export default AddCollegeModal;