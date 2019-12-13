package com.xcuni.guizhouyl.utils;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 身份证算法实现
 * <p>
 * 1、号码的结构 公民身份号码是特征组合码，
 * 由十七位数字本体码和一位校验码组成。
 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码  三位数字顺序码和一位数字校验码。
 * <p>
 * 2、地址码(前六位数） 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
 * <p>
 * 3、出生日期码（第七位至十四位） 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * <p>
 * 4、顺序码（第十五位至十七位）
 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
 * <p>
 * 5、校验码（第十八位数）
 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16
 * ，先对前17位数字的权求和
 * Ai:表示第i位置上的身份证号码数字值
 * Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 * （2）计算模 Y = mod(S, 11)
 * （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7  8 9 10
 * 校验码: 1 0 X 9 8 7 6 5 4 3 2
 *
 * @author longgangbai
 */
public class IdCardGenerator {

    public static final Map<String, Integer> areaCode = new HashMap<String, Integer>();
    public static final Map<String, String> areaCodeStr = new HashMap<String, String>();

    static {

//        IdCardGenerator.areaCode.put("贵州省", 520000);
//        IdCardGenerator.areaCode.put("贵阳市", 520100);
        IdCardGenerator.areaCode.put("贵阳市", 520101);
        IdCardGenerator.areaCode.put("南明区", 520102);
        IdCardGenerator.areaCode.put("云岩区", 520103);
        IdCardGenerator.areaCode.put("花溪区", 520111);
        IdCardGenerator.areaCode.put("乌当区", 520112);
        IdCardGenerator.areaCode.put("白云区", 520113);
        IdCardGenerator.areaCode.put("小河区", 520114);
        IdCardGenerator.areaCode.put("开阳县", 520121);
        IdCardGenerator.areaCode.put("息烽县", 520122);
        IdCardGenerator.areaCode.put("修文县", 520123);
        IdCardGenerator.areaCode.put("清镇市", 520181);
//        IdCardGenerator.areaCode.put("六盘水市", 520200);
        IdCardGenerator.areaCode.put("钟山区", 520201);
        IdCardGenerator.areaCode.put("六枝特区", 520203);
        IdCardGenerator.areaCode.put("水城县", 520221);
        IdCardGenerator.areaCode.put("盘　县", 520222);
//        IdCardGenerator.areaCode.put("遵义市", 520300);
        IdCardGenerator.areaCode.put("遵义市", 520301);
        IdCardGenerator.areaCode.put("红花岗区", 520302);
        IdCardGenerator.areaCode.put("汇川区", 520303);
        IdCardGenerator.areaCode.put("遵义县", 520321);
        IdCardGenerator.areaCode.put("桐梓县", 520322);
        IdCardGenerator.areaCode.put("绥阳县", 520323);
        IdCardGenerator.areaCode.put("正安县", 520324);
        IdCardGenerator.areaCode.put("道真仡佬族苗族自治县", 520325);
        IdCardGenerator.areaCode.put("务川仡佬族苗族自治县", 520326);
        IdCardGenerator.areaCode.put("凤冈县", 520327);
        IdCardGenerator.areaCode.put("湄潭县", 520328);
        IdCardGenerator.areaCode.put("余庆县", 520329);
        IdCardGenerator.areaCode.put("习水县", 520330);
        IdCardGenerator.areaCode.put("赤水市", 520381);
        IdCardGenerator.areaCode.put("仁怀市", 520382);
//        IdCardGenerator.areaCode.put("安顺市", 520400);
        IdCardGenerator.areaCode.put("安顺市", 520401);
        IdCardGenerator.areaCode.put("西秀区", 520402);
        IdCardGenerator.areaCode.put("平坝县", 520421);
        IdCardGenerator.areaCode.put("普定县", 520422);
        IdCardGenerator.areaCode.put("镇宁布依族苗族自治县", 520423);
        IdCardGenerator.areaCode.put("关岭布依族苗族自治县", 520424);
        IdCardGenerator.areaCode.put("紫云苗族布依族自治县", 520425);
//        IdCardGenerator.areaCode.put("铜仁地区", 522200);
        IdCardGenerator.areaCode.put("铜仁市", 522201);
        IdCardGenerator.areaCode.put("江口县", 522222);
        IdCardGenerator.areaCode.put("玉屏侗族自治县", 522223);
        IdCardGenerator.areaCode.put("石阡县", 522224);
        IdCardGenerator.areaCode.put("思南县", 522225);
        IdCardGenerator.areaCode.put("印江土家族苗族自治县", 522226);
        IdCardGenerator.areaCode.put("德江县", 522227);
        IdCardGenerator.areaCode.put("沿河土家族自治县", 522228);
        IdCardGenerator.areaCode.put("松桃苗族自治县", 522229);
        IdCardGenerator.areaCode.put("万山特区", 522230);
//        IdCardGenerator.areaCode.put("黔西南布依族苗族自治州", 522300);
        IdCardGenerator.areaCode.put("兴义市", 522301);
        IdCardGenerator.areaCode.put("兴仁县", 522322);
        IdCardGenerator.areaCode.put("普安县", 522323);
        IdCardGenerator.areaCode.put("晴隆县", 522324);
        IdCardGenerator.areaCode.put("贞丰县", 522325);
        IdCardGenerator.areaCode.put("望谟县", 522326);
        IdCardGenerator.areaCode.put("册亨县", 522327);
        IdCardGenerator.areaCode.put("安龙县", 522328);
//        IdCardGenerator.areaCode.put("毕节地区", 522400);
        IdCardGenerator.areaCode.put("毕节市", 522401);
        IdCardGenerator.areaCode.put("大方县", 522422);
        IdCardGenerator.areaCode.put("黔西县", 522423);
        IdCardGenerator.areaCode.put("金沙县", 522424);
        IdCardGenerator.areaCode.put("织金县", 522425);
        IdCardGenerator.areaCode.put("纳雍县", 522426);
        IdCardGenerator.areaCode.put("威宁彝族回族苗族自治县", 522427);
        IdCardGenerator.areaCode.put("赫章县", 522428);
//        IdCardGenerator.areaCode.put("黔东南苗族侗族自治州", 522600);
        IdCardGenerator.areaCode.put("凯里市", 522601);
        IdCardGenerator.areaCode.put("黄平县", 522622);
        IdCardGenerator.areaCode.put("施秉县", 522623);
        IdCardGenerator.areaCode.put("三穗县", 522624);
        IdCardGenerator.areaCode.put("镇远县", 522625);
        IdCardGenerator.areaCode.put("岑巩县", 522626);
        IdCardGenerator.areaCode.put("天柱县", 522627);
        IdCardGenerator.areaCode.put("锦屏县", 522628);
        IdCardGenerator.areaCode.put("剑河县", 522629);
        IdCardGenerator.areaCode.put("台江县", 522630);
        IdCardGenerator.areaCode.put("黎平县", 522631);
        IdCardGenerator.areaCode.put("榕江县", 522632);
        IdCardGenerator.areaCode.put("从江县", 522633);
        IdCardGenerator.areaCode.put("雷山县", 522634);
        IdCardGenerator.areaCode.put("麻江县", 522635);
        IdCardGenerator.areaCode.put("丹寨县", 522636);
//        IdCardGenerator.areaCode.put("黔南布依族苗族自治州", 522700);
        IdCardGenerator.areaCode.put("都匀市", 522701);
        IdCardGenerator.areaCode.put("福泉市", 522702);
        IdCardGenerator.areaCode.put("荔波县", 522722);
        IdCardGenerator.areaCode.put("贵定县", 522723);
        IdCardGenerator.areaCode.put("瓮安县", 522725);
        IdCardGenerator.areaCode.put("独山县", 522726);
        IdCardGenerator.areaCode.put("平塘县", 522727);
        IdCardGenerator.areaCode.put("罗甸县", 522728);
        IdCardGenerator.areaCode.put("长顺县", 522729);
        IdCardGenerator.areaCode.put("龙里县", 522730);
        IdCardGenerator.areaCode.put("惠水县", 522731);
        IdCardGenerator.areaCode.put("三都水族自治县", 522732);
//
    }

    public String generate() {
        StringBuilder generater = new StringBuilder();
        generater.append(this.randomAreaCode());
        generater.append(this.randomBirthday());
        generater.append(this.randomCode());
        generater.append(this.calcTrailingNumber(generater.toString().toCharArray()));
        return generater.toString();
    }

    public String generatePerLocation(String location) {
        StringBuilder generater = new StringBuilder();
        generater.append(this.randomAreaCodePerLocation(location));
        generater.append(this.randomBirthday());
        generater.append(this.randomCode());
        generater.append(this.calcTrailingNumber(generater.toString().toCharArray()));
        return generater.toString();
    }

    public String getCityByCode(String code) {
        HashMap<String, String> codeLocMap = new HashMap<>();
        Collection<String> locs = areaCode.keySet();
        Iterator<String> itStr = locs.iterator();
        while (itStr.hasNext()) {
            String locStr = itStr.next();
            int codeInt = areaCode.get(locStr);
            codeLocMap.put(String.valueOf(codeInt), locStr);
        }

        String name = "";
        if (codeLocMap.containsKey(code))
            name = codeLocMap.get(code);

        return name;
    }


    public int randomAreaCodePerLocation(String location) {
        HashMap<String, Integer> locAreaCode = new HashMap<>();
        Collection<String> locs = areaCode.keySet();
        Iterator<String> itStr = locs.iterator();
        while (itStr.hasNext()) {
            int code = areaCode.get(itStr.next());
            String codeStr = String.valueOf(code);
            if (codeStr.startsWith(location)) {
                locAreaCode.put(itStr.next(), code);
            }
        }

        int index = (int) (Math.random() * locAreaCode.size());
        Collection<Integer> values = locAreaCode.values();
        Iterator<Integer> it = values.iterator();
        int i = 0;
        int code = 0;
        while (i <= index && it.hasNext()) {
            i++;
            code = it.next();
        }
        return code;

    }

    public int randomAreaCode() {
        int index = (int) (Math.random() * IdCardGenerator.areaCode.size());
        Collection<Integer> values = IdCardGenerator.areaCode.values();
        Iterator<Integer> it = values.iterator();
        int i = 0;
        int code = 0;
        while (i <= index && it.hasNext()) {
            i++;
            code = it.next();
        }
        return code;
    }

    public String randomBirthday() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(Calendar.YEAR, (int) (Math.random() * 25) + 1930);
        birthday.set(Calendar.MONTH, (int) (Math.random() * 12));
        birthday.set(Calendar.DATE, (int) (Math.random() * 31));

        StringBuilder builder = new StringBuilder();
        builder.append(birthday.get(Calendar.YEAR));
        long month = birthday.get(Calendar.MONTH) + 1;
        if (month < 10) {
            builder.append("0");
        }
        builder.append(month);
        long date = birthday.get(Calendar.DATE);
        if (date < 10) {
            builder.append("0");
        }
        builder.append(date);
        return builder.toString();
    }

    /*
     * <p>18位身份证验证</p>
     * 根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 第十八位数字(校验码)的计算方法为：
     * 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 2.将这17位数字和系数相乘的结果相加。
     * 3.用加出来和除以11，看余数是多少？
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     */
    public char calcTrailingNumber(char[] chars) {
        if (chars.length < 17) {
            return ' ';
        }
        int[] c = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] r = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int[] n = new int[17];
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(chars[i] + "");
        }
        for (int i = 0; i < n.length; i++) {
            result += c[i] * n[i];
        }
        return r[result % 11];
    }

    public String randomCode() {
        int code = (int) (Math.random() * 1000);
        if (code < 10) {
            return "00" + code;
        } else if (code < 100) {
            return "0" + code;
        } else {
            return "" + code;
        }
    }

    public static void main(String[] args) {
        IdCardGenerator g = new IdCardGenerator();
        String idcard = g.generate();
        System.out.println(idcard);
//        for (int i = 0; i < 10; i++) {
//            System.out.print(IdcardUtils.validateCard(g.generate()));
//            System.out.print("\t");
//            System.out.print(g.generate());
//            System.out.print("\t");
//            System.out.print(g.generate());
//            System.out.print("\t");
//            System.out.println(g.generate());
//        }
    }
}
