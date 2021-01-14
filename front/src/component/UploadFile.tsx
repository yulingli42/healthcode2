import {message} from "antd";
import {InboxOutlined} from "@ant-design/icons";
import React from "react";
import Dragger from "antd/es/upload/Dragger";

interface UploadFileProps {
    onSuccess: () => void,
    url: string
}

const UploadFile: React.FC<UploadFileProps> = ({onSuccess, url}) => {
    return (
        <Dragger
            name="file"
            fileList={[]}
            action={url}
            onChange={(info) => {
                if (info.file.status === "done") {
                    message.info("导入成功")
                    onSuccess()
                }else if (info.file.status==="error"){
                    message.warn(info.file.response.message)
                }
            }}>
            <p className="ant-upload-drag-icon">
                <InboxOutlined/>
            </p>
            <p className="ant-upload-text">从excel中导入</p>
            <p className="ant-upload-hint">点击或拖拽文件上传</p>
        </Dragger>
    )
}

export default UploadFile;