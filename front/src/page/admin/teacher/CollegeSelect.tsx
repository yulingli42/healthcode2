import {Select} from "antd";
import React, {useEffect, useState} from "react";
import {College} from "../../../entity/College";
import instance from "../../../axiosInstance";

interface CollegeSelectProps {
    onChange: (value: number) => void
}

const {Option} = Select;

const CollegeSelect: React.FC<CollegeSelectProps> = ({onChange}) => {
    const [collegeList, setCollegeList] = useState<College[]>([])

    useEffect(() => {
        instance.get<College[]>("/admin/getAllCollege")
            .then(response => setCollegeList(response.data))
    }, [])

    return <Select placeholder={"学院"} style={{width: "100%"}} onChange={(value) => onChange(value as number)}>
        {
            collegeList.map(college => <Option key={college.id} value={college.id}>{college.name}</Option>)
        }
    </Select>
}

export default CollegeSelect;