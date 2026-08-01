/*
 * Copyright (C) 2025 FastChickensHR <contact@fastchickenshr.com>
 *
 * This file is part of the FastChickensHR project.
 *
 * For license information see the LICENSE file in the root of this project.
 */
package com.fastChickensHR.edi.x834.loop2000.loop2310;

import com.fastChickensHR.edi.x834.data.ActionCode;
import com.fastChickensHR.edi.x834.data.IdentificationCodeQualifier;
import com.fastChickensHR.edi.x834.loop2000.data.MaintenanceReasonCode;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * A provider the member is assigned to — one occurrence of Loop 2310 in the X12 834 (005010X220A1),
 * most often the primary care physician an enrollee has selected.
 *
 * <p>The {@link #lastName name} and the {@link #identifier} (under its {@link
 * #identifierQualifier}) say who the provider is. When the assignment is being changed rather than
 * merely stated, the {@link #changeAction}, {@link #changeDate} and optional {@link #changeReason}
 * say so — Anthem's PCP-change scenario sends exactly that, as {@code PLA*2*1P*<date>**<reason>}.
 *
 * <p>Which identifier a carrier wants differs: Florida Blue mandates the NPI ({@code XX}) and says
 * "only the above code is valid"; CareFirst uses its own {@code SV} legacy number until it issues
 * NPIs; BCBSM asks for the NPI when available and its own physician number otherwise. CareFirst
 * also gates the whole loop — "PCP is only processed for Medical products" — which is a config-time
 * answer, not something this library decides.
 *
 * <p>This is a pure domain object; translation to 834 segments is the writer's job.
 */
@Getter
@Setter
public class Provider {

  /** The most Loop 2310 occurrences the 834 permits per member. */
  public static final int MAX_PER_MEMBER = 30;

  /** The provider's last or organization name (NM103) — required. */
  private String lastName;

  /** The provider's first name (NM104). */
  private String firstName;

  /** The provider's middle name (NM105). */
  private String middleName;

  /** What kind of identifier {@link #identifier} is (NM108), e.g. {@code XX} for an NPI. */
  private IdentificationCodeQualifier identifierQualifier;

  /** The provider's identifier (NM109); paired with {@link #identifierQualifier}. */
  private String identifier;

  /** What is happening to this assignment (PLA01); the PLA is emitted only when this is set. */
  private ActionCode changeAction;

  /** When the change takes effect (PLA03); required when {@link #changeAction} is set. */
  private LocalDateTime changeDate;

  /** Why the change is happening (PLA05); optional. */
  private MaintenanceReasonCode changeReason;

  /** Creates an empty provider assignment; callers set fields afterwards. */
  public Provider() {}

  /**
   * Creates a provider assignment naming only the provider.
   *
   * @param lastName the provider's last or organization name (NM103)
   */
  public Provider(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Creates a provider assignment naming the provider and its identifier.
   *
   * @param lastName the provider's last or organization name (NM103)
   * @param identifierQualifier what kind of identifier follows (NM108)
   * @param identifier the provider's identifier (NM109)
   */
  public Provider(
      String lastName, IdentificationCodeQualifier identifierQualifier, String identifier) {
    this.lastName = lastName;
    this.identifierQualifier = identifierQualifier;
    this.identifier = identifier;
  }
}
