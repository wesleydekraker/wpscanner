package tools.wesley.wpscanner.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import tools.wesley.wpscanner.domain.Version;

import java.io.IOException;

public class VersionDeserializer extends StdDeserializer<Version> {
    public VersionDeserializer() {
        this(null);
    }

    public VersionDeserializer(Class<?> t) {
        super(t);
    }

    @Override
    public Version deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return new Version(jsonParser.getText());
    }
}