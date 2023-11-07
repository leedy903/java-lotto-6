package lotto.utils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UtilsTest {
    @DisplayName("숫자가 아닌 문자열을 변경할 경우 예외가 발생한다.")
    @Test
    void convertNonInteger(){
        assertThatThrownBy(() -> Utils.stringToInt("a"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숫자가 아닌 문자열을 변경할 경우 예외가 발생한다.")
    @Test
    void convertNonIntegers(){
        assertThatThrownBy(() -> Utils.stringListToInt(List.of("1", "a", "3")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}