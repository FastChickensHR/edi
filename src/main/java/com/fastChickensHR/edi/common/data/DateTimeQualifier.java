/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.common.data;

import com.fastChickensHR.edi.common.EdiCodeEnum;
import com.fastChickensHR.edi.common.EdiEnumLookup;
import lombok.Getter;

import java.util.Map;

/**
 * Enumeration representing Date/Time Qualifiers used in EDI transactions.
 * These codes indicate the purpose or context of a date or date/time element.
 */
@Getter
public enum DateTimeQualifier implements EdiCodeEnum {
    CANCEL_AFTER("001", "Cancel After"),
    DELIVERY_REQUESTED("002", "Delivery Requested"),
    INVOICE("003", "Invoice"),
    PURCHASE_ORDER("004", "Purchase Order"),
    SAILING("005", "Sailing"),
    SOLD("006", "Sold"),
    EFFECTIVE("007", "Effective"),
    PURCHASE_ORDER_RECEIVED("008", "Purchase Order Received"),
    PROCESS("009", "Process"),
    REQUESTED_SHIP("010", "Requested Ship"),
    SHIPPED("011", "Shipped"),
    TERMS_DISCOUNT_DUE("012", "Terms Discount Due"),
    TERMS_NET_DUE("013", "Terms Net Due"),
    DEFERRED_PAYMENT("014", "Deferred Payment"),
    PROMOTION_START("015", "Promotion Start"),
    PROMOTION_END("016", "Promotion End"),
    ESTIMATED_DELIVERY("017", "Estimated Delivery"),
    AVAILABLE("018", "Available"),
    UNLOADED("019", "Unloaded"),
    CHECK("020", "Check"),
    CHARGE_BACK("021", "Charge Back"),
    FREIGHT_BILL("022", "Freight Bill"),
    PROMOTION_ORDER_START("023", "Promotion Order - Start"),
    PROMOTION_ORDER_END("024", "Promotion Order - End"),
    PROMOTION_SHIP_START("025", "Promotion Ship - Start"),
    PROMOTION_SHIP_END("026", "Promotion Ship - End"),
    PROMOTION_REQUESTED_DELIVERY_START("027", "Promotion Requested Delivery - Start"),
    PROMOTION_REQUESTED_DELIVERY_END("028", "Promotion Requested Delivery - End"),
    PROMOTION_PERFORMANCE_START("029", "Promotion Performance - Start"),
    PROMOTION_PERFORMANCE_END("030", "Promotion Performance - End"),
    PROMOTION_INVOICE_PERFORMANCE_START("031", "Promotion Invoice Performance - Start"),
    PROMOTION_INVOICE_PERFORMANCE_END("032", "Promotion Invoice Performance - End"),
    PROMOTION_FLOOR_STOCK_PROTECT_START("033", "Promotion Floor Stock Protect - Start"),
    PROMOTION_FLOOR_STOCK_PROTECT_END("034", "Promotion Floor Stock Protect - End"),
    DELIVERED("035", "Delivered"),
    EXPIRATION_DATE("036", "Expiration Date coverage expires"),
    SHIP_NOT_BEFORE("037", "Ship Not Before"),
    SHIP_NO_LATER("038", "Ship No Later"),
    SHIP_WEEK_OF("039", "Ship Week of"),
    STATUS_AFTER_AND_INCLUDING("040", "Status (After and Including)"),
    STATUS_PRIOR_AND_INCLUDING("041", "Status (Prior and Including)"),
    SUPERSEDED("042", "Superseded"),
    PUBLICATION("043", "Publication"),
    SETTLEMENT_DATE("044", "Settlement Date as Specified by the Originator"),
    ENDORSEMENT_DATE("045", "Endorsement Date"),
    FIELD_FAILURE("046", "Field Failure"),
    FUNCTIONAL_TEST("047", "Functional Test"),
    SYSTEM_TEST("048", "System Test"),
    PROTOTYPE_TEST("049", "Prototype Test"),
    RECEIVED("050", "Received"),
    CUMULATIVE_QUANTITY_START("051", "Cumulative Quantity Start"),
    CUMULATIVE_QUANTITY_END("052", "Cumulative Quantity End"),
    BUYERS_LOCAL("053", "Buyers Local"),
    SELLERS_LOCAL("054", "Sellers Local"),
    CONFIRMED("055", "Confirmed"),
    ESTIMATED_PORT_OF_ENTRY("056", "Estimated Port of Entry"),
    ACTUAL_PORT_OF_ENTRY("057", "Actual Port of Entry"),
    CUSTOMS_CLEARANCE("058", "Customs Clearance"),
    INLAND_SHIP("059", "Inland Ship"),
    ENGINEERING_CHANGE_LEVEL("060", "Engineering Change Level"),
    CANCEL_IF_NOT_DELIVERED_BY("061", "Cancel if Not Delivered by"),
    BLUEPRINT("062", "Blueprint"),
    DO_NOT_DELIVER_AFTER("063", "Do Not Deliver After"),
    DO_NOT_DELIVER_BEFORE("064", "Do Not Deliver Before"),
    FIRST_SCHEDULE_DELIVERY("065", "1st Schedule Delivery"),
    FIRST_SCHEDULE_SHIP("066", "1st Schedule Ship"),
    CURRENT_SCHEDULE_DELIVERY("067", "Current Schedule Delivery"),
    CURRENT_SCHEDULE_SHIP("068", "Current Schedule Ship"),
    PROMISED_FOR_DELIVERY("069", "Promised for Delivery"),
    SCHEDULED_FOR_DELIVERY_AFTER("070", "Scheduled for Delivery (After and Including)"),
    REQUESTED_FOR_DELIVERY_AFTER("071", "Requested for Delivery (After and Including)"),
    PROMISED_FOR_DELIVERY_AFTER("072", "Promised for Delivery (After and Including)"),
    SCHEDULED_FOR_DELIVERY_PRIOR("073", "Scheduled for Delivery (Prior to and Including)"),
    REQUESTED_FOR_DELIVERY_PRIOR("074", "Requested for Delivery (Prior to and Including)"),
    PROMISED_FOR_DELIVERY_PRIOR("075", "Promised for Delivery (Prior to and Including)"),
    SCHEDULED_FOR_DELIVERY_WEEK("076", "Scheduled for Delivery (Week of)"),
    REQUESTED_FOR_DELIVERY_WEEK("077", "Requested for Delivery (Week of)"),
    PROMISED_FOR_DELIVERY_WEEK("078", "Promised for Delivery (Week of)"),
    PROMISED_FOR_SHIPMENT("079", "Promised for Shipment"),
    SCHEDULED_FOR_SHIPMENT_AFTER("080", "Scheduled for Shipment (After and Including)"),
    REQUESTED_FOR_SHIPMENT_AFTER("081", "Requested for Shipment (After and Including)"),
    PROMISED_FOR_SHIPMENT_AFTER("082", "Promised for Shipment (After and Including)"),
    SCHEDULED_FOR_SHIPMENT_PRIOR("083", "Scheduled for Shipment (Prior to and Including)"),
    REQUESTED_FOR_SHIPMENT_PRIOR("084", "Requested for Shipment (Prior to and Including)"),
    PROMISED_FOR_SHIPMENT_PRIOR("085", "Promised for Shipment (Prior to and Including)"),
    SCHEDULED_FOR_SHIPMENT_WEEK("086", "Scheduled for Shipment (Week of)"),
    REQUESTED_FOR_SHIPMENT_WEEK("087", "Requested for Shipment (Week of)"),
    PROMISED_FOR_SHIPMENT_WEEK("088", "Promised for Shipment (Week of)"),
    INQUIRY("089", "Inquiry"),
    REPORT_START("090", "Report Start"),
    REPORT_END("091", "Report End"),
    CONTRACT_EFFECTIVE("092", "Contract Effective"),
    CONTRACT_EXPIRATION("093", "Contract Expiration"),
    MANUFACTURE("094", "Manufacture"),
    BILL_OF_LADING("095", "Bill of Lading"),
    DISCHARGE("096", "Discharge"),
    TRANSACTION_CREATION("097", "Transaction Creation"),
    BID_EFFECTIVE("098", "Bid (Effective)"),
    BID_OPEN("099", "Bid Open (Date Bids Will Be Opened)"),
    NO_SHIPPING_SCHEDULE("100", "No Shipping Schedule Established as of");

    private final String code;
    private final String description;
    private static final EdiEnumLookup<DateTimeQualifier> LOOKUP;

    static {
        LOOKUP = new EdiEnumLookup<>(
                DateTimeQualifier.class,
                "Date/Time Qualifier",
                Map.ofEntries(
                        Map.entry("1", CANCEL_AFTER),
                        Map.entry("2", DELIVERY_REQUESTED),
                        Map.entry("3", INVOICE),
                        Map.entry("4", PURCHASE_ORDER),
                        Map.entry("5", SAILING),
                        Map.entry("6", SOLD),
                        Map.entry("7", EFFECTIVE),
                        Map.entry("8", PURCHASE_ORDER_RECEIVED),
                        Map.entry("9", PROCESS),
                        Map.entry("10", REQUESTED_SHIP),

                        Map.entry("invoice date", INVOICE),
                        Map.entry("po date", PURCHASE_ORDER),
                        Map.entry("po received", PURCHASE_ORDER_RECEIVED),
                        Map.entry("ship date", SHIPPED),
                        Map.entry("ship request", REQUESTED_SHIP),
                        Map.entry("delivery date", DELIVERY_REQUESTED),
                        Map.entry("effective date", EFFECTIVE),
                        Map.entry("contract date", CONTRACT_EFFECTIVE),
                        Map.entry("expiration", EXPIRATION_DATE),
                        Map.entry("expires", EXPIRATION_DATE),
                        Map.entry("cancel date", CANCEL_AFTER),
                        Map.entry("received date", RECEIVED),
                        Map.entry("est delivery", ESTIMATED_DELIVERY),
                        Map.entry("estimated delivery", ESTIMATED_DELIVERY),
                        Map.entry("promised delivery", PROMISED_FOR_DELIVERY),
                        Map.entry("manufacture date", MANUFACTURE),
                        Map.entry("bill of lading date", BILL_OF_LADING),
                        Map.entry("bol date", BILL_OF_LADING)
                )
        );
    }

    DateTimeQualifier(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets a DateTimeQualifier instance from any input string.
     * Matches against codes, names, descriptions, and common variations.
     *
     * @param input the string to look up
     * @return the matching DateTimeQualifier
     * @throws IllegalArgumentException if no match is found
     */
    public static DateTimeQualifier fromString(String input) {
        return LOOKUP.fromString(input);
    }

    @Override
    public String toString() {
        return code;
    }
}