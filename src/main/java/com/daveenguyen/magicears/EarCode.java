package com.daveenguyen.magicears;

import java.util.ArrayList;
import java.util.List;

public class EarCode {
    private final float CARRIER_FREQUENCY = 38005f;
    private final float BAUD_RATE = 2400f;
    private final float MICROSEC_PER_SEC = 1000000f;
    private final int[] CRC_TABLE = {
            0x00, 0x5E, 0xBC, 0xE2, 0x61, 0x3F, 0xDD, 0x83,
            0xC2, 0x9C, 0x7E, 0x20, 0xA3, 0xFD, 0x1F, 0x41,
            0x9D, 0xC3, 0x21, 0x7F, 0xFC, 0xA2, 0x40, 0x1E,
            0x5F, 0x01, 0xE3, 0xBD, 0x3E, 0x60, 0x82, 0xDC,
            0x23, 0x7D, 0x9F, 0xC1, 0x42, 0x1C, 0xFE, 0xA0,
            0xE1, 0xBF, 0x5D, 0x03, 0x80, 0xDE, 0x3C, 0x62,
            0xBE, 0xE0, 0x02, 0x5C, 0xDF, 0x81, 0x63, 0x3D,
            0x7C, 0x22, 0xC0, 0x9E, 0x1D, 0x43, 0xA1, 0xFF,
            0x46, 0x18, 0xFA, 0xA4, 0x27, 0x79, 0x9B, 0xC5,
            0x84, 0xDA, 0x38, 0x66, 0xE5, 0xBB, 0x59, 0x07,
            0xDB, 0x85, 0x67, 0x39, 0xBA, 0xE4, 0x06, 0x58,
            0x19, 0x47, 0xA5, 0xFB, 0x78, 0x26, 0xC4, 0x9A,
            0x65, 0x3B, 0xD9, 0x87, 0x04, 0x5A, 0xB8, 0xE6,
            0xA7, 0xF9, 0x1B, 0x45, 0xC6, 0x98, 0x7A, 0x24,
            0xF8, 0xA6, 0x44, 0x1A, 0x99, 0xC7, 0x25, 0x7B,
            0x3A, 0x64, 0x86, 0xD8, 0x5B, 0x05, 0xE7, 0xB9,
            0x8C, 0xD2, 0x30, 0x6E, 0xED, 0xB3, 0x51, 0x0F,
            0x4E, 0x10, 0xF2, 0xAC, 0x2F, 0x71, 0x93, 0xCD,
            0x11, 0x4F, 0xAD, 0xF3, 0x70, 0x2E, 0xCC, 0x92,
            0xD3, 0x8D, 0x6F, 0x31, 0xB2, 0xEC, 0x0E, 0x50,
            0xAF, 0xF1, 0x13, 0x4D, 0xCE, 0x90, 0x72, 0x2C,
            0x6D, 0x33, 0xD1, 0x8F, 0x0C, 0x52, 0xB0, 0xEE,
            0x32, 0x6C, 0x8E, 0xD0, 0x53, 0x0D, 0xEF, 0xB1,
            0xF0, 0xAE, 0x4C, 0x12, 0x91, 0xCF, 0x2D, 0x73,
            0xCA, 0x94, 0x76, 0x28, 0xAB, 0xF5, 0x17, 0x49,
            0x08, 0x56, 0xB4, 0xEA, 0x69, 0x37, 0xD5, 0x8B,
            0x57, 0x09, 0xEB, 0xB5, 0x36, 0x68, 0x8A, 0xD4,
            0x95, 0xCB, 0x29, 0x77, 0xF4, 0xAA, 0x48, 0x16,
            0xE9, 0xB7, 0x55, 0x0B, 0x88, 0xD6, 0x34, 0x6A,
            0x2B, 0x75, 0x97, 0xC9, 0x4A, 0x14, 0xF6, 0xA8,
            0x74, 0x2A, 0xC8, 0x96, 0x15, 0x4B, 0xA9, 0xF7,
            0xB6, 0xE8, 0x0A, 0x54, 0xD7, 0x89, 0x6B, 0x35
    };
    private final int START_BIT = 0;
    private final int STOP_BIT = 1;
    private int carrierFrequency; // The IR carrier frequency in hertz.
    private int[] pattern; // The toggle pattern in microseconds to transmit.


    /**
     * Generates the IR toggle pattern from given hex code for Android 4.4.3 or newer.
     *
     * @param code the hex code without CRC at the end.
     */
    public EarCode(String code) {
        this(code, true, true);
    }

    /**
     * Generates the IR toggle pattern from given hex code.
     *
     * @param code   the hex code.
     * @param addCrc adds CRC calculation. Pass false if code includes the CRC.
     */
    public EarCode(String code, boolean addCrc) {
        this(code, addCrc, true);
    }

    /**
     * Generates the IR toggle pattern from given hex code.
     *
     * @param code   the hex code.
     * @param addCrc adds CRC calculation. Pass false if code includes the CRC.
     * @param isNew  the flag to indicate if using Android 4.4.3 or newer.
     */
    public EarCode(String code, boolean addCrc, boolean isNew) {
        List<Integer> patternList;

        patternList = generatePattern(code, addCrc, isNew);
        carrierFrequency = (int) CARRIER_FREQUENCY;
        pattern = convertIntegers(patternList);
    }

    /**
     * Gets the carriet frequency.
     *
     * @return the carrier frequency.
     */
    public int getCarrierFrequency() {
        return carrierFrequency;
    }

    /**
     * Gets the toggle pattern.
     *
     * @return the toggle pattern.
     */
    public int[] getPattern() {
        return pattern;
    }

    /**
     * Convert list of integers to primitive array.
     *
     * @param integers list of integers.
     * @return array of int.
     */
    private int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i);
        }
        return ret;
    }

    /**
     * Generates the toggle pattern in terms of microseconds if isNew is true; otherwise, in
     * terms of pulse.
     *
     * @param code   the hex code string.
     * @param addCrc adds crc calculation if true.
     * @param isNew  the flag to indicate if using Android 4.4.3 or newer.
     * @return the toggle pattern in terms based on isNew.
     */
    private List<Integer> generatePattern(String code, boolean addCrc, boolean isNew) {
        // If API is new (>= 4.4.3), calculate microseconds per bit, else calculate pulse per bit.
        float multiplier = (isNew) ? (MICROSEC_PER_SEC / BAUD_RATE) : (CARRIER_FREQUENCY / BAUD_RATE);

        List<Integer> patternList = new ArrayList<>();
        List<Integer> buffer = parseByteValues(code);

        if (addCrc) buffer.add(calcCrc(buffer));

        buffer = toTogglePattern(buffer);


        for (int num = 0; num < buffer.size(); num++) {
            patternList.add(Math.round(buffer.get(num) * multiplier));
        }

        return patternList;
    }

    /**
     * Transforms the list of code values to toggle pattern.
     *
     * @param codeValues the list of integer values of the hex codes.
     * @return the alternating toggle pattern in terms of bits.
     */
    private List<Integer> toTogglePattern(List<Integer> codeValues) {
        StringBuilder myStr = new StringBuilder();

        for (int value : codeValues) {
            // Convert byte value to binary
            StringBuilder binaryString = new StringBuilder(String.format("%8s", Integer.toString(value, 2)).replace(' ', '0'));

            myStr.append(START_BIT)
                    .append(binaryString.reverse()) // Reverse to send least significant bit first
                    .append(STOP_BIT);
        }

        String fullString = myStr.toString();

        List<Integer> togglePattern = new ArrayList<>();

        // Count the occurrence of the same consecutive bits and add them to the pattern list
        char c = fullString.charAt(0);
        int count = 1;

        for (int i = 1; i < fullString.length(); i++) {
            if (c == fullString.charAt(i)) {
                count++;
            } else {
                togglePattern.add(count);
                c = fullString.charAt(i);
                count = 1;
            }
        }

        togglePattern.add(count);

        return togglePattern;
    }

    /**
     * Parses the byte values from the hex code string and returns it as a list of integers.
     *
     * @param hexCode The hex code string. Spaces are ignored.
     * @return List of values of each hex byte.
     */
    private List<Integer> parseByteValues(String hexCode) {
        List<Integer> result = new ArrayList<>();
        String codeString = hexCode.replace(" ", "").trim();
        int size = codeString.length();

        if (size % 2 == 0) {
            for (String s : getParts(codeString, 2)) {
                result.add(Integer.parseInt(s, 16));
            }
        }

        return result;
    }

    /**
     * Split a string into parts by partition size.
     *
     * @param string        String to be partitioned
     * @param partitionSize length of parts
     * @return list of parts
     */
    private List<String> getParts(String string, int partitionSize) {
        List<String> parts = new ArrayList<String>();
        int len = string.length();
        for (int i = 0; i < len; i += partitionSize) {
            parts.add(string.substring(i, Math.min(len, i + partitionSize)));
        }
        return parts;
    }

    /**
     * Calculates the CRC for the given list of integers.
     *
     * @param data The list of integers that represents the ear code
     * @return The calculated CRC value.
     */
    private int calcCrc(List<Integer> data) {
        int crc = 0;

        for (int i = 0; i < data.size(); i++) {
            crc = CRC_TABLE[crc ^ data.get(i)];
        }

        return crc;
    }
}
