import {Button, message, Upload} from "antd";
import {UploadOutlined} from "@ant-design/icons";
import React from "react";

interface UploadFileProps {
    onSuccess: () => void,
    url: string
}

const UploadFile: React.FC<UploadFileProps> = ({onSuccess,url}) => {
    return (
        <div>
            <Upload
                name="file"
                fileList={[]}
                action={url}
                onChange={(info) => {
                    if (info.file.status === "done") {
                        message.info("导入成功")
                        onSuccess()
                    }
                }}>
                <Button icon={<UploadOutlined/>}>从 excel 导入</Button>
            </Upload>
        </div>
    )
}

export default UploadFile;