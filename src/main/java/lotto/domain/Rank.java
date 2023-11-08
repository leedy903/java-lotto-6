package lotto.domain;

import java.util.Arrays;
import java.util.function.BiPredicate;

public enum Rank {

    FAIL(0, 0, (matchCount, isBonusMatched) -> matchCount < 3, ""),
    FIFTH(3, 5_000, (matchCount, isBonusMatched) -> matchCount == 3, "3개 일치 (5,000원)"),
    FOURTH(4, 50_000, (matchCount, isBonusMatched) -> matchCount == 4,"4개 일치 (50,000원)"),
    THIRD(5, 1_500_000, (matchCount, isBonusMatched) -> matchCount == 5 && !isBonusMatched, "5개 일치 (1,500,000원)"),
    SECOND(5, 30_000_000, (matchCount, isBonusMatched) -> matchCount == 5 && isBonusMatched, "5개 일치, 보너스 볼 일치 (30,000,000원)"),
    FIRST(6, 2_000_000_000, (matchCount, isBonusMatched) -> matchCount == 6, "6개 일치 (2,000,000,000원)");

    private int matchCount;
    private int prize;
    private BiPredicate<Integer, Boolean> isMatched;
    private String message;

    Rank(int matchCount, int prize, BiPredicate<Integer, Boolean> isMatched, String message) {
        this.matchCount = matchCount;
        this.prize = prize;
        this.isMatched = isMatched;
        this.message = message;
    }

    public static Rank valueOf(int matchCount, boolean isBonusMatched) {
        return Arrays.stream(values())
                .filter(Rank -> Rank.isMatched.test(matchCount, isBonusMatched))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] Out of winning condition > "));
    }

    public int getPrize() {
        return prize;
    }

    public String getMessage() {
        return message;
    }
}
