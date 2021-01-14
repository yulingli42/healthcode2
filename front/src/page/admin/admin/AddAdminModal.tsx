import React, {useEffect, useState} from "react";
import {Input, Modal, Select} from "antd";
import {AdminRole} from "../../../entity/Admin";
import instance from "../../../axiosInstance";
import {College} from "../../../entity/College";

const {Option} = Select;

interface AddAdminModalProps {
    visible: boolean,
    setVisible: (value: boolean) => void,
    onSuccess: () => void
}

const AddAdminModal: React.FC<AddAdminModalProps> = ({visible, setVisible, onSuccess}) => {
    const [colleges, setColleges] = useState<College[]>([])
    const [name, setName] = useState('')
    const [password, setPassword] = useState('')
    const [type, setType] = useState(AdminRole.SYSTEM_ADMIN)
    const [collegeId, setCollegeId] = useState<number>()
    const [hiddenCollege, setHiddenCollege] = useState(true)

    useEffect(() => {
        instance.get<College[]>("/admin/getAllCollege")
            .then((resp) => setColleges(resp.data))
    }, [])

    const submit = async () => {
        await instance.post("/admin/addAdmin", {name, password, type, collegeId})
        setVisible(false)
        onSuccess()
    }

    const onTypeChange = (type: AdminRole) => {
        setType(type)
        if (type === AdminRole.COLLEGE_ADMIN) {
            setHiddenCollege(false)
        } else {
            setHiddenCollege(true)
        }
    }

    return (
        <Modal
            title={"添加管理员"}
            visible={visible}
            onCancel={() => setVisible(false)}
            onOk={() => submit()}
        >
            <Input placeholder={"管理员名称"} value={name} onChange={e => setName(e.target.value)}/>
            <Input type={"password"} placeholder={"管理员密码"} value={password}
                   onChange={e => setPassword(e.target.value)}/>
            <Select style={{display: "block"}} value={type}
                    onChange={onTypeChange}>
                <Select.Option value={AdminRole.SYSTEM_ADMIN}>系统管理员</Select.Option>
                <Select.Option value={AdminRole.SCHOOL_ADMIN}>校级管理员</Select.Option>
                <Select.Option value={AdminRole.COLLEGE_ADMIN}>院级管理员</Select.Option>
            </Select>
            {
                !hiddenCollege && <Select style={{display: "block"}} value={collegeId} onChange={setCollegeId}>
                    {
                        colleges.map(college => <Option key={college.id} value={college.id}>{college.name}</Option>)
                    }
                </Select>
            }
        </Modal>
    )
}

export default AddAdminModal