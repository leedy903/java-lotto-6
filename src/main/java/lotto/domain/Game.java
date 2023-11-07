package lotto.domain;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lotto.view.InputView;
import lotto.view.OutputView;

public class Game {
    private final int LOTTO_PRICE = 1000;
    private final int LOTTO_SIZE = 6;
    private final int LOTTO_MIN_NUMBER = 1;
    private final int LOTTO_MAX_NUMBER = 45;
    private int purchaseAmount;
    private List<Integer> winningNumbers = new ArrayList<>();
    private List<Lotto> issuedLottos = new ArrayList<>();
    private int bonusNumber;

    public void run() {
        purchaseLotto();
        issueLotto();
        printIssuedLotto();
        setWinningNumbers();
        setBonusNumber();
        printResult();
        Console.close();
    }

    private void purchaseLotto() {
        while(true) {
            try {
                purchaseAmount = InputView.inputPurchaseAmount();
                validatePurchaseAmount(purchaseAmount);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + e.getStackTrace()[0]);
            }
        }
    }

    private void issueLotto() {
        int lottoAmount = purchaseAmount / LOTTO_PRICE;
        for (int i = 0; i < lottoAmount; i++) {
            List<Integer> randomNumbers = Randoms.pickUniqueNumbersInRange(LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER, LOTTO_SIZE);
            List<Integer> issuedNumbers = new ArrayList<>(randomNumbers);
            Collections.sort(issuedNumbers);
            Lotto issuedLotto = new Lotto(issuedNumbers);
            issuedLottos.add(issuedLotto);
        }
    }

    private void printIssuedLotto() {
        OutputView.printLottoAmount(issuedLottos.size());
        for (Lotto lotto: issuedLottos) {
            lotto.printNumbers();
        }
    }

    private void setWinningNumbers() {
        while(true) {
            try {
                winningNumbers = InputView.inputWinningNumbers();
                validateWinningNumbers(winningNumbers);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + e.getStackTrace()[0]);
            }
        }
    }

    private void setBonusNumber() {
        while (true) {
            try {
                bonusNumber = InputView.inputBonusNumber();
                validateWinningNumbersContainsBonusNumber(winningNumbers, bonusNumber);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + e.getStackTrace()[0]);
            }
        }
    }

    private void printResult() {
        OutputView.printStatistic(getStatistic());
        OutputView.printTotalRateOfReturn(getTotalRateOfReturn());
    }

    private String getStatistic() {
        StringBuilder statistic = new StringBuilder();
        Map<Rank, Integer> rankCounter = getRankCounter();
        for (Rank rank : rankCounter.keySet()) {
            int count = rankCounter.get(rank);
            if (rank != Rank.FAIL) {
                statistic.append(rank.getMessage()).append(" - ").append(count).append("개\n");
            }
        }
        return statistic.toString();
    }

    private double getTotalRateOfReturn() {
        Map<Rank, Integer> rankCounter = getRankCounter();
        long totalPrize = 0;
        for (Rank rank : rankCounter.keySet()) {
            int count = rankCounter.get(rank);
            totalPrize += (long) rank.getPrize() * count;
        }
        return (double) Math.round(totalPrize/(purchaseAmount/1000))/10;
    }

    private Map<Rank, Integer> getRankCounter() {
        Map<Rank, Integer> rankCounter = new EnumMap<>(Rank.class);
        List<Rank> ranks = Arrays.stream(Rank.values()).toList();
        for(Rank rank : ranks) {
            rankCounter.put(rank, 0);
        }
        for (Lotto lotto : issuedLottos) {
            int matchCount = lotto.countMatch(winningNumbers);
            boolean bonusMatched = lotto.isBonusMatched(bonusNumber);
            Rank rank = Rank.valueOf(matchCount, bonusMatched);
            rankCounter.put(rank, rankCounter.get(rank) + 1);
        }
        return rankCounter;
    }


    private void validatePurchaseAmount(int purchaseAmount) {
        validatePurchaseAmountRange(purchaseAmount);
        validatePurchaseAmountMultipleOfThousand(purchaseAmount);
    }

    private void validatePurchaseAmountRange(int purchaseAmount) {
        if (purchaseAmount < 0 || purchaseAmount > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("[ERROR] Out of Integer range");
        }
    }

    private void validatePurchaseAmountMultipleOfThousand(int purchaseAmount) {
        if (purchaseAmount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] Purchase amount isn't multiple of 1,000");
        }
    }

    private void validateWinningNumbers(List<Integer> winningNumbers) {
        validateWinningNumbersRange(winningNumbers);
        validateWinningNumbersDuplicate(winningNumbers);
    }

    private void validateWinningNumbersRange(List<Integer> winningNumbers) {
        if (winningNumbers.size() > LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] Too many winning numbers, winning numbers must be " + LOTTO_SIZE);
        }
        if (winningNumbers.size() < LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] Too less winning numbers, winning numbers must be " + LOTTO_SIZE);
        }
    }

    private void validateWinningNumbersDuplicate(List<Integer> winningNumbers) {
        Set<Integer> duplicateChecker = new HashSet<>();
        for (int i = 0; i < winningNumbers.size(); i++) {
            duplicateChecker.add(winningNumbers.get(i));
        }
        if (duplicateChecker.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("[ERROR] Duplicated number in winning numbers");
        }
    }

    private void validateWinningNumbersContainsBonusNumber(List<Integer> winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] Winning numbers contain bonus number");
        }
    }

}
