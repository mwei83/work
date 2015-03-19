package com.mengwei.java.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class HelloClient {

    public static String host = "127.0.0.1";
    public static int port = 7878;

    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     * @throws URISyntaxException 
     */
    public static void main(String[] args) throws InterruptedException,
            IOException, URISyntaxException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());

            // 连接服务端
            ChannelFuture f = b.connect(host, port).sync(); // (5)  
            URI uri = new URI("http://127.0.0.1:7878");  
            String msg = "Are you ok?";  
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,  
                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes()));  
  
            // 构建http请求  
            request.headers().set(HttpHeaders.Names.HOST, host);  
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);  
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());  
            request.headers().set("messageType", "normal");  
            request.headers().set("businessType", "testServerState");  
            // 发送http请求  
            f.channel().write(request);  
            f.channel().flush();  
            f.channel().closeFuture().sync();  
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
}
