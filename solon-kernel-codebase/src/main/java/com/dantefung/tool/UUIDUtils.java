package com.dantefung.tool;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * UUIDUtils
 */
public abstract class UUIDUtils {

    /**
     * Generate UUID
     * e.g:809000792dcc421abdb4a7c4c0ef98d9
     * @return
     */
    public final static String uuid(){
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return (StringUtils.join(
                digits(mostSignificantBits >> 32, 8),
                digits(mostSignificantBits >> 16, 4) ,
                digits(mostSignificantBits, 4),
                digits(leastSignificantBits >> 48, 4),
                digits(leastSignificantBits, 12)
                ));
    }
    
    /** Returns val represented by the specified number of hex digits. */
    private final static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
}
