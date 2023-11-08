package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import lotto.utils.Utils;

public class InputView {

    public static int inputPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
        String userInput = Console.readLine().trim();
        validateUserInput(userInput);
        return Utils.stringToInt(userInput);
    }


    public static List<Integer> inputWinningNumbers() {
        System.out.println("당첨 번호를 입력해 주세요.");
        String userInput = Console.readLine().trim();
        validateUserInput(userInput);
        List<String> userInputs = Arrays.stream(userInput.split(",", -1)).toList();
        return Utils.stringListToInt(userInputs);
    }

    public static int inputBonusNumber() {
        System.out.println("보너스 번호를 입력해 주세요.");
        String userInput = Console.readLine().trim();
        validateUserInput(userInput);
        return Utils.stringToInt(userInput);
    }

    private static void validateUserInput(String userInput) {
        validateUserInputLength(userInput);
    }

    private static void validateUserInputLength(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            throw new IllegalArgumentException("[ERROR] There are no user input > ");
        }
    }
}
