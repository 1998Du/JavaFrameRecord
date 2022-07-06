package com.dwk.netty.unpooleddemo;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Unpooled - netty提供的操作缓冲区的工具类
 */
public class UnpooledDemo {

    public static void main(String[] args) {
        test();
    }

    public static void test(){
        /*
        创建ByteBuf
        底层就是一个byte数组  initialCapacity 表示大小
        netty的ByteBuf中不用使用flip重置，底层维护了readIndex和writeIndex
        通过readIndex、writeIndex和capacity将buffer分成三个区域
            0 - readIndex 已经读取的区域
            readIndex - writeIndex 可读的区域
            writeIndex - capacity 可写的区域
         */
        ByteBuf buf = Unpooled.buffer(10);

        for (int i = 0; i<10; i++){
            // writeByte 自动将参数强转成byte
            buf.writeByte(i);
        }

        // 输出
        for (int i = 0; i<buf.capacity(); i++){
            buf.readByte();
        }

    }

    public static void test02(){
        ByteBuf buf = Unpooled.copiedBuffer("UnpooledTest", CharsetUtil.UTF_8);
        if (buf.hasArray()){
            byte[] array = buf.array();
            String str = new String(array, Charset.forName("utf-8"));

            buf.arrayOffset();
            buf.readerIndex();
            buf.writerIndex();
            buf.capacity();

            // 可读的字节数
            buf.readableBytes();

            // 获取固定区间内的数据 i：开始位置 i1：读取长度
            buf.getCharSequence(0,4,Charset.forName("utf-8"));

        }
    }

}
