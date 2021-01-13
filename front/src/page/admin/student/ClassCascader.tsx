import React, {CSSProperties, useEffect, useState} from "react";
import instance from "../../../axiosInstance";
import {Major} from "../../../entity/Major";
import {College} from "../../../entity/College";
import {Cascader} from "antd";
import {CascaderOptionType, CascaderValueType} from "antd/lib/cascader";

interface ClassCascaderProps {
    changeClass: (id: number | null) => void,
    style?: CSSProperties;
}

const ClassCascader: React.FC<ClassCascaderProps> = ({changeClass,style}) => {
    const [options, setOptions] = useState<Array<CascaderOptionType>>([]);

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

    const loadData = async (selectedOptions?: CascaderOptionType[]) => {
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

    const onChange = (value: CascaderValueType, selectedOptions?: CascaderOptionType[]) => {
        if (value.length === 3 && selectedOptions != null) {
            changeClass(selectedOptions[2].value as number)
        } else {
            changeClass(null)
        }
    }

    return <Cascader
        options={options}
        loadData={loadData}
        changeOnSelect
        placeholder={"请选择班级"}
        onChange={onChange}
        style={style}/>
}

export default ClassCascader;