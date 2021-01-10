import React from "react";
import {Button, Checkbox, Col, Divider, Form, Input, Radio, Row} from "antd";
import {Teacher} from "../entity/Teacher";
import {Student} from "../entity/Student";

interface FormProps {
    onFinish: (haveBeenToKeyEpidemicAreas: boolean,
               haveBeenAbroad: boolean,
               isTheExposed: boolean,
               isSuspectedCase: boolean,
               currentSymptoms: Array<string>) => void,
    user: Teacher | Student,
    isStudent: boolean
}

const SubmitDailyCardForm: React.FC<FormProps> = ({onFinish, user, isStudent}) => {
    const finish = (values: any) => {
        const haveBeenToKeyEpidemicAreas = values.haveBeenToKeyEpidemicAreas != null
        const haveBeenAbroad = values.haveBeenAbroad != null
        const isTheExposed = values.isTheExposed != null
        const isSuspectedCase = values.isSuspectedCase != null
        const currentSymptoms = values.currentSymptoms
        onFinish(haveBeenToKeyEpidemicAreas, haveBeenAbroad, isTheExposed, isSuspectedCase, currentSymptoms)
    }

    const radioStyle = {
        display: 'block',
        height: '30px',
        lineHeight: '30px',
    };

    return (
        <div>
            <Form onFinish={finish} style={{marginTop: 30}}>
                <Form.Item label={isStudent ? "学号" : "工号"} style={{display: "block"}}>
                    <Input value={user.id} disabled/>
                </Form.Item>
                <Form.Item label="姓名" style={{display: "block"}}>
                    <Input value={user.name} disabled/>
                </Form.Item>
                <Form.Item label="身份证号" style={{display: "block"}}>
                    <Input value={user.idCard} disabled/>
                </Form.Item>
                <Form.Item
                    style={{display: "block"}}
                    name="haveBeenToKeyEpidemicAreas" label="近期（14天内）去过湖北省或重点疫区"
                    rules={[{required: true, message: '未选择此项'}]}>
                    <Radio.Group>
                        <Radio value="true" style={radioStyle}>是</Radio>
                        <Radio value="false" style={radioStyle}>否</Radio>
                    </Radio.Group>
                </Form.Item>
                <Divider/>
                <Form.Item name="haveBeenAbroad"
                           label="近期（14天内）去过国外"
                           style={{display: "block"}}
                           rules={[{required: true, message: '未选择此项'}]}>
                    <Radio.Group>
                        <Radio value="true" style={radioStyle}>是</Radio>
                        <Radio value="false" style={radioStyle}>否</Radio>
                    </Radio.Group>
                </Form.Item>
                <Divider/>
                <Form.Item name="isTheExposed"
                           label="近期（14天内）接触过新冠确诊病人或疑似病人"
                           style={{display: "block"}}
                           rules={[{required: true, message: '未选择此项'}]}>
                    <Radio.Group>
                        <Radio value="true" style={radioStyle}>是</Radio>
                        <Radio value="false" style={radioStyle}>否</Radio>
                    </Radio.Group>
                </Form.Item>
                <Divider/>
                <Form.Item name="isSuspectedCase"
                           label="被卫生部门确认为新冠肺炎确诊病例或疑似病例"
                           style={{display: "block"}}
                           rules={[{required: true, message: '未选择此项'}]}>
                    <Radio.Group>
                        <Radio value="true" style={radioStyle}>是</Radio>
                        <Radio value="false" style={radioStyle}>否</Radio>
                    </Radio.Group>
                </Form.Item>
                <Divider/>
                <Form.Item name="currentSymptoms"
                           label="身体健康情况"
                           style={{display: "block"}}>
                    <Checkbox.Group>
                        <Row>
                            <Col span={8}><Checkbox value="发烧">发烧（≥37.3℃）</Checkbox></Col>
                            <Col span={8}><Checkbox value="乏力">乏力</Checkbox></Col>
                            <Col span={8}><Checkbox value="干咳">干咳</Checkbox></Col>
                            <Col span={8}><Checkbox value="鼻塞">鼻塞</Checkbox></Col>
                            <Col span={8}><Checkbox value="流涕">流涕</Checkbox></Col>
                            <Col span={8}><Checkbox value="咽痛">咽痛</Checkbox></Col>
                            <Col span={8}><Checkbox value="腹泻">腹泻</Checkbox></Col>
                        </Row>
                    </Checkbox.Group>
                </Form.Item>
                <Divider/>
                <Form.Item rules={[{required: true, message: '未勾选此项'}]}>
                    <Radio.Group>
                        <Radio>本人郑重承诺：填报信息真实，愿意承担相应的法律责任。</Radio>
                    </Radio.Group>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        提交
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
}

export default SubmitDailyCardForm;