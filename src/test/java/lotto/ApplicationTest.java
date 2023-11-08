package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("구입금액이 천 단위가 아니면 예외가 발생한다.")
    @Test
    void purchaseAmountNotMultipleOfThousand() {
        assertSimpleTest(() -> {
            runException("101");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("구입금액이 음수면 예외가 발생한다.")
    @Test
    void purchaseAmountMinus() {
        assertSimpleTest(() -> {
            runException("-1");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("구입금액이 Integer Max보다 크면 예외가 발생한다.")
    @Test
    void purchaseAmountTooBig() {
        assertSimpleTest(() -> {
            runException("1000000000000000000");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호에 숫자가 아닌 문자가 있으면 예외가 발생한다.")
    @Test
    void winningNumbersNonInteger() {
        assertSimpleTest(() -> {
            runException("1000", "1,a,3,4,5,6");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호에 중복이 있으면 예외가 발생한다.")
    @Test
    void winningNumbersDuplicate() {
        assertSimpleTest(() -> {
            runException("1000", "1,1,3,4,5,6");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호에 1 ~ 45 이외의 숫자가 있으면 예외가 발생한다.")
    @Test
    void winningNumbersMinus() {
        assertSimpleTest(() -> {
            runException("1000", "-1,2,3,4,5,6");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호에 1 ~ 45 이외의 숫자가 있으면 예외가 발생한다.")
    @Test
    void winningNumbersTooBig() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,46");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호가 6개가 되지 않는다면 예외가 발생한다.")
    @Test
    void winningNumbersTooLess() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호가 6 개를 넘는다면 예외가 발생한다.")
    @Test
    void winningNumbersTooMany() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6,7");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }


    @DisplayName("당첨 번호가 쉼표(,)로 시작하면 예외가 발생한다.")
    @Test
    void winningNumbersStratWithComma() {
        assertSimpleTest(() -> {
            runException("1000", ",1,2,3,4,5,6");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호가 쉼표(,)들로 시작하면 예외가 발생한다.")
    @Test
    void winningNumbersStratWithCommas() {
        assertSimpleTest(() -> {
            runException("1000", ",,,1,2,3,4,5,6");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호 중간에 쉼표(,)가 여러 번 들어가면 예외가 발생한다.")
    @Test
    void winningNumbersDuplicatedComma() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,,,,3,4,5,6");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호 마지막에 쉼표(,)가 들어가면 예외가 발생한다.")
    @Test
    void winningNumbersEndWithComma() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6,");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("당첨 번호 마지막에 쉼표(,)들이 들어가면 예외가 발생한다.")
    @Test
    void winningNumbersEndWithCommas() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6,,,");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("보너스 번호가 숫자가 아니라면 예외가 발생한다.")
    @Test
    void bonusNumbersNonInteger() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6", "a");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("보너스 번호가 1 ~ 45 이외의 숫자라면 예외가 발생한다.")
    @Test
    void bonusNumbersMinus() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6", "-1");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("보너스 번호가 1 ~ 45 이외의 숫자라면 예외가 발생한다.")
    @Test
    void bonusNumbersTooBing() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6", "46");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복 된다면 예외가 발생한다.")
    @Test
    void bonusNumbersDuplicatedWithWinningNumbers() {
        assertSimpleTest(() -> {
            runException("1000", "1,2,3,4,5,6", "1");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
