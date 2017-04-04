package com.cs1119it.fanxin.lecontrol.mina;

import com.cs1119it.fanxin.lecontrol.unit.ByteStringUtil;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.proxy.utils.StringUtilities;

/**
 * Created by fanxin on 2017/4/4.
 */

public class ByteArrayCodecFactory implements ProtocolCodecFactory {
    private ByteArrayDecoder decoder;
    private ByteArrayEncoder encoder;

    public ByteArrayCodecFactory() {
        encoder = new ByteArrayEncoder();
        decoder = new ByteArrayDecoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    //编码
    private class ByteArrayEncoder extends ProtocolEncoderAdapter {

        @Override
        public void encode(IoSession session, Object message, ProtocolEncoderOutput out) {
            if (message instanceof String) {
                IoBuffer buffer = IoBuffer.allocate(256);
                buffer.setAutoExpand(true);

                buffer.put(ByteStringUtil.hexStrToByteArray(message.toString()));
                buffer.flip();

                out.write(buffer);
                out.flush();

                buffer.free();
            }
        }

    }

    //解码
    private class ByteArrayDecoder extends ProtocolDecoderAdapter {
        @Override
        public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
            int limit = in.limit();
            byte[] bytes = new byte[limit];
            in.get(bytes);
            out.write(ByteStringUtil.byteArrayToHexStr(bytes));
        }
    }
}
