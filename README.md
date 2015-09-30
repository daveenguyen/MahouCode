# MagicEars

The purpose of this project is to calculate the transmit pattern from GwtS/MwM hex codes.

The pattern generated is ready for Android's infrared transmitter API.

[ConsumerIrManager.transmit](https://developer.android.com/reference/android/hardware/ConsumerIrManager.html#transmit(int,%20int%5B%5D))


## Usage

### Gradle

``` gradle
repositories {
  jcenter()
  maven { url "https://jitpack.io" }
}

dependencies {
  compile 'com.github.daveenguyen:magicears:-SNAPSHOT'
}
```


## Example

``` java
import com.daveenguyen.magicears.EarCode;
import java.util.Arrays;

public class Example {
  public static void main(String[] args) {
    // right ear white and left ear blue.
    // CRC not in code.
    EarCode code = new EarCode("93 0E 00 0E 84");
    System.out.println(code.getCarrierFrequency());
    System.out.println(Arrays.toString(code.getPattern()));
    // ConsumerIrManager.transmit(code.getCarrierFrequency(), code.getPattern());
  }
}

// Output:
// 38005
// [417, 833, 833, 417, 833, 833, 833, 1250, 1667, 417, 3750, 417, 833, 1250, 1667, 417, 1250, 417, 1667, 833, 417, 417, 417, 1250, 417, 1250]
```
