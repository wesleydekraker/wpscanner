package tools.wesley.wpscanner.repositories;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.BigIntTypeDescriptor;
import tools.wesley.wpscanner.domain.Version;

public class VersionStringType extends AbstractSingleColumnStandardBasicType<Version> {
    public VersionStringType() {
        super(BigIntTypeDescriptor.INSTANCE, VersionStringJavaDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "VersionString";
    }
}
