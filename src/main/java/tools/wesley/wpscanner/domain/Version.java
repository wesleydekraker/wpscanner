package tools.wesley.wpscanner.domain;

public class Version {
    private static final Long UNKNOWN = -1L;

    private Long versionAsLong;
    private String versionAsString;

    public Version() {
        this(UNKNOWN);
    }

    public Version(String versionAsString) {
        this.versionAsLong = versionToLong(versionAsString);
        this.versionAsString = versionToString(versionAsLong);
    }

    public Version(Long versionAsLong) {
        this.versionAsLong = versionAsLong;
        this.versionAsString = versionToString(versionAsLong);
    }

    public Long toLong() {
        return versionAsLong;
    }

    private Long versionToLong(String version) {
        String[] parts = version.split("\\.");

        int maxPart = 0;
        for (String s : parts) {
            if (s.length() > maxPart) {
                maxPart = s.length();
            }
        }

        if (version.length() > 0 && parts.length < 5 && maxPart < 4) {
            StringBuilder builder = new StringBuilder();

            for (String part : parts)
                builder.append(("000" + part).substring(part.length()));

            builder.append("000".repeat((4 - parts.length)));

            return Long.parseLong(builder.toString());
        }

        return UNKNOWN;
    }

    private String versionToString(Long version) {
        if (version != null && !version.equals(UNKNOWN)) {
            String cleanVersion = Long.toString(version);
            cleanVersion = ("000000000000" + cleanVersion).substring(cleanVersion.length());

            long[] parts = new long[4];
            for (int i = 0; i < 4; i++)
                parts[i] = Long.parseLong(cleanVersion.substring(i * 3, i * 3 + 3));

            if (parts[2] == 0 && parts[3] == 0) {
                return parts[0] + "." + parts[1];
            } else if (parts[3] == 0) {
                return parts[0] + "." + parts[1] + "." + parts[2];
            } else {
                return parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3];
            }
        }

        return "X.X.X";
    }

    @Override
    public String toString() {
        return versionAsString;
    }
}
