package com.cryptofacilities.interview;

/**
 * Created by CF-8 on 6/27/2017.
 */
public class Order {

    /**
     * unique identifier for the order
     */
    private String orderId;

    /**
     * identifier of an instrument
     */
    private String instrument;

    /**
     * either buy or sell
     */
    private Side side;

    /**
     * limit price for the order, always positive
     */
    private long price;

    /**
     * required quantity, always positive
     */
    private long quantity;

    /**
     * Default ctor
     */
    public Order() {}


    /**
     * Copying ctor
     *
     * @param order an order to make copy from
     */
    public Order(Order order) {
        this( order.orderId, order.instrument, order.side, order.price, order.quantity );
    }


    /**
     * All-values ctor
     *
     * @param orderId unique identifier for the order
     * @param instrument identifier of an instrument
     * @param side either buy or sell
     * @param price limit price for the order, always positive
     * @param quantity required quantity, always positive
     */
    public Order(String orderId, String instrument, Side side, long price, long quantity) {
        this.orderId = orderId;
        this.instrument = instrument;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (price != order.price) return false;
        if (quantity != order.quantity) return false;
        if (orderId != null ? !orderId.equals(order.orderId) : order.orderId != null) return false;
        if (instrument != null ? !instrument.equals(order.instrument) : order.instrument != null) return false;
        return side == order.side;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (instrument != null ? instrument.hashCode() : 0);
        result = 31 * result + (side != null ? side.hashCode() : 0);
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (int) (quantity ^ (quantity >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", instrument='" + instrument + '\'' +
                ", side=" + side +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
