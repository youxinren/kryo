
package serializers;

import java.nio.ByteBuffer;

import serializers.java.MediaContent;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.compress.DeflateCompressor;
import com.esotericsoftware.kryo.context.BufferContext;

public class KryoCompressedSerializer extends KryoOptimizedSerializer {
	public KryoCompressedSerializer () {
		this("persistence-compressed");
	}

	public KryoCompressedSerializer (String name) {
		super(name);
		Serializer mediaContentSerializer = kryo.getRegisteredClass(MediaContent.class).serializer;
		kryo.register(MediaContent.class, new DeflateCompressor(mediaContentSerializer));

		final ByteBuffer i = ByteBuffer.allocate(512);
		final ByteBuffer o = ByteBuffer.allocate(512);
		objectBuffer.setContext(new BufferContext() {
			public ByteBuffer getOutputBuffer () {
				return o;
			}

			public ByteBuffer getInputBuffer () {
				return i;
			}
		});
	}
}
