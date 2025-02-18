package model;

public class Voucher {
    private String voucherCode;
    private int discountAmount;
    private String validUntil;

    public Voucher(String voucherCode, int discountAmount, String validUntil) {
        this.voucherCode = voucherCode;
        this.discountAmount = discountAmount;
        this.validUntil = validUntil;
    }

    // Getters and Setters
    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
}
