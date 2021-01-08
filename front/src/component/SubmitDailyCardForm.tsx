import React from "react";
import {Button, Checkbox, Col, Form, Radio, Row} from "antd";

interface FormProps {
    onFinish: (haveBeenToKeyEpidemicAreas: boolean,
               haveBeenAbroad: boolean,
               isTheExposed: boolean,
               isSuspectedCase: boolean,
               currentSymptoms: Array<string>) => void
}

const SubmitDailyCardForm: React.FC<FormProps> = ({onFinish}) => {

    const finish = (values: any) => {
        const haveBeenToKeyEpidemicAreas = values.haveBeenToKeyEpidemicAreas != null
        const haveBeenAbroad = values.haveBeenAbroad != null
        const isTheExposed = values.isTheExposed != null
        const isSuspectedCase = values.isSuspectedCase != null
        const currentSymptoms = values.currentSymptoms
        onFinish(haveBeenToKeyEpidemicAreas, haveBeenAbroad, isTheExposed, isSuspectedCase, currentSymptoms)
    }

    return (
        <div>
            <Form onFinish={finish}>
                <Form.Item name="haveBeenToKeyEpidemicAreas">
                    <Checkbox.Group>
                        <Checkbox value="true">
                            近期（14天内）去过湖北省或重点疫区
                        </Checkbox>
                    </Checkbox.Group>
                </Form.Item>
                <Form.Item name="haveBeenAbroad">
                    <Checkbox.Group>
                        <Checkbox value="true">
                            近期（14天内）去过国外
                        </Checkbox>
                    </Checkbox.Group>
                </Form.Item>
                <Form.Item name="isTheExposed">
                    <Checkbox.Group>
                        <Checkbox value="true">
                            近期（14天内）接触过新冠确诊病人或疑似病人
                        </Checkbox>
                    </Checkbox.Group>
                </Form.Item>
                <Form.Item name="isSuspectedCase">
                    <Checkbox.Group>
                        <Checkbox value="true">
                            被卫生部门确认为新冠肺炎确诊病例或疑似病例
                        </Checkbox>
                    </Checkbox.Group>
                </Form.Item>
                <Form.Item name="currentSymptoms">
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