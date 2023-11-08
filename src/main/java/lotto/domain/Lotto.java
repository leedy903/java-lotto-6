package lotto.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        validateDuplicate(numbers);
        validateRange(numbers);
        this.numbers = numbers;
    }

    public void printNumbers() {
        System.out.println(numbers.toString());
    }

    public int countMatch(List<Integer> winningNumbers) {
        int matchCount = 0;
        for (int winningNumber : winningNumbers) {
            if (numbers.contains(winningNumber)) {
                matchCount += 1;
            }
        }
        return matchCount;
    }

    public boolean isBonusMatched(int bonusNumber) {
        return numbers.contains(bonusNumber);
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] Number size must be 6");
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> duplicateChecker = new HashSet<Integer>();
        for (int i = 0; i < numbers.size(); i++) {
            duplicateChecker.add(numbers.get(i));
        }
        if (duplicateChecker.size() != 6) {
            throw new IllegalArgumentException("[ERROR] Duplicated number in lotto numbers > ");
        }
    }

    private void validateRange(List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (0 > numbers.get(i) || numbers.get(i) > 45) {
                throw new IllegalArgumentException("[ERROR] Lotto numbers is out of range > ");
            }
        }
    }
}
