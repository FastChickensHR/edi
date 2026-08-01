/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.data;

import com.fastChickensHR.edi.x834.util.EdiCodeEnum;
import com.fastChickensHR.edi.x834.util.EdiEnumLookup;
import java.util.Map;
import lombok.Getter;

/**
 * Code values for the X12 Date/Time Qualifier (data element 374), which states the meaning of an
 * accompanying date, time, or date/time period. In the X12 834 (005010X220A1) it appears in DTP
 * segments (DTP01) that carry dates such as coverage, eligibility, and employment periods.
 *
 * <p>No default value is defined for this element. {@link #fromString(String)} resolves a value
 * from its code, enum name, description, or a common synonym, and throws {@link
 * IllegalArgumentException} when the input matches none.
 */
@Getter
public enum DateTimeQualifier implements EdiCodeEnum {
  /** Cancel After — X12 code "001". */
  CANCEL_AFTER("001", "Cancel After"),
  /** Delivery Requested — X12 code "002". */
  DELIVERY_REQUESTED("002", "Delivery Requested"),
  /** Invoice — X12 code "003". */
  INVOICE("003", "Invoice"),
  /** Purchase Order — X12 code "004". */
  PURCHASE_ORDER("004", "Purchase Order"),
  /** Sailing — X12 code "005". */
  SAILING("005", "Sailing"),
  /** Sold — X12 code "006". */
  SOLD("006", "Sold"),
  /** Effective — X12 code "007". */
  EFFECTIVE("007", "Effective"),
  /** Purchase Order Received — X12 code "008". */
  PURCHASE_ORDER_RECEIVED("008", "Purchase Order Received"),
  /** Process — X12 code "009". */
  PROCESS("009", "Process"),
  /** Requested Ship — X12 code "010". */
  REQUESTED_SHIP("010", "Requested Ship"),
  /** Shipped — X12 code "011". */
  SHIPPED("011", "Shipped"),
  /** Terms Discount Due — X12 code "012". */
  TERMS_DISCOUNT_DUE("012", "Terms Discount Due"),
  /** Terms Net Due — X12 code "013". */
  TERMS_NET_DUE("013", "Terms Net Due"),
  /** Deferred Payment — X12 code "014". */
  DEFERRED_PAYMENT("014", "Deferred Payment"),
  /** Promotion Start — X12 code "015". */
  PROMOTION_START("015", "Promotion Start"),
  /** Promotion End — X12 code "016". */
  PROMOTION_END("016", "Promotion End"),
  /** Estimated Delivery — X12 code "017". */
  ESTIMATED_DELIVERY("017", "Estimated Delivery"),
  /** Available — X12 code "018". */
  AVAILABLE("018", "Available"),
  /** Unloaded — X12 code "019". */
  UNLOADED("019", "Unloaded"),
  /** Check — X12 code "020". */
  CHECK("020", "Check"),
  /** Charge Back — X12 code "021". */
  CHARGE_BACK("021", "Charge Back"),
  /** Freight Bill — X12 code "022". */
  FREIGHT_BILL("022", "Freight Bill"),
  /** Promotion Order - Start — X12 code "023". */
  PROMOTION_ORDER_START("023", "Promotion Order - Start"),
  /** Promotion Order - End — X12 code "024". */
  PROMOTION_ORDER_END("024", "Promotion Order - End"),
  /** Promotion Ship - Start — X12 code "025". */
  PROMOTION_SHIP_START("025", "Promotion Ship - Start"),
  /** Promotion Ship - End — X12 code "026". */
  PROMOTION_SHIP_END("026", "Promotion Ship - End"),
  /** Promotion Requested Delivery - Start — X12 code "027". */
  PROMOTION_REQUESTED_DELIVERY_START("027", "Promotion Requested Delivery - Start"),
  /** Promotion Requested Delivery - End — X12 code "028". */
  PROMOTION_REQUESTED_DELIVERY_END("028", "Promotion Requested Delivery - End"),
  /** Promotion Performance - Start — X12 code "029". */
  PROMOTION_PERFORMANCE_START("029", "Promotion Performance - Start"),
  /** Promotion Performance - End — X12 code "030". */
  PROMOTION_PERFORMANCE_END("030", "Promotion Performance - End"),
  /** Promotion Invoice Performance - Start — X12 code "031". */
  PROMOTION_INVOICE_PERFORMANCE_START("031", "Promotion Invoice Performance - Start"),
  /** Promotion Invoice Performance - End — X12 code "032". */
  PROMOTION_INVOICE_PERFORMANCE_END("032", "Promotion Invoice Performance - End"),
  /** Promotion Floor Stock Protect - Start — X12 code "033". */
  PROMOTION_FLOOR_STOCK_PROTECT_START("033", "Promotion Floor Stock Protect - Start"),
  /** Promotion Floor Stock Protect - End — X12 code "034". */
  PROMOTION_FLOOR_STOCK_PROTECT_END("034", "Promotion Floor Stock Protect - End"),
  /** Delivered — X12 code "035". */
  DELIVERED("035", "Delivered"),
  /** Expiration Date coverage expires — X12 code "036". */
  EXPIRATION_DATE("036", "Expiration Date coverage expires"),
  /** Ship Not Before — X12 code "037". */
  SHIP_NOT_BEFORE("037", "Ship Not Before"),
  /** Ship No Later — X12 code "038". */
  SHIP_NO_LATER("038", "Ship No Later"),
  /** Ship Week of — X12 code "039". */
  SHIP_WEEK_OF("039", "Ship Week of"),
  /** Status (After and Including) — X12 code "040". */
  STATUS_AFTER_AND_INCLUDING("040", "Status (After and Including)"),
  /** Status (Prior and Including) — X12 code "041". */
  STATUS_PRIOR_AND_INCLUDING("041", "Status (Prior and Including)"),
  /** Superseded — X12 code "042". */
  SUPERSEDED("042", "Superseded"),
  /** Publication — X12 code "043". */
  PUBLICATION("043", "Publication"),
  /** Settlement Date as Specified by the Originator — X12 code "044". */
  SETTLEMENT_DATE_BY_ORIGINATOR("044", "Settlement Date as Specified by the Originator"),
  /** Endorsement Date — X12 code "045". */
  ENDORSEMENT_DATE("045", "Endorsement Date"),
  /** Field Failure — X12 code "046". */
  FIELD_FAILURE("046", "Field Failure"),
  /** Functional Test — X12 code "047". */
  FUNCTIONAL_TEST("047", "Functional Test"),
  /** System Test — X12 code "048". */
  SYSTEM_TEST("048", "System Test"),
  /** Prototype Test — X12 code "049". */
  PROTOTYPE_TEST("049", "Prototype Test"),
  /** Received — X12 code "050". */
  RECEIVED("050", "Received"),
  /** Cumulative Quantity Start — X12 code "051". */
  CUMULATIVE_QUANTITY_START("051", "Cumulative Quantity Start"),
  /** Cumulative Quantity End — X12 code "052". */
  CUMULATIVE_QUANTITY_END("052", "Cumulative Quantity End"),
  /** Buyers Local — X12 code "053". */
  BUYERS_LOCAL("053", "Buyers Local"),
  /** Sellers Local — X12 code "054". */
  SELLERS_LOCAL("054", "Sellers Local"),
  /** Confirmed — X12 code "055". */
  CONFIRMED("055", "Confirmed"),
  /** Estimated Port of Entry — X12 code "056". */
  ESTIMATED_PORT_OF_ENTRY("056", "Estimated Port of Entry"),
  /** Actual Port of Entry — X12 code "057". */
  ACTUAL_PORT_OF_ENTRY("057", "Actual Port of Entry"),
  /** Customs Clearance — X12 code "058". */
  CUSTOMS_CLEARANCE("058", "Customs Clearance"),
  /** Inland Ship — X12 code "059". */
  INLAND_SHIP("059", "Inland Ship"),
  /** Engineering Change Level — X12 code "060". */
  ENGINEERING_CHANGE_LEVEL("060", "Engineering Change Level"),
  /** Cancel if Not Delivered by — X12 code "061". */
  CANCEL_IF_NOT_DELIVERED_BY("061", "Cancel if Not Delivered by"),
  /** Blueprint — X12 code "062". */
  BLUEPRINT("062", "Blueprint"),
  /** Do Not Deliver After — X12 code "063". */
  DO_NOT_DELIVER_AFTER("063", "Do Not Deliver After"),
  /** Do Not Deliver Before — X12 code "064". */
  DO_NOT_DELIVER_BEFORE("064", "Do Not Deliver Before"),
  /** 1st Schedule Delivery — X12 code "065". */
  FIRST_SCHEDULE_DELIVERY("065", "1st Schedule Delivery"),
  /** 1st Schedule Ship — X12 code "066". */
  FIRST_SCHEDULE_SHIP("066", "1st Schedule Ship"),
  /** Current Schedule Delivery — X12 code "067". */
  CURRENT_SCHEDULE_DELIVERY("067", "Current Schedule Delivery"),
  /** Current Schedule Ship — X12 code "068". */
  CURRENT_SCHEDULE_SHIP("068", "Current Schedule Ship"),
  /** Promised for Delivery — X12 code "069". */
  PROMISED_FOR_DELIVERY("069", "Promised for Delivery"),
  /** Scheduled for Delivery (After and Including) — X12 code "070". */
  SCHEDULED_FOR_DELIVERY_AFTER("070", "Scheduled for Delivery (After and Including)"),
  /** Requested for Delivery (After and Including) — X12 code "071". */
  REQUESTED_FOR_DELIVERY_AFTER("071", "Requested for Delivery (After and Including)"),
  /** Promised for Delivery (After and Including) — X12 code "072". */
  PROMISED_FOR_DELIVERY_AFTER("072", "Promised for Delivery (After and Including)"),
  /** Scheduled for Delivery (Prior to and Including) — X12 code "073". */
  SCHEDULED_FOR_DELIVERY_PRIOR("073", "Scheduled for Delivery (Prior to and Including)"),
  /** Requested for Delivery (Prior to and Including) — X12 code "074". */
  REQUESTED_FOR_DELIVERY_PRIOR("074", "Requested for Delivery (Prior to and Including)"),
  /** Promised for Delivery (Prior to and Including) — X12 code "075". */
  PROMISED_FOR_DELIVERY_PRIOR("075", "Promised for Delivery (Prior to and Including)"),
  /** Scheduled for Delivery (Week of) — X12 code "076". */
  SCHEDULED_FOR_DELIVERY_WEEK("076", "Scheduled for Delivery (Week of)"),
  /** Requested for Delivery (Week of) — X12 code "077". */
  REQUESTED_FOR_DELIVERY_WEEK("077", "Requested for Delivery (Week of)"),
  /** Promised for Delivery (Week of) — X12 code "078". */
  PROMISED_FOR_DELIVERY_WEEK("078", "Promised for Delivery (Week of)"),
  /** Promised for Shipment — X12 code "079". */
  PROMISED_FOR_SHIPMENT("079", "Promised for Shipment"),
  /** Scheduled for Shipment (After and Including) — X12 code "080". */
  SCHEDULED_FOR_SHIPMENT_AFTER("080", "Scheduled for Shipment (After and Including)"),
  /** Requested for Shipment (After and Including) — X12 code "081". */
  REQUESTED_FOR_SHIPMENT_AFTER("081", "Requested for Shipment (After and Including)"),
  /** Promised for Shipment (After and Including) — X12 code "082". */
  PROMISED_FOR_SHIPMENT_AFTER("082", "Promised for Shipment (After and Including)"),
  /** Scheduled for Shipment (Prior to and Including) — X12 code "083". */
  SCHEDULED_FOR_SHIPMENT_PRIOR("083", "Scheduled for Shipment (Prior to and Including)"),
  /** Requested for Shipment (Prior to and Including) — X12 code "084". */
  REQUESTED_FOR_SHIPMENT_PRIOR("084", "Requested for Shipment (Prior to and Including)"),
  /** Promised for Shipment (Prior to and Including) — X12 code "085". */
  PROMISED_FOR_SHIPMENT_PRIOR("085", "Promised for Shipment (Prior to and Including)"),
  /** Scheduled for Shipment (Week of) — X12 code "086". */
  SCHEDULED_FOR_SHIPMENT_WEEK("086", "Scheduled for Shipment (Week of)"),
  /** Requested for Shipment (Week of) — X12 code "087". */
  REQUESTED_FOR_SHIPMENT_WEEK("087", "Requested for Shipment (Week of)"),
  /** Promised for Shipment (Week of) — X12 code "088". */
  PROMISED_FOR_SHIPMENT_WEEK("088", "Promised for Shipment (Week of)"),
  /** Inquiry — X12 code "089". */
  INQUIRY("089", "Inquiry"),
  /** Report Start — X12 code "090". */
  REPORT_START("090", "Report Start"),
  /** Report End — X12 code "091". */
  REPORT_END("091", "Report End"),
  /** Contract Effective — X12 code "092". */
  CONTRACT_EFFECTIVE("092", "Contract Effective"),
  /** Contract Expiration — X12 code "093". */
  CONTRACT_EXPIRATION("093", "Contract Expiration"),
  /** Manufacture — X12 code "094". */
  MANUFACTURE("094", "Manufacture"),
  /** Bill of Lading — X12 code "095". */
  BILL_OF_LADING("095", "Bill of Lading"),
  /** Discharge — X12 code "096". */
  DISCHARGE("096", "Discharge"),
  /** Transaction Creation — X12 code "097". */
  TRANSACTION_CREATION("097", "Transaction Creation"),
  /** Bid (Effective) — X12 code "098". */
  BID_EFFECTIVE("098", "Bid (Effective)"),
  /** Bid Open (Date Bids Will Be Opened) — X12 code "099". */
  BID_OPEN("099", "Bid Open (Date Bids Will Be Opened)"),
  /** No Shipping Schedule Established as of — X12 code "100". */
  NO_SHIPPING_SCHEDULE("100", "No Shipping Schedule Established as of"),
  /** No Production Schedule Established as of — X12 code "101". */
  NO_PRODUCTION_SCHEDULE("101", "No Production Schedule Established as of"),
  /** Issue — X12 code "102". */
  ISSUE("102", "Issue"),
  /** Award — X12 code "103". */
  AWARD("103", "Award"),
  /** System Survey — X12 code "104". */
  SYSTEM_SURVEY("104", "System Survey"),
  /** Quality Rating — X12 code "105". */
  QUALITY_RATING("105", "Quality Rating"),
  /** Required By — X12 code "106". */
  REQUIRED_BY("106", "Required By"),
  /** Deposit — X12 code "107". */
  DEPOSIT("107", "Deposit"),
  /** Postmark — X12 code "108". */
  POSTMARK("108", "Postmark"),
  /** Received at Lockbox — X12 code "109". */
  RECEIVED_AT_LOCKBOX("109", "Received at Lockbox"),
  /** Originally Scheduled Ship — X12 code "110". */
  ORIGINALLY_SCHEDULED_SHIP("110", "Originally Scheduled Ship"),
  /** Manifest/Ship Notice — X12 code "111". */
  MANIFEST_SHIP_NOTICE("111", "Manifest/Ship Notice"),
  /** Buyers Dock — X12 code "112". */
  BUYERS_DOCK("112", "Buyers Dock"),
  /** Sample Required — X12 code "113". */
  SAMPLE_REQUIRED("113", "Sample Required"),
  /** Tooling Required — X12 code "114". */
  TOOLING_REQUIRED("114", "Tooling Required"),
  /** Sample Available — X12 code "115". */
  SAMPLE_AVAILABLE("115", "Sample Available"),
  /** Scheduled Interchange Delivery — X12 code "116". */
  SCHEDULED_INTERCHANGE_DELIVERY("116", "Scheduled Interchange Delivery"),
  /** Requested Pickup — X12 code "118". */
  REQUESTED_PICKUP("118", "Requested Pickup"),
  /** Test Performed — X12 code "119". */
  TEST_PERFORMED("119", "Test Performed"),
  /** Control Plan — X12 code "120". */
  CONTROL_PLAN("120", "Control Plan"),
  /** Feasibility Sign Off — X12 code "121". */
  FEASIBILITY_SIGN_OFF("121", "Feasibility Sign Off"),
  /** Failure Mode Effective — X12 code "122". */
  FAILURE_MODE_EFFECTIVE("122", "Failure Mode Effective"),
  /** Group Contract Effective — X12 code "124". */
  GROUP_CONTRACT_EFFECTIVE("124", "Group Contract Effective"),
  /** Group Contract Expiration — X12 code "125". */
  GROUP_CONTRACT_EXPIRATION("125", "Group Contract Expiration"),
  /** Wholesale Contract Effective — X12 code "126". */
  WHOLESALE_CONTRACT_EFFECTIVE("126", "Wholesale Contract Effective"),
  /** Wholesale Contract Expiration — X12 code "127". */
  WHOLESALE_CONTRACT_EXPIRATION("127", "Wholesale Contract Expiration"),
  /** Replacement Effective — X12 code "128". */
  REPLACEMENT_EFFECTIVE("128", "Replacement Effective"),
  /** Customer Contract Effective — X12 code "129". */
  CUSTOMER_CONTRACT_EFFECTIVE("129", "Customer Contract Effective"),
  /** Customer Contract Expiration — X12 code "130". */
  CUSTOMER_CONTRACT_EXPIRATION("130", "Customer Contract Expiration"),
  /** Item Contract Effective — X12 code "131". */
  ITEM_CONTRACT_EFFECTIVE("131", "Item Contract Effective"),
  /** Item Contract Expiration — X12 code "132". */
  ITEM_CONTRACT_EXPIRATION("132", "Item Contract Expiration"),
  /** Accounts Receivable - Statement Date — X12 code "133". */
  ACCOUNTS_RECEIVABLE_STATEMENT_DATE("133", "Accounts Receivable - Statement Date"),
  /** Ready for Inspection — X12 code "134". */
  READY_FOR_INSPECTION("134", "Ready for Inspection"),
  /** Booking — X12 code "135". */
  BOOKING("135", "Booking"),
  /** Technical Rating — X12 code "136". */
  TECHNICAL_RATING("136", "Technical Rating"),
  /** Delivery Rating — X12 code "137". */
  DELIVERY_RATING("137", "Delivery Rating"),
  /** Commercial Rating — X12 code "138". */
  COMMERCIAL_RATING("138", "Commercial Rating"),
  /** Estimated — X12 code "139". */
  ESTIMATED("139", "Estimated"),
  /** Actual — X12 code "140". */
  ACTUAL("140", "Actual"),
  /** Assigned — X12 code "141". */
  ASSIGNED("141", "Assigned"),
  /** Loss — X12 code "142". */
  LOSS("142", "Loss"),
  /** Due Date of First Payment to Principal and Interest — X12 code "143". */
  DUE_DATE_FIRST_PAYMENT("143", "Due Date of First Payment to Principal and Interest"),
  /** Estimated Acceptance — X12 code "144". */
  ESTIMATED_ACCEPTANCE("144", "Estimated Acceptance"),
  /** Opening Date — X12 code "145". */
  OPENING_DATE("145", "Opening Date"),
  /** Closing Date — X12 code "146". */
  CLOSING_DATE("146", "Closing Date"),
  /** Due Date Last Complete Installment Paid — X12 code "147". */
  DUE_DATE_LAST_COMPLETE_INSTALLMENT("147", "Due Date Last Complete Installment Paid"),
  /**
   * Date of Local Office Approval of Conveyance of Damaged Real Estate Property — X12 code "148".
   */
  DATE_LOCAL_OFFICE_APPROVAL(
      "148", "Date of Local Office Approval of Conveyance of Damaged Real Estate Property"),
  /** Date Deed Filed for Record — X12 code "149". */
  DATE_DEED_FILED("149", "Date Deed Filed for Record"),
  /** Service Period Start — X12 code "150". */
  SERVICE_PERIOD_START("150", "Service Period Start"),
  /** Service Period End — X12 code "151". */
  SERVICE_PERIOD_END("151", "Service Period End"),
  /** Effective Date of Change — X12 code "152". */
  EFFECTIVE_DATE_OF_CHANGE("152", "Effective Date of Change"),
  /** Service Interruption — X12 code "153". */
  SERVICE_INTERRUPTION("153", "Service Interruption"),
  /** Adjustment Period Start — X12 code "154". */
  ADJUSTMENT_PERIOD_START("154", "Adjustment Period Start"),
  /** Adjustment Period End — X12 code "155". */
  ADJUSTMENT_PERIOD_END("155", "Adjustment Period End"),
  /** Allotment Period Start — X12 code "156". */
  ALLOTMENT_PERIOD_START("156", "Allotment Period Start"),
  /** Test Period Start — X12 code "157". */
  TEST_PERIOD_START("157", "Test Period Start"),
  /** Test Period Ending — X12 code "158". */
  TEST_PERIOD_ENDING("158", "Test Period Ending"),
  /** Bid Price Exception — X12 code "159". */
  BID_PRICE_EXCEPTION("159", "Bid Price Exception"),
  /** Samples to be Returned By — X12 code "160". */
  SAMPLES_TO_BE_RETURNED_BY("160", "Samples to be Returned By"),
  /** Loaded on Vessel — X12 code "161". */
  LOADED_ON_VESSEL("161", "Loaded on Vessel"),
  /** Pending Archive — X12 code "162". */
  PENDING_ARCHIVE("162", "Pending Archive"),
  /** Actual Archive — X12 code "163". */
  ACTUAL_ARCHIVE("163", "Actual Archive"),
  /** First Issue — X12 code "164". */
  FIRST_ISSUE("164", "First Issue"),
  /** Final Issue — X12 code "165". */
  FINAL_ISSUE("165", "Final Issue"),
  /** Message — X12 code "166". */
  MESSAGE("166", "Message"),
  /** Most Recent Revision (or Initial Version) — X12 code "167". */
  MOST_RECENT_REVISION("167", "Most Recent Revision (or Initial Version)"),
  /** Release — X12 code "168". */
  RELEASE("168", "Release"),
  /** Product Availability Date — X12 code "169". */
  PRODUCT_AVAILABILITY_DATE("169", "Product Availability Date"),
  /** Supplemental Issue — X12 code "170". */
  SUPPLEMENTAL_ISSUE("170", "Supplemental Issue"),
  /** Revision — X12 code "171". */
  REVISION("171", "Revision"),
  /** Correction — X12 code "172". */
  CORRECTION("172", "Correction"),
  /** Week Ending — X12 code "173". */
  WEEK_ENDING("173", "Week Ending"),
  /** Month Ending — X12 code "174". */
  MONTH_ENDING("174", "Month Ending"),
  /** Cancel if not shipped by — X12 code "175". */
  CANCEL_IF_NOT_SHIPPED_BY("175", "Cancel if not shipped by"),
  /** Expedited on — X12 code "176". */
  EXPEDITED_ON("176", "Expedited on"),
  /** Cancellation — X12 code "177". */
  CANCELLATION("177", "Cancellation"),
  /** Hold (as of) — X12 code "178". */
  HOLD_AS_OF("178", "Hold (as of)"),
  /** Hold as Stock (as of) — X12 code "179". */
  HOLD_AS_STOCK("179", "Hold as Stock (as of)"),
  /** No Promise (as of) — X12 code "180". */
  NO_PROMISE("180", "No Promise (as of)"),
  /** Stop Work (as of) — X12 code "181". */
  STOP_WORK("181", "Stop Work (as of)"),
  /** Will Advise (as of) — X12 code "182". */
  WILL_ADVISE("182", "Will Advise (as of)"),
  /** Connection — X12 code "183". */
  CONNECTION("183", "Connection"),
  /** Inventory — X12 code "184". */
  INVENTORY("184", "Inventory"),
  /** Vessel Registry — X12 code "185". */
  VESSEL_REGISTRY("185", "Vessel Registry"),
  /** Invoice Period Start — X12 code "186". */
  INVOICE_PERIOD_START("186", "Invoice Period Start"),
  /** Invoice Period End — X12 code "187". */
  INVOICE_PERIOD_END("187", "Invoice Period End"),
  /** Credit Advice — X12 code "188". */
  CREDIT_ADVICE("188", "Credit Advice"),
  /** Debit Advice — X12 code "189". */
  DEBIT_ADVICE("189", "Debit Advice"),
  /** Released to Vessel — X12 code "190". */
  RELEASED_TO_VESSEL("190", "Released to Vessel"),
  /** Material Specification — X12 code "191". */
  MATERIAL_SPECIFICATION("191", "Material Specification"),
  /** Delivery Ticket — X12 code "192". */
  DELIVERY_TICKET("192", "Delivery Ticket"),
  /** Period Start — X12 code "193". */
  PERIOD_START("193", "Period Start"),
  /** Period End — X12 code "194". */
  PERIOD_END("194", "Period End"),
  /** Contract Re-Open — X12 code "195". */
  CONTRACT_RE_OPEN("195", "Contract Re-Open"),
  /** Start — X12 code "196". */
  START("196", "Start"),
  /** End — X12 code "197". */
  END("197", "End"),
  /** Completion — X12 code "198". */
  COMPLETION("198", "Completion"),
  /** Seal — X12 code "199". */
  SEAL("199", "Seal"),
  /** Assembly Start — X12 code "200". */
  ASSEMBLY_START("200", "Assembly Start"),
  /** Acceptance — X12 code "201". */
  ACCEPTANCE("201", "Acceptance"),
  /** Master Lease Agreement — X12 code "202". */
  MASTER_LEASE_AGREEMENT("202", "Master Lease Agreement"),
  /** First Produced — X12 code "203". */
  FIRST_PRODUCED("203", "First Produced"),
  /** Official Rail Car Interchange (Either Actual or Agreed Upon) — X12 code "204". */
  OFFICIAL_RAIL_CAR_INTERCHANGE(
      "204", "Official Rail Car Interchange (Either Actual or Agreed Upon)"),
  /** Transmitted — X12 code "205". */
  TRANSMITTED("205", "Transmitted"),
  /** Status (Outside Processor) — X12 code "206". */
  STATUS_OUTSIDE_PROCESSOR("206", "Status (Outside Processor)"),
  /** Status (Commercial) — X12 code "207". */
  STATUS_COMMERCIAL("207", "Status (Commercial)"),
  /** Lot Number Expiration — X12 code "208". */
  LOT_NUMBER_EXPIRATION("208", "Lot Number Expiration"),
  /** Contract Performance Start — X12 code "209". */
  CONTRACT_PERFORMANCE_START("209", "Contract Performance Start"),
  /** Contract Performance Delivery — X12 code "210". */
  CONTRACT_PERFORMANCE_DELIVERY("210", "Contract Performance Delivery"),
  /** Service Requested — X12 code "211". */
  SERVICE_REQUESTED("211", "Service Requested"),
  /** Returned to Customer — X12 code "212". */
  RETURNED_TO_CUSTOMER("212", "Returned to Customer"),
  /** Adjustment to Bill Dated — X12 code "213". */
  ADJUSTMENT_TO_BILL_DATED("213", "Adjustment to Bill Dated"),
  /** Date of Repair/Service — X12 code "214". */
  DATE_OF_REPAIR_SERVICE("214", "Date of Repair/Service"),
  /** Interruption Start — X12 code "215". */
  INTERRUPTION_START("215", "Interruption Start"),
  /** Interruption End — X12 code "216". */
  INTERRUPTION_END("216", "Interruption End"),
  /** Spud — X12 code "217". */
  SPUD("217", "Spud"),
  /** Initial Completion — X12 code "218". */
  INITIAL_COMPLETION("218", "Initial Completion"),
  /** Plugged and Abandoned — X12 code "219". */
  PLUGGED_AND_ABANDONED("219", "Plugged and Abandoned"),
  /** Penalty — X12 code "220". */
  PENALTY("220", "Penalty"),
  /** Penalty Begin — X12 code "221". */
  PENALTY_BEGIN("221", "Penalty Begin"),
  /** Birth — X12 code "222". */
  BIRTH("222", "Birth"),
  /** Birth Certificate — X12 code "223". */
  BIRTH_CERTIFICATE("223", "Birth Certificate"),
  /** Adoption — X12 code "224". */
  ADOPTION("224", "Adoption"),
  /** Christening — X12 code "225". */
  CHRISTENING("225", "Christening"),
  /** Lease Commencement — X12 code "226". */
  LEASE_COMMENCEMENT("226", "Lease Commencement"),
  /** Lease Term Start — X12 code "227". */
  LEASE_TERM_START("227", "Lease Term Start"),
  /** Lease Term End — X12 code "228". */
  LEASE_TERM_END("228", "Lease Term End"),
  /** Rent Start — X12 code "229". */
  RENT_START("229", "Rent Start"),
  /** Installation — X12 code "230". */
  INSTALLATION("230", "Installation"),
  /** Progress Payment — X12 code "231". */
  PROGRESS_PAYMENT("231", "Progress Payment"),
  /** Claim Statement Period Start — X12 code "232". */
  CLAIM_STATEMENT_PERIOD_START("232", "Claim Statement Period Start"),
  /** Claim Statement Period End — X12 code "233". */
  CLAIM_STATEMENT_PERIOD_END("233", "Claim Statement Period End"),
  /** Settlement Date — X12 code "234". */
  SETTLEMENT_DATE("234", "Settlement Date"),
  /** Delayed Billing (Not Delayed Payment) — X12 code "235". */
  DELAYED_BILLING("235", "Delayed Billing (Not Delayed Payment)"),
  /** Lender Credit Check — X12 code "236". */
  LENDER_CREDIT_CHECK("236", "Lender Credit Check"),
  /** Student Signed — X12 code "237". */
  STUDENT_SIGNED("237", "Student Signed"),
  /** Schedule Release — X12 code "238". */
  SCHEDULE_RELEASE("238", "Schedule Release"),
  /** Baseline — X12 code "239". */
  BASELINE("239", "Baseline"),
  /** Baseline Start — X12 code "240". */
  BASELINE_START("240", "Baseline Start"),
  /** Baseline Complete — X12 code "241". */
  BASELINE_COMPLETE("241", "Baseline Complete"),
  /** Actual Start — X12 code "242". */
  ACTUAL_START("242", "Actual Start"),
  /** Actual Complete — X12 code "243". */
  ACTUAL_COMPLETE("243", "Actual Complete"),
  /** Estimated Start — X12 code "244". */
  ESTIMATED_START("244", "Estimated Start"),
  /** Estimated Completion — X12 code "245". */
  ESTIMATED_COMPLETION("245", "Estimated Completion"),
  /** Start no earlier than — X12 code "246". */
  START_NO_EARLIER_THAN("246", "Start no earlier than"),
  /** Start no later than — X12 code "247". */
  START_NO_LATER_THAN("247", "Start no later than"),
  /** Finish no later than — X12 code "248". */
  FINISH_NO_LATER_THAN("248", "Finish no later than"),
  /** Finish no earlier than — X12 code "249". */
  FINISH_NO_EARLIER_THAN("249", "Finish no earlier than"),
  /** Mandatory (or Target) Start — X12 code "250". */
  MANDATORY_START("250", "Mandatory (or Target) Start"),
  /** Mandatory (or Target) Finish — X12 code "251". */
  MANDATORY_FINISH("251", "Mandatory (or Target) Finish"),
  /** Early Start — X12 code "252". */
  EARLY_START("252", "Early Start"),
  /** Early Finish — X12 code "253". */
  EARLY_FINISH("253", "Early Finish"),
  /** Late Start — X12 code "254". */
  LATE_START("254", "Late Start"),
  /** Late Finish — X12 code "255". */
  LATE_FINISH("255", "Late Finish"),
  /** Scheduled Start — X12 code "256". */
  SCHEDULED_START("256", "Scheduled Start"),
  /** Scheduled Finish — X12 code "257". */
  SCHEDULED_FINISH("257", "Scheduled Finish"),
  /** Original Early Start — X12 code "258". */
  ORIGINAL_EARLY_START("258", "Original Early Start"),
  /** Original Early Finish — X12 code "259". */
  ORIGINAL_EARLY_FINISH("259", "Original Early Finish"),
  /** Rest Day — X12 code "260". */
  REST_DAY("260", "Rest Day"),
  /** Rest Start — X12 code "261". */
  REST_START("261", "Rest Start"),
  /** Rest Finish — X12 code "262". */
  REST_FINISH("262", "Rest Finish"),
  /** Holiday — X12 code "263". */
  HOLIDAY("263", "Holiday"),
  /** Holiday Start — X12 code "264". */
  HOLIDAY_START("264", "Holiday Start"),
  /** Holiday Finish — X12 code "265". */
  HOLIDAY_FINISH("265", "Holiday Finish"),
  /** Base — X12 code "266". */
  BASE("266", "Base"),
  /** Timenow — X12 code "267". */
  TIMENOW("267", "Timenow"),
  /** End Date of Support — X12 code "268". */
  END_DATE_OF_SUPPORT("268", "End Date of Support"),
  /** Date Account Matures — X12 code "269". */
  DATE_ACCOUNT_MATURES("269", "Date Account Matures"),
  /** Date Filed — X12 code "270". */
  DATE_FILED("270", "Date Filed"),
  /** Penalty End — X12 code "271". */
  PENALTY_END("271", "Penalty End"),
  /** Exit Plant Date — X12 code "272". */
  EXIT_PLANT_DATE("272", "Exit Plant Date"),
  /** Latest On Board Carrier Date — X12 code "273". */
  LATEST_ON_BOARD_CARRIER_DATE("273", "Latest On Board Carrier Date"),
  /** Requested Departure Date — X12 code "274". */
  REQUESTED_DEPARTURE_DATE("274", "Requested Departure Date"),
  /** Approved — X12 code "275". */
  APPROVED("275", "Approved"),
  /** Contract Start — X12 code "276". */
  CONTRACT_START("276", "Contract Start"),
  /** Contract Definition — X12 code "277". */
  CONTRACT_DEFINITION("277", "Contract Definition"),
  /** Last Item Delivery — X12 code "278". */
  LAST_ITEM_DELIVERY("278", "Last Item Delivery"),
  /** Contract Completion — X12 code "279". */
  CONTRACT_COMPLETION("279", "Contract Completion"),
  /** Date Course of Orthodontics Treatment Began or is Expected to Begin — X12 code "280". */
  DATE_COURSE_OF_ORTHODONTICS(
      "280", "Date Course of Orthodontics Treatment Began or is Expected to Begin"),
  /** Over Target Baseline Month — X12 code "281". */
  OVER_TARGET_BASELINE_MONTH("281", "Over Target Baseline Month"),
  /** Previous Report — X12 code "282". */
  PREVIOUS_REPORT("282", "Previous Report"),
  /** Funds Appropriation - Start — X12 code "283". */
  FUNDS_APPROPRIATION_START("283", "Funds Appropriation - Start"),
  /** Funds Appropriation - End — X12 code "284". */
  FUNDS_APPROPRIATION_END("284", "Funds Appropriation - End"),
  /** Employment or Hire — X12 code "285". */
  EMPLOYMENT_OR_HIRE("285", "Employment or Hire"),
  /** Retirement — X12 code "286". */
  RETIREMENT("286", "Retirement"),
  /** Medicare — X12 code "287". */
  MEDICARE("287", "Medicare"),
  /** Consolidated Omnibus Budget Reconciliation Act (COBRA) — X12 code "288". */
  COBRA("288", "Consolidated Omnibus Budget Reconciliation Act (COBRA)"),
  /** Premium Paid to Date — X12 code "289". */
  PREMIUM_PAID_TO_DATE("289", "Premium Paid to Date"),
  /** Coordination of Benefits — X12 code "290". */
  COORDINATION_OF_BENEFITS("290", "Coordination of Benefits"),
  /** Plan — X12 code "291". */
  PLAN("291", "Plan"),
  /** Benefit — X12 code "292". */
  BENEFIT("292", "Benefit"),
  /** Education — X12 code "293". */
  EDUCATION("293", "Education"),
  /** Earnings Effective Date — X12 code "294". */
  EARNINGS_EFFECTIVE_DATE("294", "Earnings Effective Date"),
  /** Primary Care Provider — X12 code "295". */
  PRIMARY_CARE_PROVIDER("295", "Primary Care Provider"),
  /** Initial Disability Period Return To Work — X12 code "296". */
  INITIAL_DISABILITY_PERIOD_RETURN_TO_WORK("296", "Initial Disability Period Return To Work"),
  /** Initial Disability Period Last Day Worked — X12 code "297". */
  INITIAL_DISABILITY_PERIOD_LAST_DAY_WORKED("297", "Initial Disability Period Last Day Worked"),
  /** Latest Absence — X12 code "298". */
  LATEST_ABSENCE("298", "Latest Absence"),
  /** Illness — X12 code "299". */
  ILLNESS("299", "Illness"),
  /** Enrollment Signature Date — X12 code "300". */
  ENROLLMENT_SIGNATURE_DATE("300", "Enrollment Signature Date"),
  /** Consolidated Omnibus Budget Reconciliation Act (COBRA) Qualifying Event — X12 code "301". */
  COBRA_QUALIFYING_EVENT(
      "301", "Consolidated Omnibus Budget Reconciliation Act (COBRA) Qualifying Event"),
  /** Maintenance — X12 code "302". */
  MAINTENANCE("302", "Maintenance"),
  /** Maintenance Effective — X12 code "303". */
  MAINTENANCE_EFFECTIVE("303", "Maintenance Effective"),
  /** Latest Visit or Consultation — X12 code "304". */
  LATEST_VISIT_OR_CONSULTATION("304", "Latest Visit or Consultation"),
  /** Net Credit Service Date — X12 code "305". */
  NET_CREDIT_SERVICE_DATE("305", "Net Credit Service Date"),
  /** Adjustment Effective Date — X12 code "306". */
  ADJUSTMENT_EFFECTIVE_DATE("306", "Adjustment Effective Date"),
  /** Eligibility — X12 code "307". */
  ELIGIBILITY("307", "Eligibility"),
  /** Pre-Award Survey — X12 code "308". */
  PRE_AWARD_SURVEY("308", "Pre-Award Survey"),
  /** Plan Termination — X12 code "309". */
  PLAN_TERMINATION("309", "Plan Termination"),
  /** Date of Closing — X12 code "310". */
  DATE_OF_CLOSING("310", "Date of Closing"),
  /** Latest Receiving Date/Cutoff Date — X12 code "311". */
  LATEST_RECEIVING_DATE("311", "Latest Receiving Date/Cutoff Date"),
  /** Salary Deferral — X12 code "312". */
  SALARY_DEFERRAL("312", "Salary Deferral"),
  /** Cycle — X12 code "313". */
  CYCLE("313", "Cycle"),
  /** Disability — X12 code "314". */
  DISABILITY("314", "Disability"),
  /** Offset — X12 code "315". */
  OFFSET("315", "Offset"),
  /** Prior Incorrect Date of Birth — X12 code "316". */
  PRIOR_INCORRECT_DATE_OF_BIRTH("316", "Prior Incorrect Date of Birth"),
  /** Corrected Date of Birth — X12 code "317". */
  CORRECTED_DATE_OF_BIRTH("317", "Corrected Date of Birth"),
  /** Added — X12 code "318". */
  ADDED("318", "Added"),
  /** Failed — X12 code "319". */
  FAILED("319", "Failed"),
  /** Date Foreclosure Proceedings Instituted — X12 code "320". */
  DATE_FORECLOSURE_PROCEEDINGS_INSTITUTED("320", "Date Foreclosure Proceedings Instituted"),
  /** Purchased — X12 code "321". */
  PURCHASED("321", "Purchased"),
  /** Put into Service — X12 code "322". */
  PUT_INTO_SERVICE("322", "Put into Service"),
  /** Replaced — X12 code "323". */
  REPLACED("323", "Replaced"),
  /** Returned — X12 code "324". */
  RETURNED("324", "Returned"),
  /** Disbursement Date — X12 code "325". */
  DISBURSEMENT_DATE("325", "Disbursement Date"),
  /** Guarantee Date — X12 code "326". */
  GUARANTEE_DATE("326", "Guarantee Date"),
  /** Quarter Ending — X12 code "327". */
  QUARTER_ENDING("327", "Quarter Ending"),
  /** Changed — X12 code "328". */
  CHANGED("328", "Changed"),
  /** Terminated — X12 code "329". */
  TERMINATED("329", "Terminated"),
  /** Referral Date — X12 code "330". */
  REFERRAL_DATE("330", "Referral Date"),
  /** Evaluation Date — X12 code "331". */
  EVALUATION_DATE("331", "Evaluation Date"),
  /** Placement Date — X12 code "332". */
  PLACEMENT_DATE("332", "Placement Date"),
  /** Individual Education Plan (IEP) — X12 code "333". */
  INDIVIDUAL_EDUCATION_PLAN("333", "Individual Education Plan (IEP)"),
  /** Re-evaluation Date — X12 code "334". */
  RE_EVALUATION_DATE("334", "Re-evaluation Date"),
  /** Dismissal Date — X12 code "335". */
  DISMISSAL_DATE("335", "Dismissal Date"),
  /** Employment Begin — X12 code "336". */
  EMPLOYMENT_BEGIN("336", "Employment Begin"),
  /** Employment End — X12 code "337". */
  EMPLOYMENT_END("337", "Employment End"),
  /** Medicare Begin — X12 code "338". */
  MEDICARE_BEGIN("338", "Medicare Begin"),
  /** Medicare End — X12 code "339". */
  MEDICARE_END("339", "Medicare End"),
  /** Consolidated Omnibus Budget Reconciliation Act (COBRA) Begin — X12 code "340". */
  COBRA_BEGIN("340", "Consolidated Omnibus Budget Reconciliation Act (COBRA) Begin"),
  /** Consolidated Omnibus Budget Reconciliation Act (COBRA) End — X12 code "341". */
  COBRA_END("341", "Consolidated Omnibus Budget Reconciliation Act (COBRA) End"),
  /** Premium Paid to Date Begin — X12 code "342". */
  PREMIUM_PAID_TO_DATE_BEGIN("342", "Premium Paid to Date Begin"),
  /** Premium Paid to Date End — X12 code "343". */
  PREMIUM_PAID_TO_DATE_END("343", "Premium Paid to Date End"),
  /** Coordination of Benefits Begin — X12 code "344". */
  COORDINATION_OF_BENEFITS_BEGIN("344", "Coordination of Benefits Begin"),
  /** Coordination of Benefits End — X12 code "345". */
  COORDINATION_OF_BENEFITS_END("345", "Coordination of Benefits End"),
  /** Plan Begin — X12 code "346". */
  PLAN_BEGIN("346", "Plan Begin"),
  /** Plan End — X12 code "347". */
  PLAN_END("347", "Plan End"),
  /** Benefit Begin — X12 code "348". */
  BENEFIT_BEGIN("348", "Benefit Begin"),
  /** Benefit End — X12 code "349". */
  BENEFIT_END("349", "Benefit End"),
  /** Education Begin — X12 code "350". */
  EDUCATION_BEGIN("350", "Education Begin"),
  /** Education End — X12 code "351". */
  EDUCATION_END("351", "Education End"),
  /** Primary Care Provider Begin — X12 code "352". */
  PRIMARY_CARE_PROVIDER_BEGIN("352", "Primary Care Provider Begin"),
  /** Primary Care Provider End — X12 code "353". */
  PRIMARY_CARE_PROVIDER_END("353", "Primary Care Provider End"),
  /** Illness Begin — X12 code "354". */
  ILLNESS_BEGIN("354", "Illness Begin"),
  /** Illness End — X12 code "355". */
  ILLNESS_END("355", "Illness End"),
  /** Eligibility Begin — X12 code "356". */
  ELIGIBILITY_BEGIN("356", "Eligibility Begin"),
  /** Eligibility End — X12 code "357". */
  ELIGIBILITY_END("357", "Eligibility End"),
  /** Cycle Begin — X12 code "358". */
  CYCLE_BEGIN("358", "Cycle Begin"),
  /** Cycle End — X12 code "359". */
  CYCLE_END("359", "Cycle End"),
  /** Initial Disability Period Start — X12 code "360". */
  INITIAL_DISABILITY_PERIOD_START("360", "Initial Disability Period Start"),
  /** Initial Disability Period End — X12 code "361". */
  INITIAL_DISABILITY_PERIOD_END("361", "Initial Disability Period End"),
  /** Offset Begin — X12 code "362". */
  OFFSET_BEGIN("362", "Offset Begin"),
  /** Offset End — X12 code "363". */
  OFFSET_END("363", "Offset End"),
  /** Plan Period Election Begin — X12 code "364". */
  PLAN_PERIOD_ELECTION_BEGIN("364", "Plan Period Election Begin"),
  /** Plan Period Election End — X12 code "365". */
  PLAN_PERIOD_ELECTION_END("365", "Plan Period Election End"),
  /** Plan Period Election — X12 code "366". */
  PLAN_PERIOD_ELECTION("366", "Plan Period Election"),
  /** Due to Customer — X12 code "367". */
  DUE_TO_CUSTOMER("367", "Due to Customer"),
  /** Submittal — X12 code "368". */
  SUBMITTAL("368", "Submittal"),
  /** Estimated Departure Date — X12 code "369". */
  ESTIMATED_DEPARTURE_DATE("369", "Estimated Departure Date"),
  /** Actual Departure Date — X12 code "370". */
  ACTUAL_DEPARTURE_DATE("370", "Actual Departure Date"),
  /** Estimated Arrival Date — X12 code "371". */
  ESTIMATED_ARRIVAL_DATE("371", "Estimated Arrival Date"),
  /** Actual Arrival Date — X12 code "372". */
  ACTUAL_ARRIVAL_DATE("372", "Actual Arrival Date"),
  /** Order Start — X12 code "373". */
  ORDER_START("373", "Order Start"),
  /** Order End — X12 code "374". */
  ORDER_END("374", "Order End"),
  /** Delivery Start — X12 code "375". */
  DELIVERY_START("375", "Delivery Start"),
  /** Delivery End — X12 code "376". */
  DELIVERY_END("376", "Delivery End"),
  /** Contract Costs Through — X12 code "377". */
  CONTRACT_COSTS_THROUGH("377", "Contract Costs Through"),
  /** Financial Information Submission — X12 code "378". */
  FINANCIAL_INFORMATION_SUBMISSION("378", "Financial Information Submission"),
  /** Business Termination — X12 code "379". */
  BUSINESS_TERMINATION("379", "Business Termination"),
  /** Applicant Signed — X12 code "380". */
  APPLICANT_SIGNED("380", "Applicant Signed"),
  /** Cosigner Signed — X12 code "381". */
  COSIGNER_SIGNED("381", "Cosigner Signed"),
  /** Enrollment — X12 code "382". */
  ENROLLMENT("382", "Enrollment"),
  /** Adjusted Hire — X12 code "383". */
  ADJUSTED_HIRE("383", "Adjusted Hire"),
  /** Credited Service — X12 code "384". */
  CREDITED_SERVICE("384", "Credited Service"),
  /** Credited Service Begin — X12 code "385". */
  CREDITED_SERVICE_BEGIN("385", "Credited Service Begin"),
  /** Credited Service End — X12 code "386". */
  CREDITED_SERVICE_END("386", "Credited Service End"),
  /** Deferred Distribution — X12 code "387". */
  DEFERRED_DISTRIBUTION("387", "Deferred Distribution"),
  /** Payment Commencement — X12 code "388". */
  PAYMENT_COMMENCEMENT("388", "Payment Commencement"),
  /** Payroll Period — X12 code "389". */
  PAYROLL_PERIOD("389", "Payroll Period"),
  /** Payroll Period Begin — X12 code "390". */
  PAYROLL_PERIOD_BEGIN("390", "Payroll Period Begin"),
  /** Payroll Period End — X12 code "391". */
  PAYROLL_PERIOD_END("391", "Payroll Period End"),
  /** Plan Entry — X12 code "392". */
  PLAN_ENTRY("392", "Plan Entry"),
  /** Plan Participation Suspension — X12 code "393". */
  PLAN_PARTICIPATION_SUSPENSION("393", "Plan Participation Suspension"),
  /** Rehire — X12 code "394". */
  REHIRE("394", "Rehire"),
  /** Retermination — X12 code "395". */
  RETERMINATION("395", "Retermination"),
  /** Termination — X12 code "396". */
  TERMINATION("396", "Termination"),
  /** Valuation — X12 code "397". */
  VALUATION("397", "Valuation"),
  /** Vesting Service — X12 code "398". */
  VESTING_SERVICE("398", "Vesting Service"),
  /** Vesting Service Begin — X12 code "399". */
  VESTING_SERVICE_BEGIN("399", "Vesting Service Begin"),
  /** Vesting Service End — X12 code "400". */
  VESTING_SERVICE_END("400", "Vesting Service End"),
  /** Last Premium Paid Date — X12 code "543". */
  LAST_PREMIUM_PAID_DATE("543", "Last Premium Paid Date"),
  /** Previous Period — X12 code "695". */
  PREVIOUS_PERIOD("695", "Previous Period");

  private final String code;
  private final String description;
  private static final EdiEnumLookup<DateTimeQualifier> LOOKUP;

  static {
    LOOKUP =
        new EdiEnumLookup<>(
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
                Map.entry("bol date", BILL_OF_LADING)));
  }

  DateTimeQualifier(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Gets a DateTimeQualifier instance from any input string. Matches against codes, names,
   * descriptions, and common variations.
   *
   * @param input the string to look up
   * @return the matching DateTimeQualifier
   * @throws IllegalArgumentException if no match is found
   */
  public static DateTimeQualifier fromString(String input) {
    return LOOKUP.fromString(input);
  }

  /**
   * Returns the raw X12 code value for this constant (not the enum name), so the enum renders
   * directly into an EDI element.
   */
  @Override
  public String toString() {
    return code;
  }
}
