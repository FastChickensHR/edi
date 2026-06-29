import { createContext, useCallback, useState, type ReactNode } from 'react'
import type { Organization } from '@/types'

interface OrganizationState {
  activeOrganization: Organization | null
  setActiveOrganization: (org: Organization) => void
}

export const OrganizationContext = createContext<OrganizationState | null>(null)

export function OrganizationProvider({ children }: { children: ReactNode }) {
  const [activeOrganization, setActiveOrganizationState] = useState<Organization | null>(null)

  const setActiveOrganization = useCallback((org: Organization) => {
    setActiveOrganizationState(org)
  }, [])

  return (
    <OrganizationContext.Provider value={{ activeOrganization, setActiveOrganization }}>
      {children}
    </OrganizationContext.Provider>
  )
}
