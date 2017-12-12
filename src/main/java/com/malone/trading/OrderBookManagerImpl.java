package com.malone.trading;

import java.util.HashMap;
import java.util.List;

public class OrderBookManagerImpl implements OrderBookManager {

    /*
        Only one order book for this assignment. In reality there would be multiple order books.
        Each dedicated to it's own separate instrument.
     */
    private HashMap<String, OrderBook> orderBooks = new HashMap<String, OrderBook>() {{
       put("BTC", new OrderBook("BTC"));
    }};

    public void addOrder(final Order order) {
        if (order != null && order.getQuantity() > 0 && order.getPrice() > 0) { //Assuming everything has a price > 0
            OrderBook orderBook = orderBooks.get(order.getInstrument());
            if (orderBook != null) {
                if (order.getSide().equals(Side.buy)) {
                    final OrderSide bidSide = orderBook.getBidSide();
                    bidSide.addNewOrder(order);
                } else {
                    final OrderSide askSide = orderBook.getAskSide();
                    askSide.addNewOrder(order);
                }
            }
        }
    }

    public void modifyOrder(final String orderId, final long newQuantity) {
        if (orderId != null && newQuantity > 0) {
            final OrderBook btcOrderBook = orderBooks.get("BTC");
            btcOrderBook.modifyOrder(orderId, newQuantity);
        }
    }

    public void deleteOrder(final String orderId) {
        if (orderId != null) {
            final OrderBook btcOrderBook = orderBooks.get("BTC");
            btcOrderBook.deleteOrder(orderId);
        }
    }

    public long getBestPrice(final String instrument, final Side side) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getBestPriceForSide(side);
        }
        return 0;
    }

    public long getOrderNumAtLevel(final String instrument, final Side side, final long price) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            final List<Order> ordersAtLevel = orderBook.getOrdersAtLevel(side, price);
            return ordersAtLevel != null ? ordersAtLevel.size() : 0;
        }
        return 0;
    }

    public long getTotalQuantityAtLevel(final String instrument, final Side side, final long price) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getTotalQuantityAtLevel(side, price);
        }
        return 0;
    }

    public long getTotalVolumeAtLevel(final String instrument, final Side side, final long price) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getTotalVolumeAtLevel(side, price);
        }
        return 0;
    }

    public List<Order> getOrdersAtLevel(final String instrument, final Side side, final long price) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getOrdersAtLevel(side, price);
        }
        return null;
    }

    public long getTotalQuantityAtSide(final String instrument, final Side side) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getTotalQuantityAtSide(side);
        }
        return 0;
    }

    public long getTotalVolumeAtSide(final String instrument, final Side side) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getTotalVolumeAtSide(side);
        }
        return 0;
    }

    public long getOrderNumAtSide(final String instrument, final Side side) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getNumberOfOrdersAtSide(side);
        }
        return 0;
    }

    public List<Order> getOrdersAtSide(final String instrument, final Side side) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getOrdersAtSide(side);
        }
        return null;
    }

}