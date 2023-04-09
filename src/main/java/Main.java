
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

        public class Main {
            static String[] products = {"Хлеб", "Яблоки", "Молоко", "Мороженое"};
            static int[] prices = {100, 200, 300, 450};
            static  File jsonFile = new File("basket.json");
            static  File txtFile = new File("basket.txt");
            static   boolean isLoad;
            static String loadFile;
            static    String loadFormat;

            static    boolean isSave;
            static    String saveFile;
            static   String saveFormat;

            static    boolean isLog;
            static    String logFile;
            public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
                File xmlFile = new File("shop.xml");
               XMLSetting(xmlFile);

                Scanner scanner = new Scanner(System.in);
                System.out.println("Список возможных товаров для покупки");
             //   String[] products = {"Хлеб", "Яблоки", "Молоко", "Мороженое"};
            //    int[] prices = {100, 200, 300, 450};
            //    int[] productPrice = new int[4];
                Basket basket = null;

                basket = getBasket(null);

                for (int i = 0; i < products.length; i++) {
                    System.out.println((i + 1) + ". " + products[i] + "  " + prices[i] + " руб/шт");
                }
                ClientLog log = new ClientLog();
                while (true) {
                    System.out.println("Выберите товар и количество или введите `end`");

                    String input = scanner.nextLine();
                    if ("end".equals(input)) {
                        System.out.println("Программа завершена!");
                        System.out.println("Ваша корзина:");
                        if(isLog){
                        log.exportAsCSV(new File(logFile));
                        }
                        break;
                    }
                    String[] parts = input.split(" ");
                    int productNumber = Integer.parseInt(parts[0]) - 1;
                    int productCount = Integer.parseInt(parts[1]);
                    basket.addToCart(productNumber, productCount);
                    log.log(productNumber+1, productCount);
                    if (isSave){
                        switch (saveFormat){
                            case "json": basket.saveJSON(jsonFile);
                            break;
                            case "txt":basket.saveTxt(txtFile);
                            break;
                        }
                    }

                }
                basket.printCart();
            }

            private static Basket getBasket(Basket basket) {
                if (isLoad && new File(loadFile).exists()) {
                    switch (loadFormat) {
                        case "json":
                            basket = Basket.loadFromJSONFile(jsonFile);
                            break;
                        case "txt":
                            basket = Basket.loadFromTxtFile(txtFile);
                            break;
                    }

                } else {  basket = new Basket(products, prices);
                    }
                return basket;
            }

            public static void XMLSetting(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(xmlFile);

                Element root = doc.getDocumentElement();
//
                Element loadSettings = (Element) root.getElementsByTagName("load").item(0);
                Element saveSettings = (Element) root.getElementsByTagName("save").item(0);
                Element logSettings = (Element) root.getElementsByTagName("log").item(0);

                isLoad = Boolean.parseBoolean(loadSettings.getElementsByTagName("enabled").item(0).getTextContent());
                loadFile = loadSettings.getElementsByTagName("fileName").item(0).getTextContent();
                loadFormat = loadSettings.getElementsByTagName("format").item(0).getTextContent();

                isSave = Boolean.parseBoolean(saveSettings.getElementsByTagName("enabled").item(0).getTextContent());
                saveFile = saveSettings.getElementsByTagName("fileName").item(0).getTextContent();
                saveFormat = saveSettings.getElementsByTagName("format").item(0).getTextContent();

                isLog = Boolean.parseBoolean(logSettings.getElementsByTagName("enabled").item(0).getTextContent());
                logFile = logSettings.getElementsByTagName("fileName").item(0).getTextContent();

            }
        }
