package tools.wesley.wpscanner.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import tools.wesley.wpscanner.domain.Version;

import java.io.IOException;

public class VersionSerializer extends StdSerializer<Version> {
    public VersionSerializer() {
        this(null);
    }

    public VersionSerializer(Class<Version> t) {
        super(t);
    }

    @Override
    public void serialize(Version value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeString(value.toString());
    }
}