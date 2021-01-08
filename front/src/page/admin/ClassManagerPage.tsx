import React, {useEffect, useState} from "react";
import {Table} from "antd";
import {Clazz} from "../../entity/Clazz";
import instance from "../../axiosInstance";
import {Link, useParams} from "react-router-dom";

const ClassManagerPage = () => {
    const [data, setData] = useState<Clazz[]>([])
    const {collegeId, majorId} = useParams<{ collegeId: string, majorId: string }>()

    useEffect(() => {
        instance.get<Clazz[]>("/admin/getAllClazzByMajor", {params: {majorId: majorId}})
            .then(response => setData(response.data))
    }, [majorId])
    const columns = [
        {title: '班级名', dataIndex: 'name', key: 'name'},
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/student/${collegeId}/${majorId}/${id}`}>查看该班级所有学生信息</Link>
        }
    ]

    return <div>
        <Table columns={columns} dataSource={data}/>
    </div>
}

export default ClassManagerPage