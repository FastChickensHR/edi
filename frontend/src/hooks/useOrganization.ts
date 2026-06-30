import { useContext } from 'react'
import { OrganizationContext } from '@/features/organizations/OrganizationContext'

export function useOrganization() {
  const ctx = useContext(OrganizationContext)
  if (!ctx) throw new Error('useOrganization must be used inside OrganizationProvider')
  return ctx
}
