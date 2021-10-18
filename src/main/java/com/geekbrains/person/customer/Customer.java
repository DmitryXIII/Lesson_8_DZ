package com.geekbrains.person.customer;

import com.geekbrains.market.Market;
import com.geekbrains.person.Person;
import com.geekbrains.person.salesman.Salesman;
import com.geekbrains.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    List<Product> purchaseList;
    List<Product> expectedPurchaseList;
    String expectedSalesmanName;

    public Customer(int cash) {
        this.purchaseList = new ArrayList<>();
        this.setCash(cash);
    }

    public Customer(String expectedSalesmanName, int cash) {
        this.purchaseList = new ArrayList<>();
        this.setCash(cash);
    }

    public void whatIBoughtInfo() {
        System.out.println();
        StringBuilder builder = new StringBuilder("Я купил ");

        if (this.purchaseList.size() == 0) {
            builder.append("ничего");
        } else {
            for (Product purchase : this.purchaseList) {
                builder.append(purchase.getName());
                builder.append(" в количестве ");
                builder.append(purchase.getCount());
                builder.append(" ");
            }
        }

        builder.append(". У меня осталось: ");
        builder.append(this.getCash());
        builder.append(" рублей");

        System.out.println(builder);
    }

    public void addPurchase(Product product) {
        if (purchaseList == null) {
            purchaseList = new ArrayList<>();
        }

        purchaseList.add(product);
    }

    /**
     * поиск конкретного продавца на рынке
     * если продавец найден покупаем товары из спика только у него
     */
    public boolean findSalesmanOnMarket(Market market, String expectedSalesmanName) {
        boolean isSalesmanFounded = false;
        for (Salesman salesman : market.getSalesmanList()) {
            if (expectedSalesmanName.equals(salesman.getName() + " " + salesman.getSecondName())) {
                System.out.printf("Продавец %s на рынке", expectedSalesmanName);
                isSalesmanFounded = true;
                for (Product product : getExpectedPurchaseList()) {
                    salesman.sellProducts(this, product);
                }
                break;
            }
        }
        if (isSalesmanFounded == false) {
            System.out.printf("У продавца %s сегодня выходной\n", expectedSalesmanName);
            return false;
        }
        return true;
    }


    public void findProductOnMarket(Market market) {
        for (Product product : getExpectedPurchaseList()) {
            for (Salesman salesman : market.getSalesmanList()) {
                boolean isBought = salesman.sellProducts(this, product);
                if (isBought) {
                    break;
                }
            }
        }
    }

    public List<Product> getExpectedPurchaseList() {
        return expectedPurchaseList;
    }

    public void addExpectedPurchaseList(Product product) {
        if (this.expectedPurchaseList == null) {
            this.expectedPurchaseList = new ArrayList<>();
        }
        this.expectedPurchaseList.add(product);
    }


}
