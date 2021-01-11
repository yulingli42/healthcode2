export enum HealthCodeType {
    GREEN = "GREEN",
    YELLOW = "YELLOW",
    RED = "RED"
}

export const healthCodeName = (type?: HealthCodeType): string => {
    switch (type) {
        case null:
            return "未填报"
        case HealthCodeType.GREEN:
            return "绿码"
        case HealthCodeType.RED:
            return "红码"
        case HealthCodeType.YELLOW:
            return "黄码"
        default:
            return "未填报"
    }
}