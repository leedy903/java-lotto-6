package lotto.view;

public class OutputView {
    public static void printLottoAmount(int lottoAmount) {
        System.out.println(lottoAmount + "개를 구매했습니다.");
    }

    public static void printStatistic(String statistics) {
        System.out.println("당첨 통계\n---");
        System.out.println(statistics);
    }

    public static void printTotalRateOfReturn(double totalRateOfReturn) {
        System.out.println("총 수익률은 " + String.format("%.1f", totalRateOfReturn) + "%입니다.");
    }

}
