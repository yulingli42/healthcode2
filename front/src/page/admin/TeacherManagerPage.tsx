import React, {useEffect, useState} from "react";
import {Button, Descriptions, PageHeader, Table} from "antd";
import {healthCodeName, HealthCodeType} from "../../entity/HealthCodeType";
import instance from "../../axiosInstance";
import {TeacherDailyCardStatistic} from "../../entity/TeacherDailyCardStatistic";
import InsertTeacherModal from "./InsertTeacherModal";
import {useSelector} from "react-redux";
import {RootState} from "../../store";
import {Admin, AdminRole} from "../../entity/Admin";
import {TeacherDailyCardVo} from "../../entity/TeacherDailyCardVo";
import {ColumnsType} from "antd/es/table";
import {Pie} from "@ant-design/charts";
import {PieConfig} from "@ant-design/charts/es/pie";

const TeacherManagerPage = () => {
    const [visible, setVisible] = useState(false)
    const loginUser = useSelector((state: RootState) => state.login)
    const [loading, setLoading] = useState(false)

    const [data, setData] = useState<TeacherDailyCardStatistic>()
    const columns: ColumnsType<TeacherDailyCardVo> = [
        {title: '工号', dataIndex: 'teacherId', key: 'teacherId'},
        {title: '姓名', dataIndex: 'name', key: 'name'},
        {title: '学院', dataIndex: 'collegeName', key: 'collegeName'},
        {
            title: '健康码',
            dataIndex: 'type',
            key: 'type',
            render: (type?: HealthCodeType) => healthCodeName(type),
            filters: [{text: '红码', value: '红码'},
                {text: '绿码', value: '绿码'},
                {text: '黄码', value: '黄码'},
                {text: '未填报', value: '未填报'}
            ],
            onFilter: (value: string | number | boolean, record: TeacherDailyCardVo) => healthCodeName(record.type) === value,
        }];

    useEffect(() => {
        setLoading(true)
        instance.get<TeacherDailyCardStatistic>("/admin/getTeacherStatistic")
            .then(response => setData(response.data))
            .finally(() => setLoading(false))
    }, [])

    const admin = loginUser.user as Admin

    const notSubmitNumber = data == null ? 0: data.totalTeacherCount - data.greenCodeTeacherCount - data.yellowCodeTeacherCount - data.redCodeTeacherCount

    const config: PieConfig = {
        autoFit: false,
        data: [
            {type: '绿码', value: data?.greenCodeTeacherCount},
            {type: '黄码', value: data?.yellowCodeTeacherCount},
            {type: '红码', value: data?.redCodeTeacherCount},
            {type: '未填报', value:notSubmitNumber}
        ],
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
                title="教师管理"
                extra={
                    admin.role === AdminRole.SYSTEM_ADMIN &&
                    <Button onClick={() => setVisible(true)} type={"primary"}>添加新教师</Button>
                }>
                <Descriptions>
                    <Descriptions.Item label="教师总数">{data?.totalTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="绿码教师数量">{data?.greenCodeTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="黄码教师数量">{data?.yellowCodeTeacherCount}</Descriptions.Item>
                    <Descriptions.Item label="红码教师数量">{data?.redCodeTeacherCount}</Descriptions.Item>
                </Descriptions>
                <Pie style={{paddingLeft: "41%", display: "block"}} {...config} />
            </PageHeader>
            <InsertTeacherModal visible={visible} setVisible={setVisible}/>
            <Table loading={loading} columns={columns} rowKey={"teacherId"} dataSource={data?.dailyCardList}/>
        </div>
    )
}

export default TeacherManagerPage;