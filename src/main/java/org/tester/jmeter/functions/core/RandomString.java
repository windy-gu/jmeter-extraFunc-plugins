package org.tester.jmeter.functions.core;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author smooth00
 * @date 2019-12-02 11:21
 */
public class RandomString {

    /** 普遍随机字符串来源 */
    private static final String[] SOURCE_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split("|");
    /** 中国姓名中姓来源 */
    private static final String[] CHINESE_XING = "赵钱孙李周郑王冯陈楮卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闽席季麻强贾路娄危江童颜郭梅盛林刁锺徐丘骆高夏蔡田樊胡凌霍虞万支柯昝管卢莫经裘缪干解应宗丁宣贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁".split("|");
    /** 中国姓名中名来源 */
    private static final String[] CHINESE_MING = "嘉懿煜城懿轩烨伟苑博伟泽熠彤鸿煊博涛烨霖烨华煜祺智宸正豪昊然明杰诚立轩立辉峻熙弘文熠彤鸿煊烨霖哲瀚鑫鹏致远俊驰雨泽烨磊晟睿天佑文昊修洁黎昕远航旭尧鸿涛伟祺轩越泽浩宇瑾瑜皓轩擎苍擎宇志泽睿渊楷瑞轩弘文哲瀚雨泽鑫磊梦琪忆之桃慕青问兰尔岚元香初夏沛菡傲珊曼文乐菱痴珊恨玉惜文香寒新柔语蓉海安夜蓉涵柏水桃醉蓝春儿语琴从彤傲晴语兰又菱碧彤元霜怜梦紫寒妙彤曼易南莲紫翠雨寒易烟如萱若南寻真晓亦向珊慕灵以蕊寻雁映易雪柳孤岚笑霜海云凝天沛珊寒云冰旋宛儿绿真盼儿晓霜碧凡夏菡曼香若烟半梦雅绿冰蓝灵槐平安书翠翠风香巧代云梦曼幼翠友巧听寒梦柏醉易访旋亦玉凌萱访卉怀亦笑蓝春翠靖柏夜蕾冰夏梦松书雪乐枫念薇靖雁寻春恨山从寒忆香觅波静曼凡旋以亦念露芷蕾千兰新波代真新蕾雁玉冷卉紫山千琴恨天傲芙盼山怀蝶冰兰山柏翠萱乐丹翠柔谷山之瑶冰露尔珍谷雪乐萱涵菡海莲傲蕾青槐冬儿易梦惜雪宛海之柔夏青亦瑶妙菡春竹修杰伟诚建辉晋鹏天磊绍辉泽洋明轩健柏煊昊强伟宸博超君浩子骞明辉鹏涛炎彬鹤轩越彬风华靖琪明诚高格光华国源宇晗昱涵润翰飞翰海昊乾浩博和安弘博鸿朗华奥华灿嘉慕坚秉建明金鑫锦程瑾瑜鹏经赋景同靖琪君昊俊明季同开济凯安康成乐语力勤良哲理群茂彦敏博明达朋义彭泽鹏举濮存溥心璞瑜浦泽奇邃祥荣轩".split("|");
    /** 手机网段，参考 https://wenku.baidu.com/view/e77ff1e65ef7ba0d4a733bf0.html */
    private static final String[] MOBILE_SEGMENT = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "147", "150", "151", "152", "153", "154", "155", "156", "157", "158", "159", "180", "185", "186", "187", "188", "189"};
    /** 常用邮箱后缀 */
    private static final String EMAIL_SUFFIX = "@gmail.com, @yahoo.com, @msn.com, @hotmail.com, @aol.com, @ask.com, @live.com, @qq.com, @0355.net, @163.com, @163.net, @263.net, @3721.net, @yeah.net, @googlemail.com, @126.com, @sina.com, @sohu.com, @yahoo.com.cn";
    /** IP范围 */
    private static final int[][] IP_RANGE = {{607649792, 608174079}, // 36.56.0.0-36.63.255.255
            {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
            {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
            {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
            {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
            {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
            {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
            {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
            {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
            {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
    };

    /** 柬埔寨 可用号段 */
    private static final String[] CAMBODIA_MOBILE_SEGMENT = {
            "11", "12", "14", "17", "61", "76", "77", "78", "79", "85",
            "89", "92", "95", "99", "10", "15", "16", "69", "70", "81",
            "86", "87", "93", "96", "98", "31", "60", "66", "67", "68",
            "71", "88", "90", "97","13", "80", "83", "84", "38", "18" };


    /**
     * 获取0到指定数值间的随机数，内部使用
     * @param max
     * @return
     */
    private static int getRandomNum(int max) {
        if (max < 0) {
            throw new RuntimeException("最大值需要大于或等于0");
        }
        return (int) (Math.random() * max);
    }

    /**
     * 返回[0-9,a-z,A-Z]的随机字符串
     * @param len 字符串长度
     * @return
     */
    public static String getFixed(int len) {
        StringBuilder resBuf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int randIndex = getRandomNum(SOURCE_STRING.length);
            resBuf.append(SOURCE_STRING[randIndex]);
        }
        return resBuf.toString();
    }

    /**
     * 生成随机指定范围的定长字符串
     *
     * @param src 产生随机字符串来源，使用英文逗号分开
     * @param len 返回字符串长度
     * @return
     */
    public static final String getRange(String src, int len) {
        if (src == null || src.length() == 0) {
            throw new RuntimeException("来源字符串为空");
        }
        if (len < 1) {
            throw new RuntimeException("返回字符串长度需要大于0");
        }
        String[] arr = src.split(",");
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int index = getRandomNum(arr.length);
            buf.append(arr[index].trim());
        }
        return buf.toString();
    }

    /**
     * 生成随机中国姓名
     * @return
     */
    public static String getChineseName() {
        int x = getRandomNum(CHINESE_XING.length);
        int m1 = getRandomNum(CHINESE_MING.length);
        if (Math.random() > 0.5) {
            // 一般的概率是两字的姓名
            return CHINESE_XING[x] + CHINESE_MING[m1];
        } else {
            // 一半的概率是三个字的姓名
            int m2 = getRandomNum(CHINESE_MING.length);
            return CHINESE_XING[x] + CHINESE_MING[m1] + CHINESE_MING[m2];
        }
    }

    /**
     * 生成随机手机号码
     * @return
     */
    public static String getMobile() {
        int index = getRandomNum(MOBILE_SEGMENT.length);
        StringBuffer buf = new StringBuffer(MOBILE_SEGMENT[index]);
        final String numbers = "0, 1, 2, 3, 4, 5, 6, 7, 8, 9";
        for (int i = 0; i < 8; i++) {
            buf.append(getRange(numbers, 1));
        }
        return buf.toString();
    }



    /**
     * 生成随机柬埔寨手机号码
     * @return
     */
    public static String getCambodiaMobile() {
        int index = getRandomNum(CAMBODIA_MOBILE_SEGMENT.length);
        StringBuffer buf = new StringBuffer(MOBILE_SEGMENT[index]);
        String temp = buf.toString();
        final String numbers = "0, 1, 2, 3, 4, 5, 6, 7, 8, 9";
        String[] seven = {"76", "96", "31", "71", "88", "97", "38", "18"};
        if (temp.equals("12")){
            // 当号段为12时，后续号码长度为6，7
            final String randomLength = "6, 7";
            int length = Integer.parseInt(getRange(randomLength, 1));
            for (int i = 0; i < length - 1; i++){
                buf.append(getRange(numbers, 1));
            }
            return "8550" + buf.toString();
        }
        else if(Arrays.asList(seven).contains(temp)){
            // 当号段为在seven数据时，后续号码长度为7
            for (int k = 0; k < 6; k++){
                buf.append(getRange(numbers, 1));
            }
            return "8550" + buf.toString();
        }
        else {
            // 其他，后续号码长度为6
            for (int l = 0; l < 5; l++){
                buf.append(getRange(numbers, 1));
            }
            return "8550" + buf.toString();
        }

    }

    /**
     * 生成uuid
     **/
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成随机电子邮箱
     * @return
     */
    public static String getEmail() {
        // 生成用户名 6-16 位长度
        int len = getRandomNum(10) + 6;
        String userName = getFixed(len).toLowerCase();
        String email = getRange(EMAIL_SUFFIX, 1);
        return userName + email;
    }

    /**
     * 生成随机IP地址
     */
    public static String getIp() {
        int i = getRandomNum(IP_RANGE.length);
        int[] ipRange = IP_RANGE[i];
        int ipVal = getRandomNum(ipRange[1] - ipRange[0]) + ipRange[0];
        int ip1 = ((ipVal >> 24) & 0xff);
        int ip2 = ((ipVal >> 16) & 0xff);
        int ip3 = ((ipVal >> 8) & 0xff);
        int ip4 = (ipVal & 0xff);
        return String.format("%d.%d.%d.%d", ip1, ip2, ip3, ip4);
    }

    public static void main(String[] args) throws Exception {
        String temp = RandomString.getCambodiaMobile();
        System.out.println("随机柬埔寨号码:" +temp);
        String str = RandomString.getFixed(6);
        System.out.println("随机普通字符串:" + str);
        str = RandomString.getRange("999001, 999002, 999003", 1);
        System.out.println("随机指定范围字符串：" + str);
        str = RandomString.getChineseName();
        System.out.println("随机中国姓名：" + str);
        str = RandomString.getMobile();
        System.out.println("随机手机号：" + str);
        str = RandomString.getEmail();
        System.out.println("随机邮箱：" + str);
        str = RandomString.getIp();
        System.out.println("随机IP：" + str);
    }

}