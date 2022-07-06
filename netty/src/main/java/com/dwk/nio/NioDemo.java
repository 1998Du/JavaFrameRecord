package com.dwk.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.Set;

/**
 * nio 案例实现
 */
public class NioDemo {

    public static void main(String[] args) {
        //buffer的基本使用
        //intBuffTest(5);

        //channel的基本使用
        //channelTest();

        //文件拷贝
        //transferFormTest("","");

        //buffer的存取
        //bufferPutGet();

        //buffer的只读
        //readOnlyBuffer();

        //使用byteBuffer数组读取进行数据交互
        bufferArray();

        //零拷贝
        ZeroCopy.main(args);
    }

    public static void intBuffTest(int capacity) {
        //创建一个容量大小为capacity的buff
        IntBuffer allocate = IntBuffer.allocate(capacity);
        for (int i = 0; i < capacity; i++) {
            allocate.put(i * 2);
        }
        //将buffer切换，读写切换  处理buffer内的标记
        allocate.flip();
        //指定开始读取的位置
        //allocate.position(1);
        //指定结束读取的位置
        //allocate.limit(2);
        while (allocate.hasRemaining()){
            System.out.println(allocate.get());
        }
    }

    public static void channelTest(){
        //DatagramChannel  用于UDP数据的读写，ServerSocketChannel/SocketChannel用于TCP的数据读写

        //文件写通道
        fileChannelWriteTest();
        //文件读通道
        fileChannelReadTest();
        //使用一个通道完成文件的读写 - 文件拷贝
        fileChannelWriteAndReadTest();

    }

    /**
     * 文件写入
     */
    public static void fileChannelWriteTest(){
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannel = null;
        ByteBuffer byteBuffer;
        try {
            String str = "fileChannelTest";
            fileOutputStream = new FileOutputStream("C:\\duwk\\code\\myself\\frame-master\\netty\\src\\main\\resources\\file\\FileChannel.txt");
            //获取通道
            fileChannel = fileOutputStream.getChannel();
            //创建缓冲区
            byteBuffer = ByteBuffer.allocate(1024);
            //写入缓冲区
            byteBuffer.put(str.getBytes("UTF-8"));
            //缓冲区索引重置
            byteBuffer.flip();
            //缓冲区数据写入通道
            fileChannel.write(byteBuffer);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                fileOutputStream.close();
                fileChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 文件读取
     */
    public static void fileChannelReadTest(){
        FileInputStream fileInputStream = null;
        ByteBuffer byteBuffer = null;
        FileChannel channel = null;
        try {
            String filePath = "C:\\duwk\\code\\myself\\frame-master\\netty\\src\\main\\resources\\file\\FileChannel.txt";
            File file = new File(filePath);
            fileInputStream = new FileInputStream(file);
            //通道读取文件
            channel = fileInputStream.getChannel();
            //缓冲区读取通道
            byteBuffer = ByteBuffer.allocate((int) file.length());
            channel.read(byteBuffer);
            byteBuffer.flip();
            //缓冲区数据输出
            System.out.println(new String(byteBuffer.array(),"UTF-8"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                fileInputStream.close();
                channel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 文件拷贝
     */
    public static void fileChannelWriteAndReadTest(){
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        ByteBuffer byteBuffer = null;
        try {
            String fileName = "C:\\duwk\\code\\myself\\frame-master\\netty\\src\\main\\resources\\file\\FileChannel.txt";
            String targetFileName = "C:\\duwk\\code\\myself\\frame-master\\netty\\src\\main\\resources\\file\\FileChannel副本.txt";
            File file = new File(fileName);
            File fileClone = new File(targetFileName);
            if (fileClone.exists()) {
                fileClone.delete();
                fileChannelWriteAndReadTest();
            }
            inputStream = new FileInputStream(file);
            //读取源文件流到通道
            FileChannel inChannel = inputStream.getChannel();
            //通道中的数据流写入到缓冲区
            byteBuffer = ByteBuffer.allocate(1024);
            inChannel.read(byteBuffer);
            byteBuffer.flip();
            //将缓冲区中的数据流写入到另一个通道
            outputStream = new FileOutputStream(fileClone);
            FileChannel outChannel = outputStream.getChannel();
            outChannel.write(byteBuffer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * buffer的类型存取  按顺序存取
     */
    public static void bufferPutGet(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.putInt(1);
        byteBuffer.putLong(1L);
        byteBuffer.putChar('A');
        byteBuffer.putShort((short) 1);

        byteBuffer.flip();

        //正常取出
        int anInt = byteBuffer.getInt();
        long aLong = byteBuffer.getLong();
        char aChar = byteBuffer.getChar();
        short aShort = byteBuffer.getShort();

        System.out.println(anInt);
        System.out.println(aLong);
        System.out.println(aChar);
        System.out.println(aShort);

        System.out.println("======================");

        //乱序取出  有异常
        short bShort = byteBuffer.getShort();
        char bChar = byteBuffer.getChar();
        long bLong = byteBuffer.getLong();
        int bnInt = byteBuffer.getInt();

        System.out.println(bnInt);
        System.out.println(bLong);
        System.out.println(bChar);
        System.out.println(bShort);
    }

    /**
     * 设置buffer只读
     */
    public static void readOnlyBuffer(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(1);
        //设置只读
        byteBuffer.asReadOnlyBuffer();
        int anInt = byteBuffer.getInt();
        System.out.println("buffer只读 ==>" + anInt);
    }

    /**
     * 使用通道的transferFrom方法拷贝文件
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     */
    public static void transferFormTest(String sourcePath,String targetPath){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inputStreamChannel = null;
        FileChannel outputStreamChannel = null;
        try {
            //创建文件流
            inputStream = new FileInputStream(sourcePath);
            outputStream = new FileOutputStream(targetPath);
            //信道
            inputStreamChannel = inputStream.getChannel();
            outputStreamChannel = outputStream.getChannel();
            //拷贝  参数：src = 源通道   position = 文件内开始转移的位置，必须是非负数   count = 最大的转换字节数，必须非负数
            outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                //关闭通道和流
                inputStreamChannel.close();
                outputStreamChannel.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * mappedByteBuffer - 可以让文件直接在内存(堆外内存)中进行修改，操作系统不必拷贝一次  NIO同步到文件
     * 相当于直接操作源文件，性能高，但是不安全
     */
    public static void mappedByteBufferTest(){
        RandomAccessFile randomAccessFile = null;
        try {
            //name : 文件名  mode：模式（r，rw，rws，rwd）
            randomAccessFile = new RandomAccessFile("", "");
            //获取通道
            FileChannel channel = randomAccessFile.getChannel();
            //MapMode mode   模式, long position  可以直接修改的起始位置, long size   映射到内存的大小(不是索引位置)即文件的多少个字节映射到内存
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            //修改文件
            map.put(0,(byte) 'A');
            map.put(10,(byte) 'B');
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * buffer的分散和聚集
     * buffer数组操作   Scattering:将数据写入到buffer时可以采用buffer数组依次写入  Gathering：从buffer读取数据时依次读取buffer数组
     */
    public static void bufferArray(){
        //服务端通道
        ServerSocketChannel serverSocketChannel = null;
        //客户端通道
        SocketChannel socketChannel = null;
        try {
            //创建服务端通道
            serverSocketChannel = ServerSocketChannel.open();
            //指定端口
            InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
            //绑定端口并启动
            serverSocketChannel.socket().bind(inetSocketAddress);
            //服务端的buffer数组
            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            //初始化buffer大小
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);
            //等待客户端连接
            socketChannel = serverSocketChannel.accept();
            //每次从客户端通道读取的字节数
            int countByte = 8;
            //获取客户端发送的数据,,循环读取
            while (true){
                //统计读取的字节数
                int byteRead = 0;
                while (byteRead < countByte){
                    //从客户端通道读取字节到buffer数组
                    long read = socketChannel.read(byteBuffers);
                    byteRead += read;
                    System.out.println("累计读取的字节:" + byteRead);
                    Arrays.stream(byteBuffers).map(buffer -> "position = " + buffer.position() + "    limit = " + buffer.limit()).forEach(System.out::println);
                }
                //将所有的buffer反转
                Arrays.stream(byteBuffers).forEach(buffer -> {buffer.flip();});
                //将数据读出显示到客户端
                long byteWrite = 0;
                while (byteWrite < countByte){
                    long writeByte = socketChannel.write(byteBuffers);
                    byteWrite += writeByte;
                }
                //将所有的buffer清除
                Arrays.stream(byteBuffers).forEach(buffer -> {buffer.clear();});

                System.out.println("byteRead : " + byteRead + "  byteWrite : " + byteWrite + "  byteCount : " + countByte);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                serverSocketChannel.close();
                socketChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

/**
 * 零拷贝
 */
class ZeroCopy {
    public static void main(String[] args) {
        //传统IO
        oldIO();
        //NIO 零拷贝
        newIO();
    }

    public static void oldIO(){
        Thread thread = new Thread(() -> {
            oldServer();
        });
        Thread thread1 = new Thread(() -> {
            oldClient();
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread1.start();
    }

    public static void newIO(){
        Thread thread = new Thread(() -> {
            newServer();
        });
        Thread thread1 = new Thread(() -> {
            newClkient();
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread1.start();
    }

    /**
     * 传统IO服务端
     */
    public static void oldServer(){
        try {
            ServerSocket server = new ServerSocket(6666);
            //等待客户端连接
            while (true){
                Socket client = server.accept();
                //获取连接的客户端的数据
                InputStream inputStream = client.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                //读取
                byte[] bytes = new byte[4096];
                while (true){
                    int read = dataInputStream.read(bytes, 0, bytes.length);
                    if (read == -1){
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 传统IO客户端
     */
    public static void oldClient(){
        Socket socket = null;
        FileInputStream inputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            socket = new Socket("127.0.0.1", 6666);
            //需要发送的文件
            String fileName = "C:\\duwk\\code\\myself\\frame-master\\netty\\src\\main\\resources\\file\\jdk1.8.0_51.zip";
            inputStream = new FileInputStream(fileName);
            //socket输出流
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //文件存入byte数组并写入socket的输出流
            byte[] bytes = new byte[4096];
            long readCount;
            long total = 0;
            long start = System.currentTimeMillis();
            while ((readCount = inputStream.read(bytes)) >= 0){
                total += readCount;
                dataOutputStream.write(bytes);
            }
            long end = System.currentTimeMillis();
            System.out.println("传统IO方式=========总共传输字节：" + total +"耗时：" + (end - start));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                socket.close();
                inputStream.close();
                dataOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 零拷贝服务端
     */
    public static void newServer(){
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            ServerSocket socket = serverSocketChannel.socket();
            socket.bind(inetSocketAddress);
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
            while (true){
                //等待客户端连接
                SocketChannel socketChannel = serverSocketChannel.accept();
                //读取客户端数据
                int read = 0;
                while (read != -1){
                    read = socketChannel.read(byteBuffer);
                    // position = 0;    mark = -1;  重置bytebuffer
                    byteBuffer.rewind();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 零拷贝客户端
     */
    public static void newClkient(){
        SocketChannel socketChannel = null;
        FileInputStream inputStream = null;
        FileChannel fileChannel = null;
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
            socketChannel = SocketChannel.open();
            socketChannel.connect(inetSocketAddress);
            String fileName = "C:\\duwk\\code\\myself\\frame-master\\netty\\src\\main\\resources\\file\\jdk1.8.0_51.zip";
            inputStream = new FileInputStream(fileName);
            fileChannel = inputStream.getChannel();

            /*在linux下一个transferTo函数就可以完成传输   在windows下transferTo每次只能发送8M，需要分段发送，分段时要注意发送的起始位置
                参数说明
                position : 读取或写入的起始位置
                count : 传输大小
                target : 目标channel
            */
            long count = 0;
            long number = fileChannel.size() / (8*1024*1024);
            number = number + 1;
            long start = System.currentTimeMillis();
            for (int i = 0;i<number;i++){
                long position = i * (8*1024*1024);
                count += fileChannel.transferTo(position, fileChannel.size(), socketChannel);
            }
            long end = System.currentTimeMillis();
            System.out.println("零拷贝方式=========发送的总共字节数：" + count + "耗时：" + (end -start));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                socketChannel.close();
                inputStream.close();
                fileChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

/**
 * 非阻塞实现服务端和客户端之间通信
 */
class NioServerAndClientConnect{

    public static void main(String[] args) {
        serverAndClientTest();
    }

    /**
     * 非阻塞实现服务端和客户端之间通信
     */
    public static void serverAndClientTest() {
        Thread thread = new Thread(() -> {
            serverTest();
        });
        Thread thread1 = new Thread(() -> {
            clientTest();
        });
        thread.start();
        //等待两秒启动客户端
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread1.start();
    }

    /**
     * 服务器端
     */
    public static void serverTest(){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            Selector selector = Selector.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
            serverSocketChannel.socket().bind(inetSocketAddress);
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //服务端的socketChannel注册到selector 并设置监听事件为准备连接事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //循环等待客户端连接
            while (true){
                //阻塞一秒后事件数量若为0则没有连接事件发生
                boolean nothing = selector.select(1000) == 0;
                if (nothing){
                    System.out.println("服务器等待了1秒，无连接");
                    continue;
                }
                //有事件发生,获取到事件的selectionKey集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //通过selectionKey反向获取通道
                selectionKeys.forEach(selectionKey -> {
                    //判断事件类型
                    //连接事件
                    boolean acceptable = selectionKey.isAcceptable();
                    //已经建立连接事件
                    boolean connectable = selectionKey.isConnectable();
                    //写事件
                    boolean writable = selectionKey.isWritable();
                    //判断selectKey是否有效
                    boolean valid = selectionKey.isValid();
                    //读事件
                    boolean readable = selectionKey.isReadable();

                    if (acceptable){
                        //处理连接事件
                        try {
                            //客户端连接事件，，给客户端生成一个非阻塞的SocketChannel
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            //将socketChannel注册到selector,设置监听事件为准备读事件,并关联一个Buffer
                            socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (readable){
                        try {
                            //处理读取事件
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            //获取channel关联的buffer
                            ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                            socketChannel.read(buffer);
                            System.out.println("客户端发送的数据：" + new String(buffer.array()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    //移除集合中的selectionKey，防止重复操作
                    selectionKeys.remove(selectionKey);
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 客户端
     */
    public static void clientTest(){
        String data = "我是数据！";
        try {
            SocketChannel socketChannel = SocketChannel.open();
            //设置非阻塞
            socketChannel.configureBlocking(false);
            //设置服务器端的ip和端口
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
            //连接服务器
            boolean connect = socketChannel.connect(inetSocketAddress);
            if (!connect){
                System.out.println("客户端正在连接...");
                while (!socketChannel.finishConnect()){
                    //客户端还没有连接成功，可以先处理其他逻辑
                    System.out.println("客户端还没有连接成功，还在连接!");
                }
            }
            System.out.println("客户端连接成功！发送数据给服务端...");
            ByteBuffer byteBuffer = ByteBuffer.wrap(data.getBytes());
            int write = socketChannel.write(byteBuffer);
            if (write == data.getBytes().length){
                System.out.println("客户端数据发送完成！");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
