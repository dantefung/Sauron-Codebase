// 定义因子
var
    RESIDE_CITY, // 居住地
    HAS_DRIVER_LICENSE, // 是否有 C 以上驾照
    HAS_CAR, // 是否有本地小汽车
    o;

// 定义规则函数
function doRool() {
    if (RESIDE_CITY != '440300') {
        return "非本地居住";
    }

    if (HAS_DRIVER_LICENSE != 'Y') {
        return "无 C 以上驾照";
    }

    if (HAS_CAR != 'N') {
        return "本地已有小汽车";
    }

    return ""; // 没有被拦截。
}

// 执行规则
doRool();