package com.mengwei.java.netty;


import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.concurrent.GlobalEventExecutor;

public class HelloServerHandler extends SimpleChannelInboundHandler<Object>  {
	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	private HttpRequest request;
	private ByteBuf content;
	private static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg)
//	throws Exception {
//		if(msg instanceof HttpRequest){
//			request = (HttpRequest) msg;
//			String uri = request.getUri();
//			System.out.println("Uri:" + uri);
//		}
//		// 收到消息直接打印输出
//		if (msg instanceof HttpContent) {  
//			HttpContent httpContent = (HttpContent) msg;
//			ByteBuf buf = httpContent.content();
//			System.out.println(":::::"+buf.toString(io.netty.util.CharsetUtil.UTF_8));
//			buf.release();  
//
//			//            if (reader.isEnd()) {  
//			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(("I am ok")  
//					.getBytes("UTF-8")));  
//			response.headers().set(Names.CONTENT_TYPE, "text/plain");  
//			response.headers().set(Names.CONTENT_LENGTH, response.content().readableBytes());  
//			response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);  
//			for(Channel ch:channelGroup){
//				ch.write(response);
//				ch.flush();
//			}
//			//                ctx.write(response);  
//			//                ctx.flush();  
//			//            }  
//		}  
//		// 返回客户端消息 - 我已经接收到了你的消息
//		//        ctx.writeAndFlush(msg+"\n");
//	}

	/*
	 * 
	 * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
	 * 
	 * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
	 */
	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//		ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
//				new GenericFutureListener<Future<Channel>>() {
//					@Override
//					public void operationComplete(Future<Channel> future) throws Exception {
//						ctx.writeAndFlush(
//								"Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat service!\n");
//						ctx.writeAndFlush(
//								"Your session is protected by " +
//								ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
//						" cipher suite.\n");
//
//						channels.add(ctx.channel());
//					}
//				});
		channels.add(ctx.channel());
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg)
	throws Exception {
		FullHttpResponse response = null;

//		if (msg instanceof HttpContent) {
//			HttpContent httpContent = (HttpContent) msg;
//
//			content = httpContent.content();
//
//			response = writeResponse((LastHttpContent)msg, ctx, content);
//		}

		if (msg instanceof HttpRequest) {
	        FullHttpResponse response1 = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));

			if(response1!=null){
				for (Channel c: channels) {
					if (c != ctx.channel()) {
						c.writeAndFlush(response1);
					} else {
						c.writeAndFlush(response1);
					}
				}

			}
		}


		// Close the connection if the client has sent 'bye'.

	}
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    private FullHttpResponse writeResponse(HttpObject currentObj, ChannelHandlerContext ctx,ByteBuf content ) {
        // Decide whether to close the connection or not.
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1,HttpResponseStatus.OK,
                content);

        response.headers().set("content-type", "text/html; charset=UTF-8");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());

        return response;
    }
}
