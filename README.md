# MahouCode
[![Build Status](https://travis-ci.org/daveenguyen/MahouCode.svg)](https://travis-ci.org/daveenguyen/MahouCode)

MahouCode calculates the transmit pattern from GwtS/MwM hex codes.
The pattern calculated is ready for use with Android's infrared transmitter API.

[ConsumerIrManager.transmit](https://developer.android.com/reference/android/hardware/ConsumerIrManager.html#transmit(int,%20int%5B%5D))

## Usage

### Gradle

``` gradle
repositories {
  jcenter()
  maven { url "https://jitpack.io" }
}

dependencies {
  compile 'com.github.daveenguyen:mahoucode:0.1.1'
}
```


## Example

``` java
import com.daveenguyen.mahoucode.MahouCode;
import java.util.Arrays;

public class Example {
  public static void main(String[] args) {
    // right ear white and left ear blue.
    // CRC not in code.
    MahouCode code = new MahouCode("93 0E 00 0E 84 DD");

    System.out.println(code.getCarrierFrequency());
    // 38005

    System.out.println(Arrays.toString(code.getPattern()));
    // [417, 833, 833, 417, 833, 833, 833, 1250, 1667, 417, 3750, 417, 833, 1250, 1667, 417, 1250, 417, 1667, 833, 417, 417, 417, 1250, 417, 1250]

    code = new MahouCode(MahouCode.parse9x("0E 00 0E 84"));
    System.out.println(code.getCode());
    // 93 0E 00 0E 84 DD
  }
}

```

## License
Copyright (c) 2015 Davee Nguyen - davee@daveenguyen.com
Released under the terms of the [MIT license](http://mit-license.org/).
