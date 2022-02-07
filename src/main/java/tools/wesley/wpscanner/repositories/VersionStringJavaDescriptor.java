package tools.wesley.wpscanner.repositories;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import tools.wesley.wpscanner.domain.Version;

public class VersionStringJavaDescriptor extends AbstractTypeDescriptor<Version> {
    public static final VersionStringJavaDescriptor INSTANCE = new VersionStringJavaDescriptor();

    public VersionStringJavaDescriptor() {
        super(Version.class);
    }

    @Override
    public Version fromString(String s) {
        return new Version(s);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <X> X unwrap(Version version, Class<X> type, WrapperOptions wrapperOptions) {
        if (Long.class.isAssignableFrom(type))
            return (X) version.toLong();

        throw unknownUnwrap(type);
    }

    @Override
    public <X> Version wrap(X value, WrapperOptions wrapperOptions) {
        if (value instanceof Long)
            return new Version((Long) value);

        throw unknownWrap(value.getClass());
    }
}