import com.google.gson.Gson;

import java.io.*;

public class Basket {
    private String[] products;
    private int[] prices;
    private int[] productPrice;

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getProductPrice() {
        return productPrice;
    }

    public Basket(){

    }
    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.productPrice = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        productPrice[productNum] += amount;
    }
    public  void printCart(){
        int j = 0;
        int sumProducts = 0;
        for (int ii : productPrice) {
            if (ii > 0) {
                sumProducts = sumProducts + productPrice[j] * prices[j];
                System.out.println(products[j] + " " + productPrice[j] + " шт " + prices[j] + " руб/шт " + (productPrice[j] * prices[j] + " руб. в сумме"));
            }
            j = j + 1;
        }
        System.out.println("Итого " + sumProducts + " руб.");
    }

    public void saveTxt(File textFile) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (String product : products) {
                writer.print(product + " ");
            }
            writer.println(" ");
            for (int price : prices) {
                writer.print(price + " ");
            }
            writer.println(" ");
            for (int i : productPrice) {
                writer.print(i + " ");
            }
        }
    }
    public static Basket loadFromTxtFile(File textFile)  {
        Basket basket =new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))){
            String productsStr = bufferedReader.readLine();
            String pricessStr = bufferedReader.readLine();
            String productPriceStr = bufferedReader.readLine();
            basket.products = productsStr.split(" ");

           String[] pricesI = pricessStr.split(" ");
            int[] prices = new int[pricesI.length];
            for (int i = 0; i < pricesI.length; i++) {
                prices[i] = Integer.parseInt(pricesI[i]);
            }
            basket.prices = prices;

            String[] productPriceI = productPriceStr.split(" ");
            int[] productPrice = new int[productPriceI.length];
            for (int i = 0; i < productPriceI.length; i++) {
                    productPrice[i] = Integer.parseInt(productPriceI[i]);
            }
            basket.productPrice = productPrice;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
    public void saveJSON(File File)  {
        try (PrintWriter writer = new PrintWriter(File)) {

            Gson gson = new Gson();
            String json = gson.toJson(this);
            writer.println(json);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static Basket loadFromJSONFile(File File) throws IOException {
        Basket basket = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(File))){
            String productsStr = bufferedReader.readLine();

            Gson gson = new Gson();
            basket = gson.fromJson(productsStr.toString(), Basket.class);
           }
        return basket;
    }
}
