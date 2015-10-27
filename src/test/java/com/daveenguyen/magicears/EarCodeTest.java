package com.daveenguyen.magicears;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EarCodeTest {

    @Test
    public void codeToTransmitPattern_isCorrect() throws Exception {
        EarCode code = new EarCode("93 0E 00 0E 84");

        int[] expected = {417, 833, 833, 417, 833, 833, 833, 1250, 1667, 417, 3750, 417, 833, 1250,
                1667, 417, 1250, 417, 1667, 833, 417, 417, 417, 1250, 417, 1250};
        assertThat(code.getPattern(), is(equalTo(expected)));
    }
}
