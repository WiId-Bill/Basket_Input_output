
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

        public class Main {
            static String[] products = {"Хлеб", "Яблоки", "Молоко", "Мороженое"};
            static int[] prices = {100, 200, 300, 450};
            static  File saveFile = new File("basket.bin");
            public static void main(String[] args) throws Exception {

                Scanner scanner = new Scanner(System.in);
                System.out.println("Список возможных товаров для покупки");

                Basket basket = null;
                if (saveFile.exists()) {
                    basket = Basket.loadFromBinFile(saveFile);
                } else {  basket = new Basket(products, prices);
                }

                for (int i = 0; i < products.length; i++) {
                    System.out.println((i + 1) + ". " + products[i] + "  " + prices[i] + " руб/шт");
                }

                while (true) {
                    System.out.println("Выберите товар и количество или введите `end`");

                    String input = scanner.nextLine();
                    if ("end".equals(input)) {
                        System.out.println("Программа завершена!");
                        System.out.println("Ваша корзина:");
                        break;
                    }
                    String[] parts = input.split(" ");
                    int productNumber = Integer.parseInt(parts[0]) - 1;
                    int productCount = Integer.parseInt(parts[1]);
                    basket.addToCart(productNumber, productCount);
                    basket.saveBin(saveFile);
                }
                basket.printCart();
            }
        }
