import styled from "styled-components";
import Text from "antd/es/typography/Text";
import {Button} from "antd";

export const TopWrapper = styled.div`
  z-index: 1;
  position: relative;
  color: white;
  background: white;
  height: 56px;
  border-bottom: 1px solid #f0f0f0;
`

export const LogoutWrapper = styled(Button)`
  margin-top: 10px;
  margin-right: 30%;
  float: right;
`

export const NameWrapper=styled(Text)`
  margin-top: 15px;
  margin-left: 30%;
  float: left;
`

export const ChangePasswordWrapper=styled(Button)`
  margin-top: 10px;
  float: right;
`
