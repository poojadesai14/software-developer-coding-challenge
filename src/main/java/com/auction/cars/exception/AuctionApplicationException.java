package com.auction.cars.exception;

public class AuctionApplicationException extends RuntimeException   {

    private static final long serialVersionUID = -7530593011134270615L;

    private String errorMessage;
    private int code;

    public AuctionApplicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    /**
     * PricingException.
     * @param errorMessage errorMessage
     * @param code code
     */
    public AuctionApplicationException(String errorMessage, int code) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.code = code;
    }


}
