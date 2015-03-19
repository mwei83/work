package com.mengwei.java.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 以("\n")为结尾分割的 解码器
//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
//                Delimiters.lineDelimiter()));

        // 字符串解码 和 编码
        pipeline.addLast(new HttpResponseEncoder());
//        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new HttpRequestDecoder());
//        pipeline.addLast(new StringDecoder());
        

        // 自己的逻辑Handler
        pipeline.addLast("handler", new HelloServerHandler());
    }
}

