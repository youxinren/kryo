
package serializers;

import java.util.ArrayList;

import serializers.java.Image;
import serializers.java.Media;
import serializers.java.MediaContent;

import com.esotericsoftware.kryo.serialize.CollectionSerializer;
import com.esotericsoftware.kryo.serialize.FieldSerializer;
import com.esotericsoftware.kryo.serialize.FieldSerializer.CachedField;

public class KryoOptimizedSerializer extends KryoSerializer {
	public KryoOptimizedSerializer () {
		this("persistence-optimized");
	}

	public KryoOptimizedSerializer (String name) {
		super(name);
		kryo.register(Image.class, new FieldSerializer(kryo, true, true));

		FieldSerializer mediaContentSerializer = new FieldSerializer(kryo, true, true);
		kryo.register(MediaContent.class, mediaContentSerializer);

		CachedField imagesField = mediaContentSerializer.getField(MediaContent.class, "_images");
		imagesField.concreteType = ArrayList.class;
		imagesField.serializer = new CollectionSerializer(kryo, Image.class, true);

		CachedField mediaField = mediaContentSerializer.getField(MediaContent.class, "_media");
		mediaField.concreteType = Media.class;
		FieldSerializer mediaSerializer = new FieldSerializer(kryo, true, true);
		mediaField.serializer = mediaSerializer;

		CachedField personsField = mediaSerializer.getField(Media.class, "_persons");
		personsField.concreteType = ArrayList.class;
		personsField.serializer = new CollectionSerializer(kryo, String.class, true);

		mediaSerializer.getField(Media.class, "_copyright").canBeNull = true;
	}
}
