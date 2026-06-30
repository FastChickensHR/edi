// Mirrors backend MemberRole enum
export type MemberRole = 'OWNER' | 'ADMIN' | 'EDITOR' | 'VIEWER'

// Mirrors backend SystemType enums
export type InternalSystem = 'FASTCHICKENS_HR'
export type ExternalSystem = 'STATE_OF_MICHIGAN' | 'WORKDAY' | 'ADP' | 'BENEFITFOCUS'
export type IntegrationFormat = 'X834'

export interface Organization {
  id: string
  name: string
  createdAt: string
  updatedAt: string
}

export interface User {
  id: string
  email: string
  createdAt: string
  updatedAt: string
}

export interface OrganizationMember {
  id: string
  organizationId: string
  userId: string
  role: MemberRole
  createdAt: string
  updatedAt: string
}

export interface Integration {
  id: string
  organizationId: string
  name: string
  fromSystem: InternalSystem | ExternalSystem
  toSystem: InternalSystem | ExternalSystem
  format: IntegrationFormat
  createdAt: string
  updatedAt: string
}

export interface AuthTokens {
  accessToken: string
  // refreshToken is managed via httpOnly cookie — not present in JS
}

export interface LoginRequest {
  email: string
  password: string
  organizationId: string
}

export interface Page<T> {
  content: T[]
  totalElements: number
  totalPages: number
  number: number
  size: number
}
