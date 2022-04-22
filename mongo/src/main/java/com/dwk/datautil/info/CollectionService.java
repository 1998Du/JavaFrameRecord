package com.dwk.datautil.info;

import java.util.List;
import java.util.Map;

public interface CollectionService {

    /**查找独有的文档索引*/
    Map<String,List<String>> findAloneIndex();

    /**查找共有的索引*/
    List<String> findSameIndex();

    /**
     * 遍历共有文档，获取所有内容不相同的文档   （通过字符串比对）
     * @return
     */
    List<String> getDiffDocByString();

    /**
     * 遍历共有文档，获取所有内容不相同的文档   （对象接收文档，比对属性）
     * @return
     */
    List<String> getDiffDocByProperty();


}
