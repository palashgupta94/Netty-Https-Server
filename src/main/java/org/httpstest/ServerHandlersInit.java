package org.httpstest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;

public class ServerHandlersInit extends ChannelInitializer<SocketChannel> {

    private static Logger logger = LogManager.getLogger(ServerHandlersInit.class);

    public ServerHandlersInit() {

    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {

        SslHandler sslHandler = SSLHandlerProvider.getSSLHandler();

        socketChannel.pipeline().addLast(
                sslHandler,
                new HttpServerCodec(),
                new HttpObjectAggregator(1048576),
                new SimpleChannelInboundHandler<FullHttpRequest>() {

                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
                        logger.info("message received: {}" , fullHttpRequest);

                        ByteBuf content = fullHttpRequest.content();
                        String json = content.toString(CharsetUtil.UTF_8);
                        logger.info("Received JSON data: {}" , json);

                        channelHandlerContext.writeAndFlush(new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK));
                        channelHandlerContext.channel().close();
                    }
                });
    }

}