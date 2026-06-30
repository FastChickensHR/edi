# fastChickens EDI Platform

A multi-tenant platform for configuring and generating X12 EDI benefit enrollment documents (834 transactions) from HR system data.

## Language

### Identity & Access

**Organization**:
A tenant entity that owns integrations and under which users are grouped. All integration data is scoped to an Organization.
_Avoid_: Tenant, account, company, client

**User**:
An individual with login credentials (email + password) who belongs to one or more Organizations.
_Avoid_: Account, member, person

**OrganizationMember**:
The explicit, role-bearing relationship between a User and an Organization. A User gains access to an Organization's integrations through membership, not through direct ownership.
_Avoid_: Membership, user-org relationship

**MemberRole**:
The access level a User holds within a specific Organization: OWNER, ADMIN, EDITOR, or VIEWER.
_Avoid_: Permission, privilege, access level

**ApiKey**:
A machine-identity credential scoped to an Organization, stored only as a bcrypt hash. Used by automated HR systems that push enrollment data.
_Avoid_: Token, secret, service key

**RefreshToken**:
A server-tracked credential that allows a User to obtain new JWT access tokens without re-authenticating. Stored as a hash to enable server-side revocation.
_Avoid_: Session token

### Integration

**Integration**:
A configured data pipeline between a source HR system and a target EDI format, scoped to an Organization.
_Avoid_: Connection, pipeline, channel

**InternalSystem**:
An enum of systems owned by FastChickensHR that participate in Integrations. Current value: FASTCHICKENS_HR. When an InternalSystem is the `fromSystem` of an Integration, direction is OUTBOUND.
_Avoid_: SystemType (too vague)

**ExternalSystem**:
An enum of third-party HR systems and clearinghouses that participate in Integrations. Current values: STATE_OF_MICHIGAN, WORKDAY, ADP, BENEFITFOCUS. When an ExternalSystem is the `fromSystem` of an Integration, direction is INBOUND.
_Avoid_: SystemType (too vague)

**Partner**:
Marker interface implemented by both InternalSystem and ExternalSystem. Represents any system that can be the source or destination of an Integration.
_Avoid_: System, provider, platform

**IntegrationFormat**:
An enum identifying the EDI format produced by an Integration (e.g., X834).
_Avoid_: Format, type, standard

**X834IntegrationConfig**:
The EDI envelope and enrollment-context settings for an X834 Integration: sender/receiver IDs, policy number, member ID qualifier, and related header fields.
_Avoid_: Config, settings, EDI config

**FieldMappingRule**:
A per-field translation rule from a source HR system field to a target EDI field, with optional value mappings (e.g., HR gender codes → EDI gender codes).
_Avoid_: Mapping, rule, translation

### Frontend

**Page**:
A route-level React component that composes Layouts and Features into a complete view. Pages are thin — they contain no business logic, only wiring.
_Avoid_: View, screen, container

**Layout**:
A structural React component that defines the chrome around page content (`AuthenticatedLayout` with sidebar + topbar, `UnauthenticatedLayout` for login/error). Pages render inside a Layout via `<Outlet>`.
_Avoid_: Shell, wrapper, template

**Feature**:
A self-contained module under `src/features/` grouping the components, hooks, Zod schemas, and API calls for one domain area (e.g. `features/integrations/`). Features are composed into Pages.
_Avoid_: Module, section, domain (in frontend code)

**BaseComponent**:
A styled, accessible, unstyled-primitive-backed building block in `src/components/` (e.g. `Button`, `Input`, `Modal`, `Toast`). BaseComponents are the only place Radix UI primitives are imported.
_Avoid_: UI component, atom, primitive (in application code)

**Theme**:
The typed JavaScript object derived from Tailwind's resolved config via `resolveConfig()`. Passed to `ThemeProvider` and consumed in styled-components as `${({ theme }) => theme.colors.blue[500]}`. No Tailwind class names appear in component markup.
_Avoid_: Design tokens, CSS variables, Tailwind config (in component code)

**AuthContext**:
The React context that holds the in-memory access token, the current User, and login/logout actions. The access token is never persisted to localStorage or cookies.
_Avoid_: Auth store, session context

**OrganizationContext**:
The React context that holds the currently active Organization. All API calls and route guards use this to scope requests.
_Avoid_: Tenant context, active org

### EDI Generation

**Member**:
The X12 834 representation of a subscriber (primary insured). Distinct from the domain concept of a User.
_Avoid_: Subscriber, insured (in EDI generation code)

**DependentMember**:
The X12 834 representation of a dependent under a subscriber.
_Avoid_: Dependent (in EDI generation code — reserved for the domain model)

**EnrollmentContext**:
The bundle of EDI envelope values (policy number, member ID qualifier, dates, maintenance type) passed through the converter layer when translating domain objects to X12 segments.
_Avoid_: Context, config (in converter code)
