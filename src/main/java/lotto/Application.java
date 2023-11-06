package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        final int lottoPrice = 1000;
        final int lottoSize = 6;

        int purchaseAmount = 0;

        try {
            System.out.println("구입금액을 입력해 주세요.");
            String userInput = Console.readLine().trim();

            purchaseAmount = Integer.parseInt(userInput);
            if (purchaseAmount % lottoPrice != 0) {
                throw new IllegalArgumentException("[ERROR] purchase amount isn't multiple of 1,000");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " -- " + e.getStackTrace()[0]);
        }

        int lottoAmount = purchaseAmount / lottoPrice;

        ArrayList<Lotto> issuedLottos = new ArrayList<Lotto>();
        for (int i = 0; i < lottoAmount; i++) {
            ArrayList<Integer> randomNumbers = new ArrayList<>();
            while (randomNumbers.size() < lottoSize) {
                int randomNumber = Randoms.pickNumberInRange(1, 45);
                if (!randomNumbers.contains(randomNumber)) {
                    randomNumbers.add(randomNumber);
                }
            }
            Collections.sort(randomNumbers);
            Lotto lotto = new Lotto(randomNumbers);
            issuedLottos.add(lotto);
        }

        System.out.println(lottoAmount + "개를 구매했습니다.");
        for (Lotto lotto: issuedLottos) {
            lotto.showNumbers();
        }


        ArrayList<Integer> winningNumbers = new ArrayList<>();


        try {
            System.out.println("당첨 번호를 입력해 주세요.");
            String userInput = Console.readLine().trim();
            List<String> userInputs = Arrays.stream(userInput.split(",")).toList();

            for (int i = 0; winningNumbers.size() < lottoSize; i++) {
                int winningNumber = Integer.parseInt(userInputs.get(i));
                if (winningNumbers.contains(winningNumber)) {
                    throw new IllegalArgumentException("[ERROR] winning numbers have duplication");
                }
                winningNumbers.add(winningNumber);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " -- " + e.getStackTrace()[0]);
        }

        try {
            System.out.println("보너스 번호를 입력해 주세요.");
            String userInput = Console.readLine().trim();

            int bonusNumber = Integer.parseInt(userInput);
            if (winningNumbers.contains(bonusNumber)) {
                throw new IllegalArgumentException("[ERROR] winning numbers contain bonus number");
            }
            winningNumbers.add(bonusNumber);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " -- " + e.getStackTrace()[0]);
        }
    }
}
