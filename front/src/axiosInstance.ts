import axios, {AxiosError} from 'axios'
import {message} from "antd";
import qs from 'qs';

const instance = axios.create({
    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
})

instance.interceptors.request.use(
    config => {
        // 转为formdata数据格式
        config.data = qs.stringify(config.data)
        return config
    }
)
instance.interceptors.response.use(config => config, error => {
        const ex: AxiosError = error;
        if (ex.response != null) {
            message.warn(ex.response.data.message)
        }
        return Promise.reject(error);
    }
)
;

export default instance;