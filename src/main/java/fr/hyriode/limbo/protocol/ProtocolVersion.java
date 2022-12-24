package fr.hyriode.limbo.protocol;

/**
 * Created by AstFaster
 * on 22/12/2022 at 13:23
 */
public enum ProtocolVersion {

    UNKNOWN(-1),
    V_1_8(47), // Same for all 1.8 versions
    V_1_9(107),
    V_1_9_1(108),
    V_1_9_2(109),
    V_1_9_4(110), // Same as 1.9.3
    V_1_10(210), // Same for all 1.10 versions
    V_1_11(315),
    V_1_11_2(316), // Same as 1.11.1
    V_1_12(335),
    V_1_12_1(338),
    V_1_12_2(340),
    V_1_13(393),
    V_1_13_1(401),
    V_1_13_2(404),
    V_1_14(477),
    V_1_14_1(480),
    V_1_14_2(485),
    V_1_14_3(490),
    V_1_14_4(498),
    V_1_15(573),
    V_1_15_1(575),
    V_1_15_2(578),
    V_1_16(735),
    V_1_16_1(736),
    V_1_16_2(751),
    V_1_16_3(753),
    V_1_16_5(754), // Same as 1.16.4
    V_1_17(755),
    V_1_17_1(756),
    V_1_18(757),
    V_1_18_2(758),
    V_1_19(759),
    V_1_19_2(760),
    V_1_19_3(761),

    ;

    private final int id;

    ProtocolVersion(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public boolean isMore(ProtocolVersion version) {
        return this.id > version.getId();
    }

    public boolean isMoreOrEqual(ProtocolVersion version) {
        return this.id >= version.getId();
    }

    public boolean isLess(ProtocolVersion version) {
        return this.id < version.getId();
    }

    public boolean isLessOrEqual(ProtocolVersion version) {
        return this.id <= version.getId();
    }

    public boolean isInter(ProtocolVersion min, ProtocolVersion max) {
        return this.id >= min.getId() && this.id <= max.getId();
    }

    public static ProtocolVersion earliest() {
        return V_1_8;
    }

    public static ProtocolVersion fromId(int id) {
        for (ProtocolVersion version : values()) {
            if (version.getId() == id) {
                return version;
            }
        }
        return null;
    }

}
