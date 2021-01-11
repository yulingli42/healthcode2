import {Action} from "redux";
import {LoginUser} from "../../entity/LoginUser";

export const SetLoginStatus = "set_login_status"

interface LoginAction extends Action {
    data: LoginUser
}

const loginReducer = (state: LoginUser = {login: false} as LoginUser, action: LoginAction) => {
    switch (action.type) {
        case SetLoginStatus:
            state = action.data
            return state
        default:
            return state
    }
}


export default loginReducer