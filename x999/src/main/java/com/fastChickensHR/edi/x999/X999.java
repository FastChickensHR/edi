/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x999;

/**
 * The 999 (and 997) acknowledgment dialect's {@code Location.name} vocabulary — the tokens
 * {@link X999FileParser} emits so a consumer (the ack poller) can read the acknowledgment by location,
 * the mirror of how the 834 generator reads its own {@link com.fastChickensHR.edi.core.Location}s.
 *
 * <p>File-level fields describe the acknowledged <em>functional group</em> (the reply is one group per
 * 999); each record describes one acknowledged <em>transaction set</em>. Values are the raw X12 codes —
 * the parser stays dialect-dumb and leaves accept/reject interpretation to the caller.
 */
public final class X999 {
    private X999() {
    }

    // ---- File level: the 999's own envelope + the acknowledged group ----
    /** ISA13 of the 999 interchange. */
    public static final String INTERCHANGE_CONTROL_NUMBER = "interchangeControlNumber";
    /** AK101 — functional identifier code of the acknowledged group (e.g. {@code BE} for an 834). */
    public static final String FUNCTIONAL_ID_CODE = "functionalIdCode";
    /** AK102 — group control number (the original GS06) being acknowledged; the correlation key. */
    public static final String GROUP_CONTROL_NUMBER = "groupControlNumber";
    /** AK901 — functional group acknowledgment code (see {@link #ACCEPTED} et al). */
    public static final String GROUP_STATUS = "groupAckStatus";

    // ---- File level: TA1 interchange acknowledgment (may stand alone or accompany a 999) ----
    /** TA101 — interchange control number (the original ISA13) the TA1 acknowledges. */
    public static final String ACKNOWLEDGED_INTERCHANGE_CONTROL_NUMBER = "acknowledgedInterchangeControlNumber";
    /** TA104 — interchange acknowledgment code (A accepted, E accepted-with-errors, R rejected). */
    public static final String INTERCHANGE_ACK_STATUS = "interchangeAckStatus";

    // ---- Record level: one per acknowledged transaction set ----
    /** AK202 — transaction set control number (the original ST02) being acknowledged. */
    public static final String TRANSACTION_SET_CONTROL_NUMBER = "transactionSetControlNumber";
    /** AK501 / IK501 — transaction set acknowledgment code (see {@link #ACCEPTED} et al). */
    public static final String TRANSACTION_SET_STATUS = "transactionSetAckStatus";

    // ---- Acknowledgment codes (AK901 / AK501 / IK501), for the consumer's convenience ----
    /** {@code A} — Accepted. */
    public static final String ACCEPTED = "A";
    /** {@code E} — Accepted, but errors were noted. */
    public static final String ACCEPTED_WITH_ERRORS = "E";
    /** {@code P} — Partially accepted (some transaction sets rejected). */
    public static final String PARTIALLY_ACCEPTED = "P";
    /** {@code R} — Rejected. */
    public static final String REJECTED = "R";
    /** {@code M} — Rejected: message authentication code failed. */
    public static final String REJECTED_AUTH = "M";
    /** {@code W} — Rejected: assurance failed validity tests. */
    public static final String REJECTED_VALIDITY = "W";
    /** {@code X} — Rejected: content decryption failed. */
    public static final String REJECTED_DECRYPTION = "X";
}
