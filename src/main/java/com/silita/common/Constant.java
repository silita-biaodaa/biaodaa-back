package com.silita.common;

/**
 * 公告常量类
 */
public class Constant {

    /**
     * 资质等级父级
     **/
    public static String QUAL_LEVEL_PARENT = "1";

    /**
     * 资质等级子级
     **/
    public static String QUAL_LEVEL_SUB = "2";

    /**
     * 资质类型:0:全部
     */
    public static String BIZ_TYPE_ALL = "0";

    /**
     * 资质类型:1:公告
     */
    public static String BIZ_TYPE_NOTIC = "1";

    /**
     * 资质类型:0:公司
     */
    public static String BIZ_TYPE_COMPANY = "2";

    /**
     * 标准名称类型2：公共词典
     */
    public static String PUBLIC_DICTIONARY = "2";

    /**
     * 资质等级type
     */
    public static String TYPE_QUA_GRADE = "qua_grade";

    /**
     * 操作成功code
     */
    public static Integer CODE_SUCCESS = 1;

    /**
     * 操作成功msg
     */
    public static String MSG_SUCCESS = "操作成功";

    /**
     * 信息已存在code
     */
    public static Integer CODE_WARN_400 = 400;

    /**
     * 信息已存在msg
     */
    public static String MSG_WARN_400 = "信息已存在!";

    /**
     * 标准名称类型（1：资质 2：公共词典 3: 等级）
     */
    public static String GRADE_STD_TYPE = "3";

    /**
     * 用户未登录code
     */
    public static Integer CODE_WARN_401 = 401;

    /**
     * 用户未登录msg
     */
    public static String MSG_WARN_401 = "用户未登录!";

    /**
     * 无效token code
     */
    public static Integer CODE_WARN_402 = 402;

    /**
     * 无效tokenmsg
     */
    public static String MSG_WARN_402 = "无效token，请重新登录!";

    /**
     * 没有权限 code
     */
    public static Integer CODE_WARN_403 = 403;

    /**
     * 没有权限msg
     */
    public static String MSG_WARN_403 = "没有权限!";

    /**
     * 没有找到 code
     */
    public static Integer CODE_WARN_404 = 404;

    /**
     * 没有找到 msg
     */
    public static String MSG_WARN_404 = "数据为空!";

    /**
     * 格式错误 code
     */
    public static Integer CODE_WARN_405 = 405;

    /**
     * 格式错误 msg
     */
    public static String MSG_WARN_405 = "格式错误!";

    /**
     * 参数错误 code
     */
    public static Integer CODE_WARN_406 = 406;

    /**
     * 参数错误 msg
     */
    public static String MSG_WARN_406 = "参数错误!";

    /**
     * 接口异常 code
     */
    public static Integer CODE_ERROR_500 = 500;

    /**
     * 接口异常 msg
     */
    public static String MSG_ERROR_500 = "接口异常!";

    /**
     * 无法删除，请检查该信息的关联关系 Code
     */
    public static Integer CODE_WARN_407 = 407;

    /**
     * 无法删除，请检查该信息的关联关系 msg
     */
    public static String MSG_WARN_407 = "无法删除，请检查该信息的关联关系!";

    /**
     * 数据状态 0:未抓取
     */
    public static String DATA_STATUS_0 = "0";

    /**
     * 数据状态 1:已抓取
     */
    public static String DATA_STATUS_1 = "1";

    /**
     * 来源(程序)
     */
    public static String SOURCE_PRO = "1";

    /**
     * 来源(人工)
     */
    public static String SOURCE_LAB = "2";

    /**
     * 手机号不存在
     */
    public static String CODE_PHONE = "0";
    /**
     * 手机号不存在
     */
    public static String MSG_PHONE ="手机号不存在";

    /**
     * 手机号不存在
     */
    public static String CODE_PHONES = "0";
    /**
     * 手机号不存在
     */
    public static String MSG_PHONES ="手机号已存在";

    /**
     * 原密码错误
     */
    public static String CODE_PASSWORD = "0";

    public static String MSG_DESC = "角色已存在";

    public static  String CODE_DESC = "0";
    /**
     * 原密码错误
     */
    public static String MSG_PASSWORD ="原密码错误";

    public static String MSG_MISTAKE = "手机号或密码错误";

}
