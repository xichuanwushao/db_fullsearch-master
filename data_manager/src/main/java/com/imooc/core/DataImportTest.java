package com.imooc.core;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 通过接口获取文章数据，入库HBase和Redis（Rowkey）
 *
 * 注意：HBase建表语句 create 'article','info'
 * Created by xuwei
 */
public class DataImportTest {
    private final static Logger logger = LoggerFactory.getLogger(DataImportTest.class);

    public static void main(String[] args) throws IOException {
        //通过接口获取文章数据
//        String dataUrl = "http://data.xuwei.tech/a1/wz1";
//        JSONObject paramObj = new JSONObject();
//        paramObj.put("code","imooc");//校验码
//        paramObj.put("num",100);//数据条数，默认返回100条，最大支持返回1000条
//        JSONObject dataObj = HttpUtil.doPost(dataUrl, paramObj);
//        System.out.println(dataObj.toJSONString());

        String str = ResourceUtil.readUtf8Str("腾讯新闻.json");
//        System.out.println(str);
        JSONObject json = JSONObject.parseObject(str);
        System.out.println(json.toJSONString());
    }
}
