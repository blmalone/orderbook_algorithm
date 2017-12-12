package com.cryptofacilities.interview;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Order Book will differentiate between buyers and sellers.
 */
public class OrderBook {

    private String instrument;
    private OrderSide askSide = new OrderSide();
    private OrderSide bidSide = new OrderSide();

    public OrderBook(final String instrument) {
        this.instrument = instrument;
    }

    public void modifyOrder(final String orderId, final long newQuantity) {
        if (askSide.getOrderHashMap().containsKey(orderId)) {
            askSide.modifyOrder(orderId, newQuantity);
        } else {
            bidSide.modifyOrder(orderId, newQuantity);
        }
    }

    public void deleteOrder(final String orderId) {
        if (askSide.getOrderHashMap().containsKey(orderId)) {
            askSide.deleteOrder(orderId);
        } else {
            bidSide.deleteOrder(orderId);
        }
    }

    public long getTotalQuantityAtLevel(Side side, long priceLevel) {
        if (side.equals(Side.buy)) {
            return bidSide.getTradeableQuantityForLevel(priceLevel);
        } else {
            return askSide.getTradeableQuantityForLevel(priceLevel);
        }
    }

    public long getTotalVolumeAtLevel(Side side, long priceLevel) {
        if (side.equals(Side.buy)) {
            return bidSide.getTotalVolumeAtLevel(priceLevel);
        } else {
            return askSide.getTotalVolumeAtLevel(priceLevel);
        }
    }


    public List<Order> getOrdersAtLevel(final Side side, final Long priceLevel) {
        if (side.equals(Side.buy)) {
            return bidSide.getOrdersAtPriceLevel(priceLevel);
        } else {
            return askSide.getOrdersAtPriceLevel(priceLevel);
        }
    }

    public long getNumberOfOrdersAtSide(final Side side) {
        if (side.equals(Side.buy)) {
            return bidSide.getNumberOfOrdersAtSide();
        } else {
            return askSide.getNumberOfOrdersAtSide();
        }
    }

    public long getTotalQuantityAtSide(final Side side) {
        if (side.equals(Side.buy)) {
            return bidSide.getTradeableQuantityOnSide();
        } else {
            return askSide.getTradeableQuantityOnSide();
        }
    }

    public long getTotalVolumeAtSide(final Side side) {
        if (side.equals(Side.buy)) {
            return bidSide.getTotalVolumeOnSide();
        } else {
            return askSide.getTotalVolumeOnSide();
        }
    }

    public List<Order> getOrdersAtSide(final Side side) {
        TreeMap<Long, LinkedList<Order>> pricesTreeMap;
        if (side.equals(Side.buy)) {
            pricesTreeMap = bidSide.getPricesTree();
        } else {
            pricesTreeMap = askSide.getPricesTree();
        }
        LinkedList<Order> allOrdersOnSide = new LinkedList<Order>();
        for (Map.Entry<Long, LinkedList<Order>> entry : pricesTreeMap.entrySet()) {
            for (Order order : entry.getValue()) {
                allOrdersOnSide.add(order);
            }
        }
        return allOrdersOnSide;
    }

    public long getBestPriceForSide(final Side side) {
        if (side.equals(Side.buy)) {
            if (!bidSide.getPricesTree().isEmpty()) {
                return bidSide.getPricesTree().lastKey(); // Highest Bid Price is best
            }
            return 0;
        }
        if (!askSide.getPricesTree().isEmpty()) {
            return askSide.getPricesTree().firstKey(); // Lowest Ask Price is best
        }
        return 0;
    }

    public OrderSide getAskSide() {
        return askSide;
    }

    public OrderSide getBidSide() {
        return bidSide;
    }

}
