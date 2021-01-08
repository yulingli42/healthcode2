import {Table} from "antd";
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {College} from "../../entity/College";
import instance from "../../axiosInstance";

const CollegeManagerPage = () => {
    const [data, setData] = useState<College[]>([])

    useEffect(() => {
        instance.get<College[]>("/admin/getAllCollege")
            .then((resp) => setData(resp.data))
    }, [])

    const columns = [
        {title: '学院名', dataIndex: 'name', key: 'name'},
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/college/${id}`}>查看所有专业</Link>
        },
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/student/${id}`}>查看该学院所有学生信息</Link>
        },
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/student/${id}`}>查看该学院所有教师信息</Link>
        }
    ]

    return (
        <div>
            <Table columns={columns} dataSource={data}/>
        </div>
    )
}

export default CollegeManagerPage