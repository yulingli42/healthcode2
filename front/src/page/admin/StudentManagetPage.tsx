import React, {useEffect, useState} from "react";
import {Button, Descriptions, PageHeader, Table} from "antd";
import instance from "../../axiosInstance";
import {StudentDailyCardStatistic} from "../../entity/StudentDailyCardStatistic";
import {healthCodeName, HealthCodeType} from "../../entity/HealthCodeType";
import {useParams} from "react-router-dom";
import InsertStudentModal from "./InsertStudentModal";
import {useSelector} from "react-redux";
import {RootState} from "../../store";
import {Admin, AdminRole} from "../../entity/Admin";
import {StudentDailyCardVo} from "../../entity/StudentDailyCardVo";
import {ColumnsType} from "antd/es/table";
import {Pie} from '@ant-design/charts';
import {PieConfig} from "@ant-design/charts/es/pie";

const StudentManagerPage = () => {
    const [loading, setLoading] = useState(false)
    const [modalVisible, setModalVisible] = useState(false)
    const [data, setData] = useState<StudentDailyCardStatistic>()
    const {collegeId, majorId, classId} = useParams<{ collegeId: string, majorId: string, classId: string }>()
    const loginUser = useSelector((state: RootState) => state.login)

    const columns: ColumnsType<StudentDailyCardVo> = [
        {title: '学号', dataIndex: 'studentId', key: 'studentId'},
        {title: '姓名', dataIndex: 'name', key: 'name'},
        {title: '学院', dataIndex: 'collegeName', key: 'collegeName'},
        {title: '专业', dataIndex: 'majorName', key: 'majorName'},
        {title: '班级', dataIndex: 'className', key: 'className'},
        {
            title: '健康码',
            dataIndex: 'type',
            key: 'type',
            render: (type?: HealthCodeType) => healthCodeName(type),
            filters: [{text: '红码', value: '红码'},
                {text: '绿码', value: '绿码'},
                {text: '黄码', value: '黄码'}
            ],
            onFilter: (value: string | number | boolean, record: StudentDailyCardVo) => healthCodeName(record.type) === value,
        }, {
            title: "今日填报", dataIndex: ""
        }];

    useEffect(() => {
        setLoading(true)
        instance.get<StudentDailyCardStatistic>("/admin/getStudentStatistic", {
            params: {
                collegeId, majorId, classId
            }
        }).then(response => setData(response.data))
            .finally(() => setLoading(false))
    }, [collegeId, majorId, classId])

    const admin = loginUser.user as Admin

    const notSubmitNumber = data == null ? 0 : data.totalStudentCount - data.greenCodeStudentCount -
        data.yellowCodeStudentCount - data.redCodeStudentCount

    const testData: Record<string, any>[] = [
        {type: '绿码', value: data?.greenCodeStudentCount},
        {type: '黄码', value: data?.yellowCodeStudentCount},
        {type: '红码', value: data?.redCodeStudentCount},
        {type: '未填报', value: notSubmitNumber}
    ];
    const config: PieConfig = {
        autoFit: false,
        data: testData,
        angleField: 'value',
        colorField: 'type',
        width: 200,
        height: 200,
        color: ({type}) => {
            switch (type) {
                case "绿码":
                    return "green";
                case "黄码":
                    return "yellow";
                case "红码":
                    return "red"
                default:
                    return "grey"
            }
        }
    };

    return (
        <div>
            <PageHeader
                onBack={() => window.history.back()}
                ghost={false}
                title="学生管理"
                extra={
                    admin.role === AdminRole.SYSTEM_ADMIN &&
                    <Button onClick={() => setModalVisible(true)} type={"primary"}>添加新学生</Button>
                }>
                <Descriptions>
                    <Descriptions.Item label="学生总数">{data?.totalStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="绿码学生数量">{data?.greenCodeStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="黄码学生数量">{data?.yellowCodeStudentCount}</Descriptions.Item>
                    <Descriptions.Item label="红码学生数量">{data?.redCodeStudentCount}</Descriptions.Item>
                </Descriptions>
                <Pie style={{marginLeft: "41%", display: "block"}} {...config} />
            </PageHeader>

            <InsertStudentModal visible={modalVisible} setVisible={setModalVisible}/>
            <Table loading={loading} columns={columns} rowKey={"studentId"} dataSource={data?.dailyCardList}/>
        </div>
    )
}

export default StudentManagerPage;