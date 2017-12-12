package com.cryptofacilities.interview;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * OrderSide will model the different sides of an OrderBook. Representing both Buyers (bids) and Sellers (asks).
 */
public class OrderSide {

    private TreeMap<Long, LinkedList<Order>> pricesTree = new TreeMap<Long, LinkedList<Order>>();
    private HashMap<Long, LinkedList<Order>> pricesHashMap = new HashMap<Long, LinkedList<Order>>();
    private HashMap<String, Order> orderHashMap = new HashMap<String, Order>();
    private long numberOfOrdersAtSide;
    private long tradeableQuantityOnSide;
    private long totalVolumeOnSide;

    public OrderSide() {
        pricesTree.clear();
        pricesHashMap.clear();
        orderHashMap.clear();
        numberOfOrdersAtSide = 0;
        tradeableQuantityOnSide = 0;
        totalVolumeOnSide = 0;
    }

    public void addNewOrder(final Order order) {
        String orderId = order.getOrderId();
        if(!orderHashMap.containsKey(orderId)) { //If order doesn't exist then add it.
            Long priceLevel = order.getPrice();
            if(!pricesHashMap.containsKey(priceLevel)) { //Adding a new price level to PriceTree.
                LinkedList<Order> newOrdersList = new LinkedList<Order>();
                newOrdersList.add(order);
                pricesTree.put(priceLevel, newOrdersList); //Fast retrieval of best bid and ask prices.
                pricesHashMap.put(priceLevel, newOrdersList); // Fast retrieval of prices operations
                orderHashMap.put(orderId, order); // Fast order operations
            } else {
                orderHashMap.put(orderId, order);
                LinkedList<Order> orders = pricesHashMap.get(priceLevel);
                orders.addLast(order);
                pricesHashMap.put(priceLevel, orders);
            }
            numberOfOrdersAtSide+=1;
            tradeableQuantityOnSide += order.getQuantity();
            totalVolumeOnSide = totalVolumeOnSide + (priceLevel * order.getQuantity());
        }
    }

    public void modifyOrder(final String orderId, final long newQuantity) {
        Order order = orderHashMap.get(orderId);
        if (order != null && order.getQuantity() != newQuantity) {
            long oldQuantity = order.getQuantity();
            long priceLevel = order.getPrice();
            int indexTree = pricesTree.get(priceLevel).indexOf(order);
            int indexMap = pricesTree.get(priceLevel).indexOf(order);
            order.setQuantity(newQuantity);
            if (newQuantity < oldQuantity) { //Order maintains it's position in price level
                pricesTree.get(priceLevel).set(indexTree,order);
                pricesHashMap.get(priceLevel).set(indexMap, order);
                orderHashMap.put(orderId, order);
            } else {
                pricesHashMap.get(priceLevel).remove(indexMap);
                pricesHashMap.get(priceLevel).addLast(order);
            }
            tradeableQuantityOnSide = (tradeableQuantityOnSide - oldQuantity) + newQuantity;
            totalVolumeOnSide = (totalVolumeOnSide - (priceLevel * oldQuantity)) + (priceLevel * newQuantity);
        }

    }

    public void deleteOrder(final String orderId) {
        final Order orderToDelete = orderHashMap.get(orderId);
        if (orderToDelete != null) {
            long quantity = orderToDelete.getQuantity();
            long priceLevel = orderToDelete.getPrice();
            numberOfOrdersAtSide-=1;
            tradeableQuantityOnSide -= quantity;
            long orderVolume =  priceLevel * quantity;
            totalVolumeOnSide -= orderVolume;
            pricesTree.get(priceLevel).remove(orderToDelete);
            pricesHashMap.get(priceLevel).remove(orderToDelete);
            if (pricesHashMap.get(priceLevel).size() == 0) {
                pricesTree.remove(priceLevel);
                pricesHashMap.remove(priceLevel);
            }
            orderHashMap.remove(orderId);
        }
    }

    public long getTradeableQuantityForLevel(final long priceLevel) {
        long result = 0;
        for (Order order : pricesHashMap.get(priceLevel)) {
            result += order.getQuantity();
        }
        return result;
    }

    public LinkedList<Order> getOrdersAtPriceLevel(final Long priceLevel) {
        return pricesHashMap.get(priceLevel);
    }

    public long getTotalVolumeAtLevel(final long priceLevel) {
        long totalVolume = 0;
        for (Order order : pricesHashMap.get(priceLevel)) {
            totalVolume = totalVolume + (priceLevel * order.getQuantity());
        }
        return totalVolume;
    }

    public TreeMap<Long, LinkedList<Order>> getPricesTree() {
        return pricesTree;
    }

    public HashMap<String, Order> getOrderHashMap() {
        return orderHashMap;
    }

    public long getNumberOfOrdersAtSide() {
        return numberOfOrdersAtSide;
    }

    public long getTradeableQuantityOnSide() {
        return tradeableQuantityOnSide;
    }

    public long getTotalVolumeOnSide() {
        return totalVolumeOnSide;
    }
}
