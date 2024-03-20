package org.adamalang.mqtt;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;

public class MqttBrokerHandler extends SimpleChannelInboundHandler<MqttMessage> {
    // For now, just show the type of Mqtt Message it receives...
    // This is for each child, so putting the topic tree here is insufficient

    //Although we know it is insufficient, we can at least try
    HashMap<String, >

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("Channel is now connected");
        //The id of the channel, can just be the one that connects first, or it can be up to the client in order to figure out the id.
        // This would require a map for the connection, to figure out which client ID is for which connection.
    }


    public void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) {
        System.out.println("Mqtt message");
        if (msg instanceof MqttConnectMessage) {
            //Send back Connack Message
            MqttMessageBuilders.ConnAckBuilder connAckBuilder = MqttMessageBuilders.connAck();
            // Determine if session present
            connAckBuilder.sessionPresent(false);
            // Return code based on authentication
            connAckBuilder.returnCode(MqttConnectReturnCode.CONNECTION_ACCEPTED);
            // Properties
            connAckBuilder.properties(MqttProperties.NO_PROPERTIES);
            ctx.writeAndFlush(connAckBuilder.build());
        } else if (msg instanceof MqttSubscribeMessage) {
            MqttSubscribeMessage subMsg = (MqttSubscribeMessage) msg;
            System.out.println(subMsg);
            //Subscribe the client to the topic


            // Send back Suback Message
            MqttMessageBuilders.SubAckBuilder subAckBuilder = MqttMessageBuilders.subAck();
            subAckBuilder.packetId(subMsg.variableHeader().messageId());
            ctx.writeAndFlush(subAckBuilder.build());
        } else if (msg instanceof MqttPublishMessage) {
            // Send the publish message to everyone


            //Send the puback message to qos 1 clients
            MqttPublishMessage pubMsg = (MqttPublishMessage) msg;
            MqttPublishVariableHeader header = pubMsg.variableHeader();
            MqttMessageBuilders.PubAckBuilder pubAckBuilder = MqttMessageBuilders.pubAck();
            pubAckBuilder.reasonCode((byte) 0);
            pubAckBuilder.packetId(header.packetId());
            // If QOS is equal to 1
            if (pubMsg.fixedHeader().qosLevel() == MqttQoS.AT_LEAST_ONCE) {
                ctx.writeAndFlush(pubAckBuilder.build());
            }
        } else if (msg instanceof MqttUnsubscribeMessage) {
            MqttUnsubscribeMessage unsubMsg = (MqttUnsubscribeMessage) msg;
            //Remove client from client subscriptions

            MqttMessageBuilders.UnsubAckBuilder builder = MqttMessageBuilders.unsubAck();
            builder.addReasonCode((short) 0);
            builder.packetId(unsubMsg.variableHeader().messageId());
            ctx.writeAndFlush(builder.build());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        super.channelRead(ctx, msg);
        System.out.println(msg.getClass().getSimpleName());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        super.channelInactive(ctx);
        System.out.println("It is now inactive...");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("Channel registered to event loop...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
    }
}
