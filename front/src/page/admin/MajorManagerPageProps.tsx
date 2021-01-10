import React, {useEffect, useState} from "react";
import {Button, PageHeader, Table} from "antd";
import {Link, useParams} from "react-router-dom";
import {Major} from "../../entity/Major";
import instance from "../../axiosInstance";

const MajorManagerPage
    = () => {
    const [data, setData] = useState<Major[]>([])
    const {collegeId} = useParams<{ collegeId: string }>();

    useEffect(() => {
        instance.get<Major[]>("/admin/getMajorByCollegeId", {params: {collegeId: collegeId}})
            .then(response => setData(response.data))
    }, [collegeId])

    const columns = [
        {title: '专业名', dataIndex: 'name', key: 'name'},
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/college/${collegeId}/major/${id}`}>查看所有班级</Link>
        },
        {
            title: '', dataIndex: 'id', key: 'id',
            render: (id: number) => <Link to={`/admin/student/${collegeId}/${id}`}>查看该专业所有学生信息</Link>
        }
    ]

    return (
        <div>
            <PageHeader
                ghost={false}
                title="专业管理"
                extra={<Button type={"primary"}>添加新专业</Button>}>
            </PageHeader>
            <Table columns={columns} dataSource={data}/>
        </div>
    )
}

export default MajorManagerPage;

