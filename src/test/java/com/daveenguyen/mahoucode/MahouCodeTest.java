package com.daveenguyen.mahoucode;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MahouCodeTest {
    private MahouCode code;

    @Test
    public void getCodeTest() throws Exception {
        String result;
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("930E000E84DD");
        inputs.add("930E000E84DD ");
        inputs.add(" 930E000E84DD ");
        inputs.add(" 930E000E84DD");
        inputs.add(" 930E000 E84DD");
        inputs.add("93 0E 00 0E 84 DD");

        for (String input : inputs) {
            code = new MahouCode(input);
            result = code.getCode();
            assertThat(result, is(equalTo("93 0E 00 0E 84 DD")));
        }
    }

    @Test
    public void getPatternTest() throws Exception {
        code = new MahouCode("93 0E 00 0E 84 DD");

        int[] expected = {417, 833, 833, 417, 833, 833, 833, 1250, 1667, 417, 3750, 417, 833, 1250,
                1667, 417, 1250, 417, 1667, 833, 417, 417, 417, 1250, 417, 1250};
        assertThat(code.getPattern(), is(equalTo(expected)));
    }

    @Test
    public void getCarrierTest() throws Exception {
        MahouCode code = new MahouCode("90 60 A6");
        assertThat(code.getCarrierFrequency(), is(equalTo(38005)));
    }

    @Test
    public void getOldApiPatternTest() throws Exception {
        MahouCode code = new MahouCode("93 0E 00 0E 84 DD");

        int[] expected = {16, 32, 32, 16, 32, 32, 32, 48, 63, 16, 143, 16, 32, 48, 63, 16, 48, 16,
                63, 32, 16, 16, 16, 48, 16, 48};
        assertThat(code.getOldApiPattern(), is(equalTo(expected)));
    }

    @Test
    public void code9xTest() throws Exception {
        code = new MahouCode(MahouCode.parse9x("0E 00 0E 84"));
        String result = code.getCode();
        assertThat(result, is(equalTo("93 0E 00 0E 84 DD")));

        code = new MahouCode(MahouCode.parse9x("0E 84"));
        result = code.getCode();
        assertThat(result, is(equalTo("91 0E 84 B2")));
    }
}
