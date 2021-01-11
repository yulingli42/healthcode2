import {ThunkAction} from "redux-thunk";
import {Action, Dispatch} from "redux";
import {RootState} from "../../store";
import {SetLoginStatus} from "./loginReducer";
import {LoginUser} from "../../entity/LoginUser";

export const setUser = (user: LoginUser = {login: false} as LoginUser): ThunkAction<void, RootState, unknown, Action> => {
    return (dispatch: Dispatch) => {
        dispatch({
            type: SetLoginStatus,
            data: user
        })
    }
}