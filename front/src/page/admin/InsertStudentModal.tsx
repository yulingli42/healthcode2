import {Cascader, Input, Modal} from "antd";
import React, {useEffect, useState} from "react";
import {CascaderOptionType} from "antd/lib/cascader";
import instance from "../../axiosInstance";
import {Major} from "../../entity/Major";
import {College} from "../../entity/College";

interface ModalProps {
    visible: boolean,
    setVisible: (visible: boolean) => void
}

interface Option extends CascaderOptionType {
    type?: "college" | "major" | "class"
}

const InsertStudentModal: React.FC<ModalProps> = ({visible, setVisible}) => {
    const [options, setOptions] = useState<Array<Option>>([]);
    const [classId, setClassId] = useState<number | null>(null)
    const [studentId, setStudentId] = useState<string>('')
    const [name, setName] = useState<string>('')
    const [idCard, setIdCard] = useState<string>('')

    const onSubmit = async () => {
        await instance.post("/admin/insertStudent", {id: studentId, name: name, classId: classId, idCard: idCard})
        setVisible(false)
    }


    useEffect(() => {
        instance.get<College[]>("/admin/getAllCollege")
            .then((resp) => setOptions(resp.data.map((college) => {
                return {
                    value: college.id,
                    label: college.name,
                    isLeaf: false,
                    type: "college"
                }
            })))
    }, [])

    const loadData = async (selectedOptions?: Option[]) => {
        if (!selectedOptions) {
            return
        }
        console.log(selectedOptions)
        const targetOption = selectedOptions[selectedOptions.length - 1];
        targetOption.loading = true;

        try {
            targetOption.id = targetOption.value
            switch (targetOption.type) {
                case "college": {
                    const response = await instance.get<Major[]>("/admin/getMajorByCollegeId",
                        {
                            params: {collegeId: targetOption.value}
                        });
                    targetOption.children = response.data.map(major => {
                        return {
                            value: major.id,
                            label: major.name,
                            isLeaf: false,
                            type: "major"
                        }
                    })
                }
                    break
                case "major": {
                    const response = await instance.get<Major[]>("/admin/getAllClazzByMajor",
                        {
                            params: {majorId: targetOption.value}
                        });
                    targetOption.children = response.data.map(major => {
                        return {
                            value: major.id,
                            label: major.name,
                            isLeaf: true,
                            type: "class"
                        }
                    })
                }
                    break
            }
            setOptions([...options]);
        } catch (e) {

        } finally {
            targetOption.loading = false;
        }
    };

    return (
        <Modal
            title="添加新学生"
            onCancel={() => setVisible(false)}
            visible={visible}
            onOk={onSubmit}>
            <Cascader options={options}
                      loadData={loadData}
                      changeOnSelect
                      placeholder={"请选择班级"}
                      onChange={(value, selectedOptions) => {
                          if (value.length === 3 && selectedOptions != null) {
                              setClassId(selectedOptions[2].value as number)
                          } else {
                              setClassId(null)
                          }
                      }}
                      style={{width: '100%'}}/>
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