package com.dwk.redis.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/12 10:40
 */
public class CheckCodeUtil {

    /**验证码*/
    private String CHECK = "";

    /**排序数组*/
    List<Integer> reuse = new ArrayList<>();

    private static final char[] charCode = {'1','2','3','4','5','6','7','8','9','0',
                                            'a','b','c','d','e','f','g','h','i','j',
                                            'k','l','m','n','o','p','q','r','s','t',
                                            'u','v','w','x','y','z','A','B','C','D',
                                            'E','F','G','H','I','J','K','L','M','N',
                                            'O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    /**num:验证码位数*/
    public String createCheckCode(int num){

        char[] result = new char[num];

        for (int k = 0;k<num;k++){
            int charNum = new Random().nextInt(charCode.length);
            char en = charCode[charNum];
            int randow = getRandow(num);
            result[randow] = en;
        }

        for (int i = 0;i<result.length;i++){
            CHECK += String.valueOf(result[i]);
        }

        return CHECK;
    }

    /**排位*/
    public int getRandow(int round){
        int checkNum = new Random().nextInt(round);

        if (!reuse.contains(checkNum)){
            reuse.add(checkNum);
        }else{
            return this.getRandow(round);
        }
        return checkNum;

    }

}
